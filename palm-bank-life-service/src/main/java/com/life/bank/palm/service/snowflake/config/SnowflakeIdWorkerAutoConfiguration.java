package com.life.bank.palm.service.snowflake.config;

import com.life.bank.palm.service.snowflake.SnowflakeIdUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author: 薯条哥搞offer
 * @date: 2024-04-15
 * @desc:
 */
@Component
public class SnowflakeIdWorkerAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(SnowflakeIdWorkerAutoConfiguration.class);
    private final RedissonClient redissonClient;
    private final Integer dataCenterId = 1;
    private static final int MOD = 32;
    @Value("${spring.application.name}")
    private String appNameKey;
    public static Integer machineId;
    private static String localIp;

    private String getIpAddress() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        return address.getHostAddress();
    }

    @Bean
    @ConditionalOnMissingBean
    public SnowflakeIdWorker initMachineId() throws Exception {
        localIp = this.getIpAddress();
        log.info("获取本机ip信息：localIp: {}", localIp);
        long ip = Long.parseLong(localIp.replaceAll("\\.", ""));
        machineId = (int)(ip % 32);
        this.createMachineId();
        log.info("初始化id生成器，machine_id: {}", machineId);
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker((long)machineId, (long)this.dataCenterId);
        SnowflakeIdUtils.setSnowflakeIdWorker(snowflakeIdWorker);
        return snowflakeIdWorker;
    }

    @PreDestroy
    public void destroyMachineId() {
        this.redissonClient.getBucket(this.appNameKey + this.dataCenterId + machineId).delete();
    }

    public void createMachineId() {
        try {
            Boolean flag = this.registerMachine(machineId, localIp);
            if (flag) {
                this.updateExpTimeThread();
                return;
            }

            if (!this.checkIfCanRegister()) {
                this.getRandomMachineId();
            } else {
                log.warn("Redis中端口冲突了，使用 0-31 之间未占用的Id " + machineId + " IP:" + localIp);
            }

            this.createMachineId();
        } catch (Exception var2) {
            log.error("Redis连接异常,不能正确注册雪花机器号 " + machineId + " IP:" + localIp, var2);
            log.warn("使用临时方案，获取 32 - 63 之间的随机数作为机器号，请及时检查Redis连接");
            this.getRandomMachineId();
        }

    }

    private Boolean checkIfCanRegister() {
        boolean flag = true;

        for(int i = 0; i < 32; ++i) {
            flag = this.redissonClient.getBucket(this.appNameKey + this.dataCenterId + i).isExists();
            if (!flag) {
                machineId = i;
                break;
            }
        }

        return !flag;
    }

    private void updateExpTimeThread() {
        (new Timer(localIp)).schedule(new TimerTask() {
            public void run() {
                Boolean isLocalIp = SnowflakeIdWorkerAutoConfiguration.this.checkIsLocalIp(String.valueOf(SnowflakeIdWorkerAutoConfiguration.machineId));
                if (isLocalIp) {
                    SnowflakeIdWorkerAutoConfiguration.log.info("IP一致，更新过期时间 ip:{}, machineId:{}, time:{}", new Object[]{SnowflakeIdWorkerAutoConfiguration.localIp, SnowflakeIdWorkerAutoConfiguration.machineId, new Date()});
                    SnowflakeIdWorkerAutoConfiguration.this.redissonClient.getBucket(SnowflakeIdWorkerAutoConfiguration.this.appNameKey + SnowflakeIdWorkerAutoConfiguration.this.dataCenterId + SnowflakeIdWorkerAutoConfiguration.machineId).expire(86400L, TimeUnit.SECONDS);
                } else {
                    SnowflakeIdWorkerAutoConfiguration.log.info("重新生成机器ID ip:{}, machineId:{}, time:{}", new Object[]{SnowflakeIdWorkerAutoConfiguration.localIp, SnowflakeIdWorkerAutoConfiguration.machineId, new Date()});
                    SnowflakeIdWorkerAutoConfiguration.this.getRandomMachineId();
                    SnowflakeIdWorkerAutoConfiguration.this.createMachineId();
                    SnowflakeIdWorker.setWorkerId((long) SnowflakeIdWorkerAutoConfiguration.machineId);
                    SnowflakeIdWorkerAutoConfiguration.log.info("Timer->thread->name:{}", Thread.currentThread().getName());
                    this.cancel();
                }

            }
        }, 10000L, 82800000L);
    }

    public void getRandomMachineId() {
        Random random = new Random();
        machineId = random.nextInt(31) + 31;
    }

    private Boolean checkIsLocalIp(String machineId) {
        RBucket<String> stringBucket = this.redissonClient.getBucket(this.appNameKey + this.dataCenterId + machineId);
        if (stringBucket.isExists()) {
            String ip = (String)stringBucket.get();
            log.info("checkIsLocalIp -> redisIp: {}, localIp: {}", ip, localIp);
            return localIp.equals(ip);
        } else {
            return false;
        }
    }

    private Boolean registerMachine(Integer machineId, String localIp) {
        RBucket<String> stringBucket = this.redissonClient.getBucket(this.appNameKey + this.dataCenterId + machineId);
        if (stringBucket.isExists()) {
            String value = (String)stringBucket.get();
            if (localIp.equals(value)) {
                stringBucket.expire(86400L, TimeUnit.SECONDS);
                return true;
            } else {
                return false;
            }
        } else {
            stringBucket.set(localIp, 86400L, TimeUnit.SECONDS);
            return true;
        }
    }

    @Autowired
    public SnowflakeIdWorkerAutoConfiguration(final RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
}

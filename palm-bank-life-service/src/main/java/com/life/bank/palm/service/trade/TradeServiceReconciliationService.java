package com.life.bank.palm.service.trade;

import com.life.bank.palm.common.annotations.LogAspectAnnotation;
import com.life.bank.palm.common.result.CommonResponse;
import com.life.bank.palm.common.utils.CheckUtil;
import com.life.bank.palm.service.snowflake.SnowflakeIdUtils;
import com.life.bank.palm.service.trade.enums.TradeChannelEnum;
import com.life.bank.palm.service.trade.enums.TradeTypeEnum;
import com.life.bank.palm.service.trade.factory.TradeRecordFactory;
import com.life.bank.palm.service.user.utils.RequestContextUtil;
import com.life.bank.palm.dao.trade.mapper.TradeRecordMapper;
import com.life.bank.palm.dao.trade.pojo.TradeRecordPO;
import com.life.bank.palm.dao.user.mapper.UserMapper;
import com.life.bank.palm.dao.user.pojo.UserPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ：麻薯哥搞offer
 * @description ：TradeServiceReconciliationService
 * @date ：2024/04/08 22:58
 */
@Slf4j
@Service
public class TradeServiceReconciliationService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TradeRecordMapper recordMapper;
    @Autowired
    private RedissonClient redissonClient;

    private static final String TRADE_UNIQUE_CODE_KEY = "palm-bank-trade-unique-key-%d";


    public List<UserPO> searchUserForTrade(String keywords) {
        if (StringUtils.isBlank(keywords)) {
            return userMapper.selectAllByIsDelete(NumberUtils.INTEGER_ZERO);
        }
        return userMapper.selectAllByNicknameLikeOrCardIdLike(keywords, NumberUtils.INTEGER_ZERO);
    }



    public List<TradeChannelEnum> getAllTradeEnum() {
        return Arrays.stream(TradeChannelEnum.values()).collect(Collectors.toList());
    }

    public Long getTradeCode() {
        RBucket<Long> codeCache = redissonClient.getBucket(String.format(TRADE_UNIQUE_CODE_KEY, RequestContextUtil.getCurrentUserId()));
        if (codeCache.isExists()) {
            return codeCache.get();
        }
        long uniqueCode = SnowflakeIdUtils.nextId();
        codeCache.setAsync(uniqueCode, 5, TimeUnit.MINUTES);
        return uniqueCode;
    }

    private void deleteTradeCacheCode(Integer userId) {
        RBucket<Long> codeCache = redissonClient.getBucket(String.format(TRADE_UNIQUE_CODE_KEY, userId));
        codeCache.deleteAsync();
    }

    /**
     * 充值接口
     */
    @Transactional(rollbackFor = Exception.class)
    public UserPO doRecharge(Long token, String amount, TradeChannelEnum tradeChannel) {
        UserPO userPO = RequestContextUtil.getCurrentUser();
        Long tradeCode = this.getTradeCode();
        BigDecimal tradeAmount = new BigDecimal(amount);
        BigDecimal myBalance = new BigDecimal(userPO.getBalance());
        BigDecimal newBalance = myBalance.add(tradeAmount);

        CheckUtil.Biz.INSTANCE
                .noNull(token, "交易已过期")
                .isTrue(token.equals(tradeCode), "交易已过期")
                .isTrue(NumberUtils.INTEGER_ONE.equals(tradeAmount.compareTo(new BigDecimal(0L))), "转账金额不能为负");

        userPO.setBalance(newBalance.toString());
        TradeRecordPO recordPO = TradeRecordFactory.buildRecordForRecharge(userPO, tradeAmount, tradeChannel, TradeTypeEnum.RECHARGE);
        userMapper.updateSelective(userPO);
        recordMapper.insertSelective(recordPO);
        return userPO;
    }

    /**
     * 提现接口(后续我们直接合并充值和提现接口，提升代码复用)
     */
    @Transactional(rollbackFor = Exception.class)
    public UserPO doWithdraw(Long token, String amount, TradeChannelEnum tradeChannel) {
        UserPO userPO = RequestContextUtil.getCurrentUser();
        Long tradeCode = this.getTradeCode();
        BigDecimal withdrawAmount = new BigDecimal(amount);
        BigDecimal myBalance = new BigDecimal(userPO.getBalance());
        BigDecimal newBalance = myBalance.subtract(withdrawAmount);

        CheckUtil.Biz.INSTANCE
                .noNull(token, "交易已过期")
                .isTrue(token.equals(tradeCode), "交易已过期")
                .isTrue(NumberUtils.INTEGER_ONE.equals(withdrawAmount.compareTo(new BigDecimal(0L))), "提现金额不能为负")
                .isTrue(NumberUtils.INTEGER_ONE.equals(newBalance.compareTo(new BigDecimal(0L))), "余额不足");

        userPO.setBalance(newBalance.toString());
        TradeRecordPO recordPO = TradeRecordFactory.buildRecordForRecharge(userPO, withdrawAmount, tradeChannel, TradeTypeEnum.WITHDRAW);
        userMapper.updateSelective(userPO);
        recordMapper.insertSelective(recordPO);
        return userPO;
    }

    @Transactional(rollbackFor = Exception.class)
    @LogAspectAnnotation(paramsEnabled = false, resultEnabled = false)
    public Boolean doTrade(Long token, TradeRecordPO recordPO) {
        UserPO payeeUser = userMapper.selectOneByIdAndIsDelete(recordPO.getPayeeUserId(), NumberUtils.INTEGER_ZERO);
        UserPO me = RequestContextUtil.getCurrentUser();
        // 拿到验证code之后立即删除
        Long tradeCode = this.getTradeCode();
        this.deleteTradeCacheCode(me.getId());

        BigDecimal tradeAmount = new BigDecimal(recordPO.getAmount());
        BigDecimal payeeBalance = new BigDecimal(me.getBalance());
        BigDecimal myBalance = new BigDecimal(me.getBalance());

        recordPO.setPayUserId(me.getId());
        recordPO.setTradeType(TradeTypeEnum.TRADE.getCode());


        CheckUtil.Biz.INSTANCE
                .noNull(payeeUser, "交易用户异常")
                .noNull(token, "请求已过期")
                .isTrue(token.equals(tradeCode), "交易已过期")
                .isTrue(NumberUtils.INTEGER_ONE.equals(tradeAmount.compareTo(new BigDecimal(0L))), "转账金额不能为负")
                .isTrue(NumberUtils.INTEGER_MINUS_ONE.equals(tradeAmount.compareTo(myBalance)), "当前账户余额不足")
                .isTrue(recordPO.getPayAccount().equals(me.getCardId()), "转账卡号不合法")
                .isTrue(recordPO.getPayeeAccount().equals(payeeUser.getCardId()), "转账卡号不合法")
                .isTrue(!me.getId().equals(payeeUser.getId()), "不能自己给自己转账");

        me.setBalance(myBalance.subtract(tradeAmount).toString());
        payeeUser.setBalance(payeeBalance.add(tradeAmount).toString());

        RLock lock = redissonClient.getLock(String.valueOf(token));
        try {
            boolean locked = lock.tryLock();
            if (!locked) {
                // 获取锁失败，说明已经有转账行为
                return false;
            }
            userMapper.updateSelective(me);
            userMapper.updateSelective(payeeUser);
            recordMapper.insertSelective(recordPO);
        } finally {
            lock.unlock();
        }
        return true;
    }

    public List<TradeRecordPO> getMyTradeRecords() {
        Integer currentUserId = RequestContextUtil.getCurrentUserId();
        return recordMapper.selectAllByPayUserIdAndIsDelete(currentUserId, NumberUtils.INTEGER_ZERO);
    }




}

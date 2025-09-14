package com.life.bank.palm.service.user;

import com.life.bank.palm.common.utils.CheckUtil;
import com.life.bank.palm.common.utils.SmsSendUtil;
import com.life.bank.palm.service.user.enums.LoginPlatformEnum;
import com.life.bank.palm.service.user.factory.UserPOFactory;
import com.life.bank.palm.service.user.utils.AESEncryptUtil;
import com.life.bank.palm.service.user.utils.PhoneCheckUtil;
import com.life.bank.palm.service.user.utils.RequestContextUtil;
import com.life.bank.palm.dao.user.mapper.UserMapper;
import com.life.bank.palm.dao.user.mapper.UserTokenMapper;
import com.life.bank.palm.dao.user.pojo.UserPO;
import com.life.bank.palm.dao.user.pojo.UserTokenPO;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author ：麻薯哥搞offer
 * @description ：UserLoginService
 * @date ：2024/04/04 21:37
 */
@Service
public class UserLoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserTokenMapper userTokenMapper;
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 缓存验证码的redis key
     */
    public static final String VERIFICATION_CODE_KEY = "ssp.note.palm.bank.life.store.%s.token";

    /**
     * 缓存验证码redis key过期时间(分)
     */
    public static final Integer VERIFICATION_CODE_EXPIRE_TIME = 3;


    /**
     * 登录接口
     */
    public String login(String phone, String password) {
        UserPO userPO = userMapper.selectOneByPhoneAndIsDelete(phone, NumberUtils.INTEGER_ZERO);
        CheckUtil.Biz.INSTANCE
                .noNull(userPO, "手机号不存在")
                .isTrue(password.equals(AESEncryptUtil.decrypt(userPO.getPassword())), "密码错误");
        return this.restoreLoginToken(userPO);
    }

    /**
     * 获取验证码接口
     */
    public void sendVerificationCode(String phone) {
        CheckUtil.Biz.INSTANCE
                .isTrue(PhoneCheckUtil.checkPhoneValidate(phone), "输入手机号不合法");

        RBucket<String> codeCache = redissonClient.getBucket(String.format(VERIFICATION_CODE_KEY, phone));
        if (codeCache.isExists()) {
            SmsSendUtil.sendMobile(phone, codeCache.get());
            return;
        }

//        String code = RandomStringUtils.random(4, false, true);
        String code = "2024";
        codeCache.setAsync(code, VERIFICATION_CODE_EXPIRE_TIME, TimeUnit.MINUTES);
        SmsSendUtil.sendMobile(phone, code);
    }

    /**
     * 主动删除缓存验证码
     */
    private void invalidateTokenCache(String phone) {
        RBucket<String> stringRBucket = redissonClient.getBucket(String.format(VERIFICATION_CODE_KEY, phone));
        stringRBucket.deleteAsync();
    }

    /**
     * 注册接口
     */
    public String register(String phone, String verificationCode, String password) {
        // 验证输入合法性
        CheckUtil.Biz.INSTANCE
                .strNotBlank(verificationCode, "验证码不能为空")
                .isTrue(verificationCode.length() == 4, "验证码长度不符")
                .strNotBlank(password, "密码不能为空")
                .isTrue(password.length() > 7, "密码不能低于8位数");

        // 根据手机号查询用户，是否存在
        UserPO userPO = userMapper.selectOneByPhoneAndIsDelete(phone, NumberUtils.INTEGER_ZERO);
        CheckUtil.Biz.INSTANCE
                .isTrue(userPO == null, "当前手机号已注册，去登录");

        // 验证 验证码
        RBucket<String> stringRBucket = redissonClient.getBucket(String.format(VERIFICATION_CODE_KEY, phone));
        String code = stringRBucket.get();
        CheckUtil.Biz.INSTANCE
                .strNotBlank(code, "验证码已过期")
                .isTrue(verificationCode.equals(code), "验证码不正确");

        UserPO initUser = UserPOFactory.initUser(phone, AESEncryptUtil.encrypt(password));
        userMapper.insertSelective(initUser);
        this.invalidateTokenCache(phone);
        return this.restoreLoginToken(initUser);
    }

    /**
     * 更新数据库token表私有方法
     */
    private String restoreLoginToken(UserPO userPO) {
        String token = RandomStringUtils.randomAlphanumeric(32);
        userTokenMapper.updateIsDeleteByUserIdAndPlatform(NumberUtils.INTEGER_ONE, userPO.getId(), LoginPlatformEnum.PC.getCode());
        UserTokenPO tokenPO = UserPOFactory.initUserToken(userPO.getId(), token, LoginPlatformEnum.PC);
        userTokenMapper.insertSelective(tokenPO);
        RequestContextUtil.initUser(userPO);
        return token;
    }

    /**
     * 登出接口：删除token。web层清除cookie
     */
    public void logout(String token) {
        userTokenMapper.updateIsDeleteByToken(NumberUtils.INTEGER_ONE, token);
    }

    /**
     * 更新用户信息
     */
    public UserPO updateAccount(UserPO updateUser) {
        CheckUtil.Biz.INSTANCE
                .noNull(updateUser, "入参不合法");
        UserPO userPO = userMapper.selectOneByIdAndIsDelete(RequestContextUtil.getCurrentUserId(), NumberUtils.INTEGER_ZERO);

        this.convertUpdateInfoToUser(updateUser, userPO);
        userMapper.updateSelective(userPO);
        return userPO;
    }


    /**
     * 根据token查询用户信息：主要是给拦截器用
     */
    public UserPO parseUserByToken(String token) {
        UserTokenPO userTokenPO = userTokenMapper.selectOneByTokenAndPlatformAndIsDelete(token, LoginPlatformEnum.PC.getCode(), NumberUtils.INTEGER_ZERO);
        // token 已经过期
        if (userTokenPO == null || userTokenPO.getExpireTime().before(new Date())) {
            return null;
        }
        return userMapper.selectOneByIdAndIsDelete(userTokenPO.getUserId(), NumberUtils.INTEGER_ZERO);
    }

    public String doUpdateUserPassword(String phone, String verificationCode, String password) {
        CheckUtil.Biz.INSTANCE
                .strNotBlank(verificationCode, "验证码不能为空")
                .isTrue(verificationCode.length() == 4, "验证码长度不符")
                .strNotBlank(password, "密码不能为空")
                .isTrue(password.length() > 7, "密码不能低于8位数");
        // 验证 验证码
        RBucket<String> stringRBucket = redissonClient.getBucket(String.format(VERIFICATION_CODE_KEY, phone));
        String code = stringRBucket.get();
        CheckUtil.Biz.INSTANCE
                .strNotBlank(code, "验证码已过期")
                .isTrue(verificationCode.equals(code), "验证码不正确");

        // 保存密码
        UserPO userPO = userMapper.selectOneByPhoneAndIsDelete(phone, NumberUtils.INTEGER_ZERO);
        CheckUtil.Biz.INSTANCE
                .noNull(userPO, "用户不存在")
                .isTrue(!password.equals(AESEncryptUtil.decrypt(userPO.getPassword())), "密码修改前一致");
        userPO.setPassword(AESEncryptUtil.encrypt(password));
        userPO.setUpdateTime(null);
        userMapper.updateSelective(userPO);

        this.invalidateTokenCache(phone);

        // 更新token
        return this.restoreLoginToken(userPO);
    }




    /**
     *
     */
    private void convertUpdateInfoToUser(UserPO updateUser, UserPO existUser) {
        /*if (StringUtils.isNotBlank(updateUser.getLogo())) {
            existUser.setLogo(updateUser.getLogo());
        }*/
        if (StringUtils.isNotBlank(updateUser.getNickname())) {
            existUser.setNickname(updateUser.getNickname());
        }
        if (StringUtils.isNotBlank(updateUser.getCardId())) {
            existUser.setCardId(updateUser.getCardId());
        }
        if (StringUtils.isNotBlank(updateUser.getSchoolName())) {
            existUser.setSchoolName(updateUser.getSchoolName());
        }
        existUser.setUpdateTime(null);
    }
}

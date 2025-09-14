package com.life.bank.palm.service.user.factory;

import com.life.bank.palm.dao.user.pojo.UserPO;
import com.life.bank.palm.dao.user.pojo.UserTokenPO;
import com.life.bank.palm.service.snowflake.SnowflakeIdUtils;
import com.life.bank.palm.service.user.enums.LoginPlatformEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.security.SecureRandom;
import java.util.Date;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/04/05 22:31
 */
public class UserPOFactory {

    public static UserTokenPO initUserToken(Integer userId, String token, LoginPlatformEnum loginPlatform) {
        UserTokenPO tokenPO = new UserTokenPO();
        tokenPO.setToken(token);
        tokenPO.setUserId(userId);
        tokenPO.setPlatform(loginPlatform.getCode());
        tokenPO.setExpireTime(DateUtils.addDays(new Date(), 30));
        return tokenPO;

    }

    public static UserPO initUser(String phone, String encryptPassword) {
        UserPO userPO = new UserPO();
        userPO.setPhone(phone);
        long l = SnowflakeIdUtils.nextId();
        long random19DigitNumber = l % 100000000000000000L;
        userPO.setCardId(String.valueOf(random19DigitNumber));
        userPO.setNickname("银行客户" + RandomStringUtils.randomAlphanumeric(8));
        userPO.setLogo("https://foruda.gitee.com/avatar/1715497260032278568/11772551_zhangdaniu1997_1715497259.png!avatar100");
        userPO.setPassword(encryptPassword);
        return userPO;
    }
}

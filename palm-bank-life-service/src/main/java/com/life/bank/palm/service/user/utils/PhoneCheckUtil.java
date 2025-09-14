package com.life.bank.palm.service.user.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author ：麻薯哥搞offer
 * @description ：手机合法性校验正则工具类
 * @date ：2024/04/06 10:44
 */
public class PhoneCheckUtil {


    private static final String REGEX = "^1[3456789]\\d{9}$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    public static boolean checkPhoneValidate(String phone) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }
        return PATTERN.matcher(phone).matches();
    }
}

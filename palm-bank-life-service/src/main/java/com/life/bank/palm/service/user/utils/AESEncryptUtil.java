package com.life.bank.palm.service.user.utils;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author ：麻薯哥搞offer
 * @description ：AESEncryptUtil
 * @date ：2024/04/05 20:21
 */
public class AESEncryptUtil {

    /**
     * token加盐
     */
    private static final String AES_SALT = "kBi1MfC82EPLRYqg";

    /**
     * 算法/加密模式/填充方式
     */
    private static final String AES_PKCS5P = "AES/ECB/PKCS5Padding";


    /**
     * 加密
     * @param str 需要加密的字符串
     * @param key 密钥
     */
    public static String encrypt(String str, String key) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("key不能为空");
        }
        try {
            if (str == null) {
                return null;
            }
            // 判断Key是否为16位
            /*if (key.length() != 16) {
                return null;
            }*/
            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance(AES_PKCS5P);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
            // 此处使用BASE64做转码功能，同时能起到2次加密的作用。
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * 解密
     *
     * @param str 需要解密的字符串
     * @param key 密钥
     */
    public static String decrypt(String str, String key) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("key不能为空");
        }
        try {
            if (str == null) {
                return null;
            }
            // 判断Key是否为16位
            if (key.length() != 16) {
                return null;
            }
            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(AES_PKCS5P);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            // 先用base64解密
            byte[] encrypted = new BASE64Decoder().decodeBuffer(str);
            try {
                byte[] original = cipher.doFinal(encrypted);
                return new String(original, StandardCharsets.UTF_8);
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 加密
     *
     * @param str 需要加密的字符串
     */
    public static String encrypt(String str) {
        return encrypt(str,AES_SALT);
    }

    /**
     * 解密
     * @param str 需要解密的字符串
     */
    public static String decrypt(String str) {
        return decrypt(str,AES_SALT);
    }


    public static void main(String[] args) {
        for (long i = 1L; i < 105L; i++) {
            String encrypt = encrypt(String.valueOf(i));
            System.out.println(encrypt);
            String decrypt = decrypt(encrypt);
            System.out.println(decrypt);
        }
    }
}

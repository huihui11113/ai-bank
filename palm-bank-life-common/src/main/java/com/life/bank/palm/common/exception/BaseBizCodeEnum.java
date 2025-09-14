package com.life.bank.palm.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：麻薯哥搞offer
 * @description ：最基本的3个业务异常:未登录、服务器错误、请求成功
 * @date ：2023/07/03 21:43
 */
@Getter
@AllArgsConstructor
public enum BaseBizCodeEnum implements ErrorCodeEnum {
    /**
     * 正常请求
     */
    SUCCESS(0, "OK"),
    /**
     * 服务器异常：对应npe、之类未捕获异常
     */
    SYSTEM_ERROR(-1, "服务器错误"),
    /**
     * 通用异常，无特殊前端逻辑的业务异常
     */
    COMMON_ERROR(1001, "参数异常"),
    /**
     * 未登录
     */
    NOT_LOGIN(999, "未登录"),


    ;
    private final Integer code;
    private final String msg;

    @Override
    public Integer getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }
}

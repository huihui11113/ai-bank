package com.life.bank.palm.web.interceptor;


import com.life.bank.palm.common.exception.BaseBizCodeEnum;
import com.life.bank.palm.common.exception.CommonBizException;
import com.life.bank.palm.common.result.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @description : 全局异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 参数校验抛出的异常
     */
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class, CommonBizException.class})
    @ResponseBody
    public CommonResponse<Object> argsExceptionHandler(HttpServletRequest req, RuntimeException e) {
        if (e instanceof CommonBizException) {
            log.warn("http请求业务异常", e);
            return CommonResponse.buildError(((CommonBizException) e).getErrorCode(), ((CommonBizException) e).getErrorMsg());
        }
        return CommonResponse.buildError(BaseBizCodeEnum.COMMON_ERROR.getErrorCode(), e.getMessage());
    }

    /**
     * 处理运行时异常
     */
    @ExceptionHandler(value = {RuntimeException.class, NullPointerException.class})
    @ResponseBody
    public CommonResponse<Object> exceptionHandler(HttpServletRequest req, RuntimeException e) {
        log.error("发生运行时异常！请求路径：{}，原因是:", req.getRequestURL(), e);
        return CommonResponse.buildError(BaseBizCodeEnum.SYSTEM_ERROR);
    }


    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResponse<Object> exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("未知异常！请求路径：{}, 原因是:", req.getRequestURL(), e);
        return CommonResponse.buildError(BaseBizCodeEnum.SYSTEM_ERROR);
    }


}

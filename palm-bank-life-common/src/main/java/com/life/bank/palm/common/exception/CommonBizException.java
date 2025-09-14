package com.life.bank.palm.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;

/**
 * @author ssp
 * @description 通用业务异常
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class CommonBizException extends RuntimeException {

    /**
     * 默认错误码
     */
    private static final Integer DEFAULT_ERROR_CODE = -1;

    private int errorCode;

    private String errorMsg;


    public CommonBizException() {
        super();
        this.errorCode = DEFAULT_ERROR_CODE;
        this.errorMsg = "未知异常";
    }

    public CommonBizException(Throwable e) {
        super(e);
        this.errorCode = DEFAULT_ERROR_CODE;
        this.errorMsg = e.getMessage();
    }

    public CommonBizException(String errorMsg) {
        super(errorMsg);
        this.errorCode = DEFAULT_ERROR_CODE;
        this.errorMsg = errorMsg;
    }

    public CommonBizException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public CommonBizException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getErrorMsg());
        this.errorCode = errorCodeEnum.getErrorCode();
        this.errorMsg = errorCodeEnum.getErrorMsg();
    }

    public CommonBizException(int errorCode, String errorMsg, Object... arguments) {
        super(MessageFormat.format(errorMsg, arguments));
        this.errorCode = errorCode;
        this.errorMsg = MessageFormat.format(errorMsg, arguments);
    }

    public CommonBizException(ErrorCodeEnum errorCodeEnum, Object... arguments) {
        super(MessageFormat.format(errorCodeEnum.getErrorMsg(), arguments));
        this.errorCode = errorCodeEnum.getErrorCode();
        this.errorMsg = MessageFormat.format(errorCodeEnum.getErrorMsg(), arguments);
    }

}

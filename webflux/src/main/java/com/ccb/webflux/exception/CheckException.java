package com.ccb.webflux.exception;

import lombok.Data;

@Data
public class CheckException extends RuntimeException {

    private static final long serialVersionUID = -4114381117201917794L;

    /**
     * 出错字段的名字
     */
    private String filedName;

    /**
     * 出错字段的值
     */
    private String filedValue;


    public CheckException() {
    }

    public CheckException(String message) {
        super(message);
    }

    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckException(Throwable cause) {
        super(cause);
    }

    public CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CheckException(String filedName, String filedValue) {
        super();
        this.filedName = filedName;
        this.filedValue = filedValue;
    }


}

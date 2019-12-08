package com.lyx.las.errors;

public class ServiceException extends RuntimeException {
    protected Integer code;

    protected String message;

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String desc) {
        this.message = desc;
    }
}
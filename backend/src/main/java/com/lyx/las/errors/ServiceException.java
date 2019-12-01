package com.lyx.las.errors;

public class ServiceException extends RuntimeException {
    protected Integer code;

    protected String desc;

    public ServiceException(Integer code, String desc) {
        super(desc);
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
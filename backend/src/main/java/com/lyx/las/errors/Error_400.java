package com.lyx.las.errors;

public class Error_400 extends ServiceException {

    public Error_400(String desc) {
        super(400, desc);
    }
}
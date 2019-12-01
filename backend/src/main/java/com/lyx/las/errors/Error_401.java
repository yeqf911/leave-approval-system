package com.lyx.las.errors;

public class Error_401 extends ServiceException {

    public Error_401(String desc) {
        super(401, desc);
    }
}
package com.lyx.las.errors;

public class Error_500 extends ServiceException {

    public Error_500(String desc) {
        super(500, desc);
    }
}
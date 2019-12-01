package com.lyx.las.errors;

public class Error_403 extends ServiceException {

    public Error_403(String desc) {
        super(403, desc);
    }
}

package com.lyx.las.errors;

public class Error_404 extends ServiceException {

    public Error_404(String desc) {
        super(404, desc);
    }
}
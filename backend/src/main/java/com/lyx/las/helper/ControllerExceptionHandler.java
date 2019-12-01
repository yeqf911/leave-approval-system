package com.lyx.las.helper;

import com.lyx.las.errors.Error_400;
import com.lyx.las.errors.Error_401;
import com.lyx.las.errors.Error_404;
import com.lyx.las.errors.Error_500;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Error_400.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handlerError_400(Error_400 exception) {

        Map<String, Object> errorMessage = new HashMap<>();
        errorMessage.put("code", exception.getCode());
        errorMessage.put("desc", exception.getDesc());
        return errorMessage;
    }

    @ExceptionHandler(Error_401.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handlerError_401(Error_401 exception) {

        Map<String, Object> errorMessage = new HashMap<>();
        errorMessage.put("code", exception.getCode());
        errorMessage.put("desc", exception.getDesc());
        return errorMessage;
    }

    @ExceptionHandler(Error_404.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handlerError_404(Error_404 exception) {

        Map<String, Object> errorMessage = new HashMap<>();
        errorMessage.put("code", exception.getCode());
        errorMessage.put("desc", exception.getDesc());
        return errorMessage;
    }

    @ExceptionHandler(Error_500.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handlerError_500(Error_500 exception) {

        Map<String, Object> errorMessage = new HashMap<>();
        errorMessage.put("code", exception.getCode());
        errorMessage.put("desc", exception.getDesc());
        return errorMessage;
    }
}

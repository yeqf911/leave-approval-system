package com.lyx.las.helper;

import com.lyx.las.errors.*;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Error_400.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handlerError_400(Error_400 exception) {
        return makeErrorResponse(exception, "400 Bad Request");
    }

    @ExceptionHandler(Error_401.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handlerError_401(Error_401 exception) {
        return makeErrorResponse(exception, "401 Unauthorized");
    }

    @ExceptionHandler(Error_403.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handlerError_403(Error_403 exception) {
        return makeErrorResponse(exception, "403 Forbidden");
    }

    @ExceptionHandler(Error_404.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handlerError_404(Error_404 exception) {
        return makeErrorResponse(exception, "404 Not Found");
    }

    @ExceptionHandler(Error_500.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handlerError_500(Error_500 exception) {
        return makeErrorResponse(exception, "500 Internal Server Error");
    }

    private Map<String, Object> makeErrorResponse(ServiceException exception, String error) {
        Map<String, Object> errorMessage = new LinkedHashMap<>();
        errorMessage.put("timestamp", new Date().toString());
        errorMessage.put("status", exception.getCode());
        errorMessage.put("error", error);
        errorMessage.put("message", exception.getMessage());
        errorMessage.put("path", "");
        return errorMessage;
    }
}

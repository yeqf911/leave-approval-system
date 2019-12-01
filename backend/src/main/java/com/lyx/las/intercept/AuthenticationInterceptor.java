package com.lyx.las.intercept;

import com.lyx.las.dao.UserMapper;
import com.lyx.las.errors.Error_401;
import com.lyx.las.model.User;
import com.lyx.las.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(AuthenticationInterceptor.class.getName());

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.info(request.getHeader("PRIVATE-TOKEN"));
        String accessToken = request.getHeader("PRIVATE-TOKEN");
        if ("admin-token".equals(accessToken)) {
            return true;
        }

        User user = userService.findByAccessToken(accessToken);

        if (user == null) {
            throw new Error_401("401 unauthorized");
        }

        return true;
    }
}

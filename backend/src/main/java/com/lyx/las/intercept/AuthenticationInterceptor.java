package com.lyx.las.intercept;

import com.lyx.las.errors.Error_400;
import com.lyx.las.errors.Error_401;
import com.lyx.las.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器，所有的请求到达后台 Controller 之前，都会经过这个拦截器预先处理一遍，用户鉴权就是在这个拦截器里面做的
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(AuthenticationInterceptor.class.getName());

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.info(request.getHeader("Access-Token"));
        String accessToken = request.getHeader("Access-Token");


        // 鉴权的过程
        // 1. 检查有没有在header中传入 Access-Token，如果没有的话，鉴权失败，返回400状态码
        // 2. 如果header中传入了Access-Token,但该token并没有存在于数据库中（即通过该token查询不到任何一个用户
        // ，说明传入的是一个无效的token，则鉴权失败，返回401状态码
        if (accessToken == null) {
            throw new Error_400("missing Access-Token in headers");
        }

        UserDetails user = userService.findByAccessToken(accessToken);

        if (user == null) {
            throw new Error_401("invalid access token");
        }

        // 可以通过Token查询到正常的用户，说明该token就是有效的，鉴权通过
        // 将查询到的用户（User对象）放到Spring上下文中，以供后续的逻辑处理中可以随时取出来
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return true;
    }
}

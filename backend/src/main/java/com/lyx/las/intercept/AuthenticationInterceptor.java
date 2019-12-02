package com.lyx.las.intercept;

import com.lyx.las.dao.UserMapper;
import com.lyx.las.errors.Error_400;
import com.lyx.las.errors.Error_401;
import com.lyx.las.errors.Error_404;
import com.lyx.las.model.User;
import com.lyx.las.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

        if (accessToken == null) {
            throw new Error_400("missing Access-Token in headers");
        }

        UserDetails user = userService.findByAccessToken(accessToken);

        if (user == null) {
            throw new Error_401("invalid access token");
        }

        // 将用户放到Spring上下文中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return true;
    }
}

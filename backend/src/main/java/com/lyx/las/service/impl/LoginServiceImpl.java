package com.lyx.las.service.impl;

import com.lyx.las.errors.Error_401;
import com.lyx.las.errors.Error_404;
import com.lyx.las.model.User;
import com.lyx.las.service.LoginService;
import com.lyx.las.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;


    @Override
    public User login(String username, String password) {
        User user = userService.findByUsername(username);
        if (user != null) {
            validate(user, password);
            return user;
        } else {
            throw new Error_404("user is not exist");
        }
    }

    private void validate(User user, String password) {
        if (!password.equals(user.getPassword())) {
            throw new Error_401("password is not valid");
        }
    }

}

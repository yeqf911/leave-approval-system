package com.lyx.las.service.impl;

import com.lyx.las.dao.UserMapper;
import com.lyx.las.errors.Error_500;
import com.lyx.las.helper.AccessTokenHelper;
import com.lyx.las.model.User;
import com.lyx.las.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void create(User user) {
        try {
            user.setAccessToken(AccessTokenHelper.getInstance().generateAccessToken());
            userMapper.create(user);
        } catch (DataAccessException e) {
            throw new Error_500(e.toString());
        }
    }

    @Override
    public void delete(int id) {
        userMapper.destroy(id);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User findByAccessToken(String accessToken) {
        return userMapper.findByAccessToken(accessToken);
    }
}

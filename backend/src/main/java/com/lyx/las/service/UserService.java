package com.lyx.las.service;

import com.lyx.las.dao.UserMapper;
import com.lyx.las.errors.Error_500;
import com.lyx.las.helper.AccessTokenHelper;
import com.lyx.las.model.Course;
import com.lyx.las.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void create(User user) {
        try {
            user.setAccessToken(AccessTokenHelper.getInstance().generateAccessToken());
            userMapper.create(user);
        } catch (DataAccessException e) {
            throw new Error_500(e.toString());
        }
    }

    public void delete(int id) {
        userMapper.destroy(id);
    }

    public void update(User user) {
        userMapper.update(user);
    }

    public User findById(int id) {
        return userMapper.find(id);
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public User findByAccessToken(String accessToken) {
        return userMapper.findByAccessToken(accessToken);
    }
}

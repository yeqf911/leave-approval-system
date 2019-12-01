package com.lyx.las.service;

import com.lyx.las.model.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {

    void create(User user);

    void delete(int id);

    void update(User user);

    User find(int id);

    User findByUsername(String username);

    User findByAccessToken(String accessToken);

}

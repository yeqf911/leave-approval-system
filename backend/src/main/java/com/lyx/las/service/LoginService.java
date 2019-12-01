package com.lyx.las.service;


import com.lyx.las.model.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    User login(String username, String password);

}

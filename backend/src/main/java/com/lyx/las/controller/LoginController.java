package com.lyx.las.controller;

import com.lyx.las.model.User;
import com.lyx.las.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestParam(value = "username") String username,
                                        @RequestParam(value = "password") String password) {
        User user = loginService.login(username, password);

        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.set("Access-Token", user.getAccessToken());
        return ResponseEntity.ok().headers(responseHeaders).body("success");
    }
}

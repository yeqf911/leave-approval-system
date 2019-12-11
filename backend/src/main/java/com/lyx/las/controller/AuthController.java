package com.lyx.las.controller;

import com.lyx.las.errors.Error_400;
import com.lyx.las.model.User;
import com.lyx.las.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> userInfo) {
        String username = userInfo.getOrDefault("username", "");
        String password = userInfo.getOrDefault("password", "");

        if ("".equals(username) || "".equals(password)) {
            throw new Error_400("username or password cannot be blank");
        }

        User user = authService.login(username, password);

        Map<String, String> body = new HashMap<>();
        body.put("access_token", user.getAccessToken());
        body.put("user_role", user.getRole());

        return ResponseEntity.ok().body(body);
    }
}

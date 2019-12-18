package com.lyx.las.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lyx.las.errors.Error_400;
import com.lyx.las.helper.JsonViewHelper;
import com.lyx.las.model.User;
import com.lyx.las.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users") // 表示该类里面所有请求都必须以 /users 开头
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 创建用户
     * @param user 用户的信息
     * @param bindingResult 检测请求参数是否合法
     * @return 返回创建的用户信息
     */
    @PostMapping
    @JsonView(JsonViewHelper.SimpleView.class)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<User> create(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new Error_400(JsonViewHelper.getErrorMessages(bindingResult.getAllErrors()));
        }

        userService.create(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * 根据用户ID查询一个用户
     * @param id 用户的ID
     * @return 用户信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @JsonView(JsonViewHelper.SimpleView.class)
    public ResponseEntity<User> get(@PathVariable("id") int id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    /**
     * 获取当前登录的用户信息，根据 access_token 查询到的用户
     * @param auth 认证
     * @return 用户信息
     */
    @GetMapping("/self") // get请求的URL就是 /users/self
    @JsonView(JsonViewHelper.SimpleView.class)
    public ResponseEntity<User> getSelf(Authentication auth) {
        // 从Spring上下文（Context）中取出之前在拦截器中保存到上下文中的User对象。
        return ResponseEntity.ok().body((User) auth.getPrincipal());
    }
}

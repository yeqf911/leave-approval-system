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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @JsonView(JsonViewHelper.SimpleView.class)
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<User> create(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new Error_400(JsonViewHelper.getErrorMessages(bindingResult.getAllErrors()));
        }

        userService.create(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @JsonView(JsonViewHelper.SimpleView.class)
    public ResponseEntity<User> get(@PathVariable("id") int id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/self")
    @JsonView(JsonViewHelper.SimpleView.class)
    public ResponseEntity<User> getSelf(Authentication auth) {
        return ResponseEntity.ok().body((User) auth.getPrincipal());
    }
}

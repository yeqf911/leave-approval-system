package com.lyx.las.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lyx.las.errors.Error_400;
import com.lyx.las.helper.JsonViewHelper;
import com.lyx.las.model.User;
import com.lyx.las.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @JsonView(JsonViewHelper.SimpleView.class)
    public ResponseEntity<Object> create(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
//            ErrorResponse error = new ErrorResponse(400, JsonViewHelper.getErrorMessages(bindingResult.getAllErrors()));
//            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            throw new Error_400(JsonViewHelper.getErrorMessages(bindingResult.getAllErrors()));
        }

        userService.create(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseBody get(@PathVariable("id") int id) {
        return null;
    }


}

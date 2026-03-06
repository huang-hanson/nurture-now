package com.health.fitness.controller;

import com.health.fitness.common.Result;
import com.health.fitness.entity.User;
import com.health.fitness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public Result<User> saveUser(@RequestBody User user) {
        return Result.success(userService.saveOrUpdateUser(user));
    }

    @GetMapping("/openId/{openId}")
    public Result<User> getUserByOpenId(@PathVariable String openId) {
        User user = userService.getUserByOpenId(openId);
        return Result.success(user);
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return Result.success(user);
    }
}

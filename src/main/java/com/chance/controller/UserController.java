package com.chance.controller;


import com.chance.entity.User;
import com.chance.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chance
 * @since 2020-08-22
 */
@Api("UserController")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "select",notes = "查询")
    @PostMapping("/list")
    public List<User> select() {
        List<User> users = userService.list();
        return users;
    }
}

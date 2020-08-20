package com.chance.controller;

import com.chance.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-08-20
 */
@Api("UserController")
@RestController
@RequestMapping("/person")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "showUser",notes = "展示人员信息")
    @GetMapping("/show")
    public String showUser(@RequestParam("id") int id) {
        return userService.queryUserById(id);
    }
}

package com.chance.controller;


import com.chance.common.api.CommonRsp;
import com.chance.component.RedisUtils;
import com.chance.entity.User;
import com.chance.entity.vo.DemoUser;
import com.chance.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chance
 * @since 2020-08-22
 */
@Api("UserController")
@RestController
@RequestMapping("/auth/user")
public class UserController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IUserService userService;

    public CommonRsp login() {
        // 校验用户名密码

        // 获取token
        // int jwtMaxAgeMinutes = 60 * 24 * 7
        // int exp = 3600;
        // String token = JWTUtils.getToken(username,jwtMaxAgeMinutes);
        // redisUtils.set(key,token,exp);

        // 返回token，用户信息，数据权限
        return null;
    }

    @ApiOperation(value = "select", notes = "查询")
    @PostMapping("/list")
    public List<User> select() {
        List<User> users = userService.list();
        return users;
    }

    @ApiOperation(value = "select", notes = "查询")
    @RequestMapping("/addUser1")
    public String addUser1(String username, String password) {
        System.out.println("username is:" + username);
        System.out.println("password is:" + password);
        return "success";
    }

    @RequestMapping("/addUser2")
    public String addUser2(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username is:" + username);
        System.out.println("password is:" + password);
        return "success";
    }

    @RequestMapping("/addUser3")
    public String addUser3(DemoUser user) {
        System.out.println("username is:" + user.getUsername());
        System.out.println("password is:" + user.getPassword());
        return "success";
    }

    @GetMapping("/addUser4/{username}/{password}")
    public String addUser4(@PathVariable String username, @PathVariable String password) {
        System.out.println("username is:" + username);
        System.out.println("password is:" + password);
        return "success";
    }

    @PostMapping("/addUser5")
    public String addUser5(@ModelAttribute("user") DemoUser user) {
        System.out.println("username is:" + user.getUsername());
        System.out.println("password is:" + user.getPassword());
        return "success";
    }

    @RequestMapping(value = "/addUser6")
    public String addUser6(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println("username is:" + username);
        System.out.println("password is:" + password);
        return "success";
    }

    @RequestMapping(value = "/addUser7")
    public String addUser7(@RequestBody DemoUser user) {
        System.out.println("username is:" + user.getUsername());
        System.out.println("password is:" + user.getPassword());
        return "success";
    }

    @PostMapping(value = "/addUser8")
    public String addUser8(@RequestBody HashMap map) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        System.out.println("username is:" + username);
        System.out.println("password is:" + password);
        return "success";
    }

}

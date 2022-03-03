package top.xinsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xinsin.pojo.User;
import top.xinsin.services.UserService;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author xinxin
 * @Date 2021/12/11 18:06
 * @Version 1.0
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    //
    //

    //登录接口
    @PostMapping("/user/api/login")
    public String login(@RequestBody User user,HttpServletResponse response) {return userService.canLogin(user,response).toString();}
    //添加用户
    @PostMapping("/user/api/adduser")
    public String addUser(@RequestBody User user) {return userService.addUser(user).toString();}
    //修改密码
    @PostMapping("/user/api/changepassword")
    public String changePassword(@RequestBody User user) {return userService.changePassword(user).toString();}
    //修改头像
    @PostMapping("/user/api/setHeadPortrait")
    public String setHeadPortrait(@RequestBody User user){return userService.setHeadPortrait(user).toString();}
    @GetMapping("/user/api/getUserHeadPortrait/{username}")
    public String getUserHeadPortrait(@PathVariable String username){
        return userService.getUserHeadPortrait(username).toString();
    }
}

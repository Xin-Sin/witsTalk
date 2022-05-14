package top.xinsin.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xinsin.Utils.ResultData;
import top.xinsin.pojo.User;
import top.xinsin.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
    public ResultData<JSONObject> login(@RequestBody User user, HttpServletResponse response) {
        return userService.canLogin(user,response);
    }
    //添加用户
    @PostMapping("/user/api/adduser")
    public ResultData<JSONObject> addUser(@RequestBody User user) {return userService.addUser(user);}
    //修改密码
    @PostMapping("/user/api/changepassword")
    public ResultData<JSONObject> changePassword(@RequestBody User user) {return userService.changePassword(user);}
    //修改头像
    @PostMapping("/user/api/setHeadPortrait")
    public ResultData<String > setHeadPortrait(@RequestBody User user){return userService.setHeadPortrait(user);}
    @GetMapping("/user/api/getUserHeadPortrait/{username}")
    public String getUserHeadPortrait(@PathVariable String username){
        return userService.getUserHeadPortrait(username).toString();
    }
    @GetMapping("/user/api/getOnlineUser")
    public ResultData<List<User>> getOnlineUser(){
        return userService.getOnlineUser();
    }
}

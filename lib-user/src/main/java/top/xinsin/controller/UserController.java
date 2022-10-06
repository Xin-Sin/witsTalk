package top.xinsin.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xinsin.pojo.User;
import top.xinsin.services.UserService;
import top.xinsin.utils.ResultData;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author xinxin
 * Created On 2021/12/11 18:06
 * @version 1.0
 */
@RestController
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * 登录接口
     * @param user 用户
     * @param response 请求体
     * @return 是否可以登陆
     */
    @PostMapping("/user/api/login")
    public ResultData<JSONObject> login(@RequestBody User user, HttpServletResponse response) {
        return userService.canLogin(user,response);
    }

    /**
     * 添加用户接口
     * @param user 需要添加的用户
     * @return 受影响行数
     */
    @PostMapping("/user/api/adduser")
    public ResultData<JSONObject> addUser(@RequestBody User user) {return userService.addUser(user);}

    /**
     *
     * @param user 需要修改密码的用户，此用户的password字段为需要为修改后的密码
     * @return 受影响行数
     */
    @PostMapping("/user/api/changepassword")
    public ResultData<JSONObject> changePassword(@RequestBody User user) {return userService.changePassword(user);}

    /**
     * 设置用户头像接口
     * @param user 需要修改头像的用户，此用户的head
     * @return 受影响行数
     */
    @PostMapping("/user/api/setHeadPortrait")
    public ResultData<String > setHeadPortrait(@RequestBody User user){return userService.setHeadPortrait(user);}

    /**
     * 获取用户头像接口
     *
     * @param username 用户名
     * @return 用户头像的base64
     */
    @GetMapping("/user/api/getUserHeadPortrait/{username}")
    public ResultData<String> getUserHeadPortrait(@PathVariable String username) {
        return userService.getUserHeadPortrait(username);
    }

    /**
     * 获取所有在线用户接口
     * @return 在线用户
     */
    @GetMapping("/user/api/getOnlineUser")
    public ResultData<List<User>> getOnlineUser(){
        return userService.getOnlineUser();
    }

    /**
     * 调试用接口
     * @return 调试信息
     */
    @GetMapping("/user/api/debug")
    public ResultData<List<User>> debug(){
        return userService.getOnlineUser();
    }
}

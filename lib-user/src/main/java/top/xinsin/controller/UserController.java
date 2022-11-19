package top.xinsin.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xinsin.pojo.User;
import top.xinsin.services.UserService;
import top.xinsin.utils.ResultData;

import javax.servlet.http.HttpServletRequest;
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
     * @param user 需要修改密码的用户，此用户的password字段为需要为修改后的密码
     * @return 受影响行数
     */
    @PostMapping("/user/api/changepassword")
    public ResultData<JSONObject> changePassword(@RequestBody User user, HttpServletRequest request) {
        return userService.changePassword(user, request);
    }

    /**
     * 设置用户头像接口
     *
     * @param user 需要修改头像的用户，此用户的head
     * @return 受影响行数
     */
    @PostMapping("/user/api/setHeadPortrait")
    public ResultData<JSONObject> setHeadPortrait(@RequestBody User user, HttpServletRequest request) {
        return userService.setHeadPortrait(user, request);
    }

    /**
     * 获取用户头像接口
     * @param username 用户名
     * @return 用户头像的base64
     */
    @GetMapping("/user/api/getUserHeadPortrait/{username}")
    public String getUserHeadPortrait(@PathVariable String username){
        return userService.getUserHeadPortrait(username).toString();
    }

    /**
     * 获取所有在线用户接口
     *
     * @return 在线用户
     */
    @GetMapping("/user/api/getOnlineUser")
    public ResultData<List<User>> getOnlineUser() {
        return userService.getOnlineUser();
    }

    /**
     * 修改用户名
     *
     * @param user    新用户
     * @param request 请求
     * @return 是否成功
     */
    @PostMapping("/user/api/changeUsername")
    public ResultData<Boolean> changeUsername(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        return userService.changeUsername(user, request, response);
    }
}

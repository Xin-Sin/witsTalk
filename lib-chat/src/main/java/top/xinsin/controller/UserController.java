package top.xinsin.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xinsin.Utils.JWTTokenUtils;
import top.xinsin.pojo.User;
import top.xinsin.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xinxin
 * @Date 2021/12/11 18:06
 * @Version 1.0
 */

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/api/login")
    public String login(@RequestBody User user,HttpServletResponse response) {
        log.info("user--->login");
        return userService.canLogin(user,response);
    }

    @PostMapping("/api/adduser")
    public String addUser(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        userService.addUser(user);
        log.info("adduser");
        return jsonObject.toJSONString();
    }

    @PostMapping("/api/changepassword")
    public String changePassword(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        userService.changePassword(user);
        log.info("changepassword");
        return jsonObject.toJSONString();
    }

}

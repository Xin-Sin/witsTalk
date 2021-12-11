package top.xinsin.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import top.xinsin.pojo.User;
import top.xinsin.services.UserService;

/**
 * @Author xinxin
 * @Date 2021/12/11 18:06
 * @Version 1.0
 */

@RestController
public class Login {
    private final Logger logger = LogManager.getLogger(Login.class);
    @Autowired
    UserService userService;

    @PostMapping("/api/login")
    public String login(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        jsonObject.put("canLogin",userService.canLogin(user));
        logger.info("login");
        return jsonObject.toJSONString();
    }

    @PostMapping("/api/adduser")
    public String addUser(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        userService.addUser(user);
        logger.info("adduser");
        return jsonObject.toJSONString();
    }

    @PostMapping("/api/changepassword")
    public String changePassword(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        userService.changePassword(user);
        logger.info("changepassword");
        return jsonObject.toJSONString();
    }
}

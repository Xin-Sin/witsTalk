package top.xinsin.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.xinsin.Utils.ResponseData;
import top.xinsin.Utils.ResultData;
import top.xinsin.pojo.User;
import top.xinsin.services.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther wzp
 * @Date 2021/12/12 10:49
 * @Version 1.0
 */
@RestController
@Slf4j
public class CheckController {
    @Autowired
    UserService userService;
    public static HashMap<User, Date> checklist = new HashMap<>();
    @PostMapping("/user/api/check")
    public ResultData<Date> Check(@RequestBody User user){
        log.info("Check args:user=" + user);
        checklist.put(user,new Date());
        userService.setOnline(user);
        return ResultData.success(checklist.get(user));
    }
}

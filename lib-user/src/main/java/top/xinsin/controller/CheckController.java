package top.xinsin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.xinsin.pojo.User;
import top.xinsin.services.UserService;
import top.xinsin.utils.ResultData;

import java.util.Date;
import java.util.HashMap;

/**
 * @author wzp
 * Created On 2021/12/12 10:49
 * @version 1.0
 */
@RestController
@Slf4j
public class CheckController {

    private final UserService userService;
    @Autowired
    public CheckController(UserService userService){
        this.userService = userService;
    }
    public static HashMap<User, Date> checklist = new HashMap<>();
    @PostMapping("/user/api/check")
    public ResultData<Date> check(@RequestBody User user){
        log.info("Check args:user=" + user);
        checklist.put(user,new Date());
        userService.setOnline(user);
        return ResultData.success(checklist.get(user));
    }
}

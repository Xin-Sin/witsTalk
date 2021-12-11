package top.xinsin.container;

import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import top.xinsin.pojo.User;

/**
 * @Author xinxin
 * @Date 2021/12/11 18:06
 * @Version 1.0
 */

@RestController
public class Login {
    private final Logger logger = LogManager.getLogger(Login.class);
    @PostMapping("/api/login")
    public String login(@RequestBody User user) {

        return user.getUsername();
    }
}

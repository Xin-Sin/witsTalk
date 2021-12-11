package top.xinsin.container;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xinxin
 * @Date 2021/12/11 18:06
 * @Version 1.0
 */

@RestController
public class Login {
    @PostMapping("/api/login")
    public String login() {
        return "dsa";
    }
}

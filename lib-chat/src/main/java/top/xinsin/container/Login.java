package top.xinsin.container;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author xinxin
 * @Date 2021/12/11 18:06
 * @Version 1.0
 */

@Controller
public class Login {
    @RequestMapping("/")
    public String hello() {
        return "dsa";
    }
}

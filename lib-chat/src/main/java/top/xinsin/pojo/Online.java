package top.xinsin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import top.xinsin.enums.Auth;

/**
 * @Author xinxin
 * @Date 2022/1/16 21:46
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class Online {
    private String username;
    private Auth auth;
    private String headPortrait;
}

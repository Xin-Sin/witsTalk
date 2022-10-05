package top.xinsin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import top.xinsin.enums.Auth;

/**
 * @author xinxin
 * Created On 2022/1/16 21:46
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class Online {
    private String username;
    private Auth auth;
    private String headPortrait;
}

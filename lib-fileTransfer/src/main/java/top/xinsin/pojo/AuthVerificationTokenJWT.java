package top.xinsin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author xinxin
 * @Date 2022/1/21 22:09
 * @Version 1.0
 */
@AllArgsConstructor
@Data
public class AuthVerificationTokenJWT {
    private String id;
    private String exp;
    private String username;
}

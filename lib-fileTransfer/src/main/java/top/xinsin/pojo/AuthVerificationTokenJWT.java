package top.xinsin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xinxin
 * @date 2022/1/21 22:09
 * @version 1.0
 */
@AllArgsConstructor
@Data
public class AuthVerificationTokenJWT {
    private String id;
    private String exp;
    private String username;
}

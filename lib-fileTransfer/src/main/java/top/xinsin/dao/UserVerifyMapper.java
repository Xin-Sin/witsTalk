package top.xinsin.dao;

import org.springframework.stereotype.Repository;
import top.xinsin.pojo.AuthVerificationTokenJwt;

/**
 * @author xinxin
 * Created On 2022/1/22 9:22
 * @version 1.0
 */
@Repository
public interface UserVerifyMapper {
    /**
     * 用户验证映射
     * @param authVerificationTokenJwt 用户的JWTToken
     * @return 用户是否在线
     */
    Integer userVerify(AuthVerificationTokenJwt authVerificationTokenJwt);
}

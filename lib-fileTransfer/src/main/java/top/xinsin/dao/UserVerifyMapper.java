package top.xinsin.dao;

import org.springframework.stereotype.Repository;
import top.xinsin.pojo.AuthVerificationTokenJWT;

/**
 * @author xinxin
 * @date 2022/1/22 9:22
 * @version 1.0
 */
@Repository
public interface UserVerifyMapper {
    Integer userVerify(AuthVerificationTokenJWT authVerificationTokenJWT);
}

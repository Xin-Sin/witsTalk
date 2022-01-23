package top.xinsin.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import top.xinsin.pojo.AuthVerificationTokenJWT;

/**
 * @Author xinxin
 * @Date 2022/1/22 9:22
 * @Version 1.0
 */
@Repository
public interface UserVerifyMapper {
    Integer userVerify(AuthVerificationTokenJWT authVerificationTokenJWT);
}

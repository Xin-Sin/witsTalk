package top.xinsin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xinsin.dao.UserVerifyMapper;
import top.xinsin.pojo.AuthVerificationTokenJWT;

/**
 * @Author xinxin
 * @Date 2022/1/22 9:21
 * @Version 1.0
 */
@Service
public class UserVerifyService {
    @Autowired
    private UserVerifyMapper userVerifyMapper;

    public boolean userVerify(AuthVerificationTokenJWT authVerificationTokenJWT){
        return userVerifyMapper.userVerify(authVerificationTokenJWT) == 1;
    }
}

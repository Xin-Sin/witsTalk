package top.xinsin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xinsin.dao.UserVerifyMapper;
import top.xinsin.pojo.AuthVerificationTokenJwt;

/**
 * @author xinxin
 * Created On 2022/1/22 9:21
 * @version 1.0
 */
@Service
public class UserVerifyService {
    private final UserVerifyMapper userVerifyMapper;
    @Autowired
    public UserVerifyService(UserVerifyMapper userVerifyMapper) {
        this.userVerifyMapper = userVerifyMapper;
    }

    public boolean userVerify(AuthVerificationTokenJwt authVerificationTokenJwt){
        return userVerifyMapper.userVerify(authVerificationTokenJwt) == 1;
    }
}

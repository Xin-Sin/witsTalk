package top.xinsin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xinsin.dao.UserMapper;
import top.xinsin.pojo.User;

/**
 * @Auther wzp
 * @Date 2021/12/11 21:21
 * @Version 1.0
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public boolean canLogin(User user){
        User user1 = userMapper.canLogin(user);
        return user1 != null;
    }
    public void addUser(User user){
        userMapper.addUser(user);
    }
}

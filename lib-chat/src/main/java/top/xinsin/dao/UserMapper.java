package top.xinsin.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.xinsin.pojo.User;

/**
 * @Auther wzp
 * @Date 2021/12/11 21:00
 * @Version 1.0
 */
@Repository
public interface UserMapper {
    User canLogin(User user);
    void addUser(User user);
    void changePassword(User user);
    void setOnline(User user);
    void setOffline(User user);
    User setHeadPortrait(User user);
}

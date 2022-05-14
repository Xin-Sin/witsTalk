package top.xinsin.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.xinsin.pojo.User;

import java.util.ArrayList;

/**
 * @author wzp
 */
@Repository
public interface UserMapper {
    /**
     * Check can user login
     * @param user user need to log in
     * @return if can ,return this user else return null
     */
    User canLogin(User user);

    /**
     * add a new user to db
     * @param user user need to add
     */
    int addUser(User user);

    /**
     * changeUser's password
     * @param user user with new password
     */
    int changePassword(User user);

    /**
     * set a user's status to online
     * @param user user need to online
     */
    int setOnline(User user);

    /**
     * set a user's status to offline
     * @param user user need to offline
     */
    int setOffline(User user);

    /**
     * set a user's headingImg
     * @param user user with new base64
     * @return this user
     */
    User setHeadPortrait(User user);

    /**
     * get users online equals 1
     * @return all online equals 1 user
     */
    ArrayList<User> getOnlineUser();

    /**
     * get a user's headingImg
     * @param username the name of this user
     * @return base64
     */
    String getUserHeadPortrait(@Param("username") String username);
}

package top.xinsin.service;

import com.mybatisflex.core.service.IService;
import top.xinsin.pojo.User;

/**
 * @author xinsin
 * Created On 2023/9/21 09:56
 * @version 1.0
 */
public interface UserService extends IService<User> {
    User getUserByUserName(String username);
    boolean register(User user);
}

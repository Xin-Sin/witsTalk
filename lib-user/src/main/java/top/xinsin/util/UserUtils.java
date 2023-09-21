package top.xinsin.util;


import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import top.xinsin.pojo.User;
import top.xinsin.service.impl.UserServiceImpl;

/**
 * @author xinsin
 * Created On 2023/9/21 10:31
 * @version 1.0
 */
public class UserUtils {
    public static User getUserByUsername(String username){
        return SpringUtil.getBean(UserServiceImpl.class).getUserByUserName(username);
    }
    public static User getCurrentUserDTO(){
        String username = SecurityUtils.getLoginName();
        if (StrUtil.isNotEmpty ( username )){
            return getByLoginName( username);
        }
        return new User();
    }
    public static User getByLoginName(String username){
        return SpringUtil.getBean(UserServiceImpl.class).getUserByUserName(username);
    }
}

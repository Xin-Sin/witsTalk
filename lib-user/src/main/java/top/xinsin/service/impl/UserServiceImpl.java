package top.xinsin.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.xinsin.mapper.UserMapper;
import top.xinsin.pojo.User;
import top.xinsin.pojo.Vo.UserVo;
import top.xinsin.service.UserService;
import top.xinsin.util.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xinsin
 * Created On 2023/9/21 09:58
 * @version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserDetailsService, UserService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userInfo = this.mapper.getUserByUserName(username);

        // 用户不存在
        if (userInfo == null) {
            throw new AccountExpiredException("用户名不存在!");
        }
        // 权限集合
        List<GrantedAuthority> authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(
                userInfo.getUsername(),
                userInfo.getPassword(),
                authorities
        );
    }

    @Override
    public User getUserByUserName(String username) {
        return this.mapper.getUserByUserName(username);
    }

    @Override
    public boolean register(User user) {
        user.setPassword(SecurityUtils.encryptPassword(DigestUtils.md5Hex(user.getPassword())));
        Boolean userExist = this.mapper.isUserExist(user.getUsername());
        if (userExist == null){
            this.save(user);
            return true;
        }else{
            if (!userExist){
                return false;
            }
        }
        return false;
    }

}

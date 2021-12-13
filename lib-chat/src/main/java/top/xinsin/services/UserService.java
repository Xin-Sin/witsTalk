package top.xinsin.services;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xinsin.Utils.JWTTokenUtils;
import top.xinsin.dao.UserMapper;
import top.xinsin.pojo.User;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther wzp
 * @Date 2021/12/11 21:21
 * @Version 1.0
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    UserMapper userMapper;
    public String canLogin(User user, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        String password = user.getPassword();
        String sha512Hex = DigestUtils.sha512Hex(password);
        user.setPassword(sha512Hex);
        User user1 = userMapper.canLogin(user);
        if (user1 != null){
            Map<String,String> payload = new HashMap<>();
            payload.put("id",String.valueOf(user1.getId()));
            payload.put("username",user1.getUsername());
            String token = JWTTokenUtils.getToken(payload);
            response.setHeader("token",token);
            jsonObject.put("status",200);
            jsonObject.put("canLogin",true);
            log.info("username->" + user1.getUsername() + "->login");
        }else{
            jsonObject.put("status",200);
            jsonObject.put("canLogin",false);
        }
        return jsonObject.toJSONString();
    }
    public void addUser(User user){
        userMapper.addUser(user);
    }
    public void changePassword(User user){
        userMapper.changePassword(user);
    }
    public void setOnline(User user){
        userMapper.setOnline(user);
        user.setOnline(1);
    }
    public void setOffline(User user){
        userMapper.setOffline(user);
        user.setOnline(0);
    }
}
//edbd881f1ee2f76ba0bd70fd184f87711be991a0401fd07ccd4b199665f00761afc91731d8d8ba6cbb188b2ed5bfb465b9f3d30231eb0430b9f90fe91d136648

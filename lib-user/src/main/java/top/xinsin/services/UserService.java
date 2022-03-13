package top.xinsin.services;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xinsin.Utils.JWTTokenUtils;
import top.xinsin.Utils.ResponseData;
import top.xinsin.dao.UserMapper;
import top.xinsin.pojo.User;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  wzp
 * @date 2021/12/11 21:21
 * @version 1.0
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    UserMapper userMapper;
    public ResponseData canLogin(User user, HttpServletResponse response){
        log.info("canLogin args:user=" + user);
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
            response.setHeader("Access-Control-Expose-Headers","token");
            jsonObject.put("base64",user1.getBase64());
            jsonObject.put("canLogin",true);
        }else{
            jsonObject.put("canLogin",false);
        }
        return new ResponseData(jsonObject);
    }
    public ResponseData addUser(User user){
        log.info("addUser args:user=" + user);
        user.setPassword(DigestUtils.sha512Hex(user.getPassword()));
        userMapper.addUser(user);
        return new ResponseData();
    }
    public ResponseData changePassword(User user){
        userMapper.changePassword(user);
        return new ResponseData();
    }
    public void setOnline(User user){
        userMapper.setOnline(user);
        user.setOnline(1);
    }
    public void setOffline(User user){
        userMapper.setOffline(user);
        user.setOnline(0);
    }
    public ResponseData setHeadPortrait(User user){
        User user1 = userMapper.setHeadPortrait(user);
        if (user1 != null){
            log.info("username{}changeInto{}",user1.getUsername(),user1.getBase64());

        }else{
            log.info("username{}failToEdit",user.getUsername());
        }
        return new ResponseData();
    }
    public ResponseData getUserHeadPortrait(String name){
        String b64 = userMapper.getUserHeadPortrait(name);
        return new ResponseData(b64);
    }
    public ResponseData getOnlineUser() {
        log.info("getOnlineUser");
        ArrayList<User> onlineUser = userMapper.getOnlineUser();
        return new ResponseData(onlineUser);
    }
}

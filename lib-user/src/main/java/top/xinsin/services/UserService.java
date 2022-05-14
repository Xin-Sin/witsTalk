package top.xinsin.services;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xinsin.Utils.JWTTokenUtils;
import top.xinsin.Utils.ResultData;
import top.xinsin.Utils.SqlUtils;
import top.xinsin.dao.UserMapper;
import top.xinsin.enums.HttpCodes;
import top.xinsin.pojo.User;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wzp
 * @date 2021/12/11 21:21
 * @version 1.0
 */
@Service
@Slf4j
public class UserService {
    private final UserMapper userMapper;
    @Autowired
    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }
    public ResultData<JSONObject> canLogin(User user, HttpServletResponse response){
        log.info("canLogin args:user=" + user);
        JSONObject jsonObject = new JSONObject();
        String password = user.getPassword();
        String sha512Hex = DigestUtils.sha512Hex(password);
        user.setPassword(sha512Hex);
        User user1 = userMapper.canLogin(user);
        if (user1 != null){
            Map<String,String> payload = new HashMap<>(10);
            payload.put("id",String.valueOf(user1.getId()));
            payload.put("username",user1.getUsername());
            String token = JWTTokenUtils.getToken(payload);
            response.setHeader("token",token);
            response.setHeader("Access-Control-Expose-Headers","token");
            jsonObject.fluentPut("base64",user1.getBase64())
                    .fluentPut("canLogin",true);
        }else{
            jsonObject.fluentPut("canLogin",false);
            return ResultData.failed(HttpCodes.HTTP_CODES501,jsonObject);
        }
        return ResultData.success(jsonObject);
    }
    public ResultData<JSONObject> addUser(User user){
        log.info("addUser args:user=" + user);
        user.setPassword(DigestUtils.sha512Hex(user.getPassword()));
        JSONObject jsonObject = SqlUtils.insertOperate(userMapper.addUser(user));
        return ResultData.success(jsonObject);
    }
    public ResultData<JSONObject> changePassword(User user){
        JSONObject jsonObject = SqlUtils.updateOperate(userMapper.changePassword(user));
        return ResultData.success(jsonObject);
    }
    public ResultData<JSONObject> setOnline(User user){
        JSONObject jsonObject = SqlUtils.updateOperate(userMapper.setOnline(user));
        user.setOnline(1);
        return ResultData.success(jsonObject);
    }
    public ResultData<JSONObject> setOffline(User user){
        JSONObject jsonObject = SqlUtils.updateOperate(userMapper.setOffline(user));
        user.setOnline(0);
        return ResultData.success(jsonObject);
    }
    public ResultData<String > setHeadPortrait(User user){
        User user1 = userMapper.setHeadPortrait(user);
        if (user1 != null){
            log.info("username{}changeInto{}",user1.getUsername(),user1.getBase64());
        }else{
            log.info("username{}failToEdit",user.getUsername());
        }
        return ResultData.success("执行成功!");
    }
    public ResultData<String> getUserHeadPortrait(String name){
        String b64 = userMapper.getUserHeadPortrait(name);
        return ResultData.success(b64);
    }
    public ResultData<List<User>> getOnlineUser() {
        return ResultData.success(userMapper.getOnlineUser());
    }
}

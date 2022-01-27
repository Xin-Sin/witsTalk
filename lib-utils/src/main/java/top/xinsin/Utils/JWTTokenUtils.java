package top.xinsin.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @Author xinxin
 * @Date 2021/12/12 20:42
 * @Version 1.0
 */
public class JWTTokenUtils {
    private static final String KEY = "G%k7H4lK;D1@L87Kio8j^ns56lJ68";
    /*
    * 创建token
    * */
    public static String getToken(Map<String,String> map){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR,2);//默认两个小时过期
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        //payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        String token = builder.withExpiresAt(instance.getTime()) // 指定令牌过期时间
                .sign(Algorithm.HMAC512(KEY));
        return token;
    }
    /*
    * 验证token合法性
    * */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC512(KEY)).build().verify(token);
    }
    public static Boolean isRight(String token){
        try{
            verify(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    /*
    * 获取token信息方法
    * */
    public static DecodedJWT getTokenInfo(String token){
        return JWT.require(Algorithm.HMAC512(KEY)).build().verify(token);
    }
}

package top.xinsin.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author xinxin
 * @date 2021/12/12 20:42
 * @version 1.0
 */
public class JwtTokenUtils {
    private static final String KEY = "G%k7H4lK;D1@L87Kio8j^ns56lJ68";

    /**
     * 创建token
     * @param map 字段
     * @return token
     */
    public static String getToken(Map<String,String> map){
        Calendar instance = Calendar.getInstance();
        //默认两个小时过期
        instance.add(Calendar.HOUR,2);
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach(builder::withClaim);
        //指定令牌过期时间
        return builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC512(KEY));
    }

    /**
     * 验证token是否合法
     * @param token token
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC512(KEY)).build().verify(token);
    }

    /**
     * 验证token是否合法
     * @param token token
     * @return 是否为合法token
     */
    public static Boolean isRight(String token){
        try{
            verify(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 从token中提取信息
     * @param token token
     * @return token中包含的信息
     */
    public static DecodedJWT getTokenInfo(String token){
        return JWT.require(Algorithm.HMAC512(KEY)).build().verify(token);
    }
}

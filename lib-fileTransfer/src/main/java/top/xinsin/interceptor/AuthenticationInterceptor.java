package top.xinsin.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sun.misc.BASE64Decoder;
import top.xinsin.Utils.JwtTokenUtils;
import top.xinsin.Utils.ResponseData;
import top.xinsin.pojo.AuthVerificationTokenJwt;
import top.xinsin.service.UserVerifyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author xinxin
 * @date 2022/1/21 22:16
 * @version 1.0
 */
@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserVerifyService userVerifyService;
    public static final String OPTIONS = "OPTIONS";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (OPTIONS.equals(request.getMethod())) {
            return true;
        }
        // 从http请求头中取出token
        final String token = request.getHeader("Access-Token");
        //解决跨域问题
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Headers","*");
        ResponseData responseData;
        String data = null;
        System.out.println(token);
        //获取token
        DecodedJWT tokenInfo = JwtTokenUtils.getTokenInfo(token);
        try {
            //解析token
            data = new String(new BASE64Decoder().decodeBuffer(tokenInfo.getPayload()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //查找json字符串中的结尾 并取下标将其分割
        assert data != null;
        data = data.substring(0,data.indexOf("}",39) + 1);
        //获取实体类对象
        AuthVerificationTokenJwt authVerificationTokenJwt = JSONObject.parseObject(data, AuthVerificationTokenJwt.class);
        if (!userVerifyService.userVerify(authVerificationTokenJwt)) {
             log.warn("AuthenticationInterceptor --> username --> " + authVerificationTokenJwt.getUsername() + ": TokenVerifyFail:(");
             responseData = new ResponseData("token验证失败,该用户可能还没有登陆", HttpStatus.UNAUTHORIZED);
        }else{
            log.info("AuthenticationInterceptor --> username --> " + authVerificationTokenJwt.getUsername() + ": TokenVerifySuccess:)");
            return true;
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(responseData.toString());
        return false;
    }
}

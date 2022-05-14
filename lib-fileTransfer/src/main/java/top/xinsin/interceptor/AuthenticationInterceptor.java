package top.xinsin.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sun.misc.BASE64Decoder;
import top.xinsin.Utils.JWTTokenUtils;
import top.xinsin.Utils.ResponseData;
import top.xinsin.pojo.AuthVerificationTokenJWT;
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
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 这个！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        // 从http请求头中取出token
        final String token = request.getHeader("Access-Token");
        response.setHeader("Access-Control-Allow-Origin","*");//解决跨域问题
        response.setHeader("Access-Control-Allow-Headers","*");//解决跨域问题
        ResponseData responseData;
        String data = null;
        System.out.println(token);
//       获取token
        DecodedJWT tokenInfo = JWTTokenUtils.getTokenInfo(token);
        try {
            //解析token
            data = new String(new BASE64Decoder().decodeBuffer(tokenInfo.getPayload()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //查找json字符串中的结尾 并取下标将其分割
        assert data != null;
        data = data.substring(0,data.indexOf("}",39) + 1);
//        获取实体类对象
        AuthVerificationTokenJWT authVerificationTokenJWT = JSONObject.parseObject(data, AuthVerificationTokenJWT.class);
        if (!userVerifyService.userVerify(authVerificationTokenJWT)) {
             log.warn("AuthenticationInterceptor --> username --> " + authVerificationTokenJWT.getUsername() + ": TokenVerifyFail:(");
             responseData = new ResponseData("token验证失败,该用户可能还没有登陆", HttpStatus.UNAUTHORIZED);
        }else{
            log.info("AuthenticationInterceptor --> username --> " + authVerificationTokenJWT.getUsername() + ": TokenVerifySuccess:)");
            return true;
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(responseData.toString());
        return false;
    }
}

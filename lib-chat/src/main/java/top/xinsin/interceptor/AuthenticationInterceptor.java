package top.xinsin.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.xinsin.Utils.JWTTokenUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author xinxin
 * @Date 2021/12/12 19:17
 * @Version 1.0
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor  {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从http请求头中取出token
        final String token = request.getHeader("token");
        JSONObject jsonObject = new JSONObject();
        try {
            JWTTokenUtils.verify(token);
        } catch (SignatureVerificationException e) {
            log.info("用户验证了无效签名");
            jsonObject.put("msg","无效签名");
        }catch (TokenExpiredException e){
            log.info("用户验证的签名已过期");
            jsonObject.put("msg","签名已过期");
        }catch (AlgorithmMismatchException e){
            log.info("用户验证的token算法不一致");
            jsonObject.put("msg","算法不一致");
        }catch (Exception e){
            log.info("token无效或者是空的");
            jsonObject.put("msg","token无效");
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonObject.toJSONString());
        return false;
    }
}

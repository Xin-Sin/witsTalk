package top.xinsin.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.http.HttpStatus;
import top.xinsin.Utils.JWTTokenUtils;
import top.xinsin.Utils.ResponseData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author xinxin
 * @Date 2021/12/12 19:17
 * @Version 1.0
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor  {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        // 从http请求头中取出token
        final String token = request.getHeader("Access-Token");
        response.setHeader("Access-Control-Allow-Origin","*");//解决跨域问题
        response.setHeader("Access-Control-Allow-Headers","*");//解决跨域问题
        ResponseData responseData;
        try {
            JWTTokenUtils.verify(token);
            return true;
        } catch (SignatureVerificationException e) {
            log.info("用户验证了无效签名");
            e.printStackTrace();
            responseData = new ResponseData("无效签名", HttpStatus.UNAUTHORIZED);
        }catch (TokenExpiredException e){
            log.info("用户验证的签名已过期");
            e.printStackTrace();
            responseData = new ResponseData("签名已过期", HttpStatus.UNAUTHORIZED);
        }catch (AlgorithmMismatchException e){
            log.info("用户验证的token算法不一致");
            e.printStackTrace();
            responseData = new ResponseData("token算法不一致", HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            log.info("token无效或者是空的");
            e.printStackTrace();
            responseData = new ResponseData("token无效", HttpStatus.UNAUTHORIZED);
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(responseData.toString());
        return false;
    }
}

package top.xinsin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.xinsin.interceptor.AuthenticationInterceptor;

/**
 * @author xinxin
 * Created On 2021/12/12 21:05
 * @version 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor()).
                //拦截
                addPathPatterns("/**").
                //放行登录接口
                excludePathPatterns("/user/api/login").
                //放行验证码接口
                excludePathPatterns("/user/api/vc").
                //放行注册接口
                excludePathPatterns("/user/api/adduser").
//                放行token验证登陆接口
                excludePathPatterns("/user/api/autoLogin").
                //放行调试接口
                excludePathPatterns("/user/api/debug");
    }
}

package top.xinsin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.xinsin.interceptor.AuthenticationInterceptor;

/**
 * @author xinxin
 * @date 2021/12/12 21:05
 * @version 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor()).
                addPathPatterns("/**"). //拦截
                excludePathPatterns("/user/api/login").//放行登录接口
                excludePathPatterns("/user/api/vc").//放行验证码接口
                excludePathPatterns("/user/api/adduser").excludePathPatterns("/user/api/debug")
        ;//放行添加用户接口
    }
}

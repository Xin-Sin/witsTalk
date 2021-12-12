package top.xinsin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.xinsin.interceptor.AuthenticationInterceptor;

/**
 * @Author xinxin
 * @Date 2021/12/12 21:05
 * @Version 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor()).
                addPathPatterns("/**"). //拦截
                excludePathPatterns("/api/login");//放行
    }
}

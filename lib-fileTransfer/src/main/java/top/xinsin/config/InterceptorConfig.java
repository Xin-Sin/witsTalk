package top.xinsin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.xinsin.interceptor.AuthenticationInterceptor;

/**
 * @Author xinxin
 * @Date 2022/1/21 22:14
 * @Version 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public AuthenticationInterceptor getAuthenticationInterceptor(){
        return new AuthenticationInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthenticationInterceptor()).
                addPathPatterns("/**")      //拦截
                .excludePathPatterns("/file/api/downloadFile")
                .excludePathPatterns("/api/fileUpload");
    }
}

package top.xinsin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.xinsin.interceptor.AuthenticationInterceptor;

/**
 * @author xinxin
 * Created On 2022/1/21 22:14
 * @version 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private AuthenticationInterceptor authenticationInterceptor;
    @Autowired
    public InterceptorConfig(AuthenticationInterceptor authenticationInterceptor){
        this.authenticationInterceptor = authenticationInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                //拦截
                .addPathPatterns("/**")
                .excludePathPatterns("/file/api/downloadFile")
                .excludePathPatterns("/api/fileUpload");
    }
}

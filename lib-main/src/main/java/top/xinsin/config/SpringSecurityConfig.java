package top.xinsin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.xinsin.filter.SecurityTokenAuthenticationFilter;
import top.xinsin.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xinsin
 * Created On 2023/9/20 17:03
 * @version 1.0
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {
    private final SecurityTokenAuthenticationFilter securityTokenAuthenticationFilter;
    private final UserServiceImpl userService;

    public SpringSecurityConfig(SecurityTokenAuthenticationFilter securityTokenAuthenticationFilter, UserServiceImpl userService) {
        this.securityTokenAuthenticationFilter = securityTokenAuthenticationFilter;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(e -> e.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(e -> e.requestMatchers(getUrls()).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyRequest().authenticated()
                );
        http.headers(e -> e.cacheControl(HeadersConfigurer.CacheControlConfig::disable));
        http.addFilterBefore(securityTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers(e -> e.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }

    private String[] getUrls() {
        List<String> list = new ArrayList<>();
        list.add("/login");
        list.add("/register");
        return list.toArray(new String[0]);
    }

    /**
     * 装载BCrypt密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

}

package top.xinsin.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Auther wzp
 * @Date 2021/12/12 14:40
 * @Version 1.0
 */

@Configuration
public class Security extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.formLogin()
                .loginPage("/static/login.html")
                .and()
                .authorizeHttpRequests()
                .antMatchers("/api/vc").permitAll()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/static/login.html").permitAll()
                .and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated();
        //.csrf().disable();
    }
}

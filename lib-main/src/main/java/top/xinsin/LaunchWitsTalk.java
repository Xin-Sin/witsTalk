package top.xinsin;

import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * @author xinsin
 * Created On 2023/9/20 15:03
 * @version 1.0
 */
@SpringBootApplication
@MapperScan("top.xinsin.mapper")
@ServletComponentScan("top.xinsin")
public class LaunchWitsTalk {
    public static void main(String[] args) {
        SpringApplication.run(LaunchWitsTalk.class, args);
    }
}

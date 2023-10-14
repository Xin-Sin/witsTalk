package top.xinsin;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author xinsin
 * Created On 2023/9/20 15:03
 * @version 1.0
 */
@SpringBootApplication
@MapperScans({@MapperScan("top.xinsin.mapper"), @MapperScan("cn.wzpmc.mapper")})
@ServletComponentScan("top.xinsin")
public class LaunchWitsTalk {
    public static void main(String[] args) {
        SpringApplication.run(LaunchWitsTalk.class, args);
    }
}

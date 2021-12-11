package top.xinsin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author xinxin
 * @Date 2021/12/11 17:16
 * @Version 1.0
 */
@MapperScan("top.xinsin.dao")
@SpringBootApplication
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(Start.class,args);
    }
}

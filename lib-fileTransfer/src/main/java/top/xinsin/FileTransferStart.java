package top.xinsin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xinxin
 * @date 2021/12/14 14:00
 * @version 1.0
 */
@MapperScan("top.xinsin.dao")
@SpringBootApplication
public class FileTransferStart {
    public static void main(String[] args) {
        SpringApplication.run(FileTransferStart.class,args);
    }
}

package cn.wzpmc;

import lombok.SneakyThrows;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author wzp
 * Created On 2022/5/14
 * @version 1.0
 */
@MapperScan("cn.wzpmc.dao")
@SpringBootApplication
public class ChatStart {
    private static Netty netty;
    @Autowired
    public void setNetty(Netty netty){
        ChatStart.netty = netty;
    }

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ChatStart.class);
        ConfigurableApplicationContext run = springApplication.run(args);
        ChatFrameHandler.setCommandHandler(run);
        springApplication.addListeners(netty);
        netty.start();
    }
}
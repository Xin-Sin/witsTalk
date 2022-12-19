package cn.wzpmc;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.HashMap;

/**
 * @author wzp
 * @version 1.0
 * Created On 2022/5/14
 */
@Slf4j
@Component
public class Netty implements ApplicationListener<ContextClosedEvent> {
    @Value("${server.port}")
    private int port;
    private final EventLoopGroup boss = new NioEventLoopGroup();
    private final EventLoopGroup work = new NioEventLoopGroup();
    private Channel channel;
    private final ChatHandler chatHandler;
    @Autowired
    public Netty(ChatHandler chatHandler){
        this.chatHandler = chatHandler;
    }
    /**
     * Start netty service
     */
    public void start(){
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,work)
                //设置日志处理器
                .handler(new LoggingHandler(LogLevel.DEBUG))
                //设置自主处理器
                .childHandler(chatHandler)
                //设置通道
                .channel(NioServerSocketChannel.class);
        try {
            ChannelFuture future = bootstrap.bind(new InetSocketAddress(port)).sync();
            if (future.isSuccess()){
                log.info("Chat Server started on 0.0.0.0:{}",port);
            }
            this.channel = future.channel();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void destroy() {
        log.info("Shutdown Netty Server...");
        if(channel != null) {
            channel.close();
        }
        work.shutdownGracefully();
        boss.shutdownGracefully();
        log.info("Shutdown Netty Server Success!");
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        this.destroy();
    }
}

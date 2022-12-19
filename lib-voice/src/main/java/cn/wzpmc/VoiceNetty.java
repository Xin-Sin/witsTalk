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

@Component
@Slf4j
public class VoiceNetty implements ApplicationListener<ContextClosedEvent> {
    private final EventLoopGroup boss = new NioEventLoopGroup();
    private final EventLoopGroup worker = new NioEventLoopGroup();
    @Value("${server.port}")
    private int port;
    private Channel channel;
    private final VoiceHandler voiceHandler;
    @Autowired
    public VoiceNetty(VoiceHandler voiceNetty){
        this.voiceHandler = voiceNetty;
    }
    public void start(){
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(boss,worker)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(voiceHandler)
                    .channel(NioServerSocketChannel.class);
            ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(port)).sync();
            if (channelFuture.isSuccess()){
                log.info("Voice Server started on 0.0.0.0:{}",port);
            }
            this.channel = channelFuture.channel();
            this.channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("Shutdown Netty Server...");
        if(channel != null) {
            channel.close();
        }
        boss.shutdownGracefully();
        worker.shutdownGracefully();
        log.info("Shutdown Netty Server Success!");
    }
}

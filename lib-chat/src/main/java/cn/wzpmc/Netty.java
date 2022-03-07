package cn.wzpmc;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author wzp
 */
@Slf4j
@Component
public class Netty {
    private final EventLoopGroup boss = new NioEventLoopGroup();
    private final EventLoopGroup work = new NioEventLoopGroup();
    private Channel channel;
    /**
     * Start netty service
     */
    @SneakyThrows
    public void start(){
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,work)
                //设置日志处理器
                .handler(new LoggingHandler(LogLevel.DEBUG))
                //设置自主处理器
                .childHandler(new ChatHandler())
                //设置通道
                .channel(NioServerSocketChannel.class);
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8005)).sync();
        if (future.isSuccess()){
            log.info("started");
        }
        this.channel = future.channel();
        future.channel().closeFuture().sync();
    }
    public void destroy() {
        log.info("Shutdown Netty Server...");
        if(channel != null) { channel.close();}
        work.shutdownGracefully();
        boss.shutdownGracefully();
        log.info("Shutdown Netty Server Success!");
    }
}

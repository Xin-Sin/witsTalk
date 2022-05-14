package cn.wzpmc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @author wzp
 * @date 2022/5/14
 * @version 1.0
 */
public class VoiceStart {
    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(boss,worker)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new VoiceHandler())
                    .channel(NioServerSocketChannel.class);
            ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(8006)).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}

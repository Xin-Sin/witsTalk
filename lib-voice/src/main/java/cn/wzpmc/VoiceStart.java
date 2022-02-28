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
 */
public class VoiceStart {
    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    //设置两个LoopGroup
                    .group(boss,worker)
                    //设置日志处理器
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    //设置自主处理器
                    .childHandler(new VoiceHandler())
                    //设置通道
                    .channel(NioServerSocketChannel.class);
            //绑定端口
            ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(8006)).sync();
            //启动服务
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭服务
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}

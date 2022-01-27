package cn.wzpmc;

import cn.wzpmc.dao.ChatDao;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;

/**
 * @Author wzp
 * @Date 2022/1/26
 * @Version 1.0
 */
public class ChatStart {
    public static SqlSession session;
    public static void main(String[] args) {
        String resource = "mybatis-config.xml";
        try(InputStream inputStream = Resources.getResourceAsStream(resource)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session = sqlSessionFactory.openSession();
        }catch (IOException e){
            e.printStackTrace();
        }
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
                    .childHandler(new ChatHandler())
                    //设置通道
                    .channel(NioServerSocketChannel.class);
            //绑定端口
            ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(8005)).sync();
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

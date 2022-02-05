package cn.wzpmc;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author wzp
 * @Date 2022/1/26
 * @Version 1.0
 */
public class ChatHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socket){
        //获取pipeline
        ChannelPipeline pipeline = socket.pipeline();
        //添加解析器
        //日志解析器
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        //Http解析器
        pipeline.addLast(new HttpServerCodec());
        //块解析器
        pipeline.addLast(new ChunkedWriteHandler());
        //聚合解析器
        pipeline.addLast(new HttpObjectAggregator(16384));
        //设置uri为/connect
        pipeline.addLast(new WebSocketServerProtocolHandler("/chat"));
        //自定义解析器
        pipeline.addLast(new ChatFrameHandler());
    }
}

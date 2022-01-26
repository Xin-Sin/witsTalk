package cn.wzpmc;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author wzp
 * @Date 2022/1/26
 * @Version 1.0
 */
@Slf4j
public class ChatFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static ConcurrentHashMap<ChannelId,Channel> channels = new ConcurrentHashMap<>();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        /*
        此方法会在收到消息时调用
         */
        String data = textWebSocketFrame.text();
        log.debug("receive message {}",data);
    }
    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext){
        /*
        此方法会在客户端连接时调用
         */
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        log.info("getConnected ip = {} id = {}",channel.remoteAddress(),id);
        channel.writeAndFlush("Connected");
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext){
        /*
        此方法会在客户端断开连接时调用
         */
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        log.info("client disconnected ip = {} id = {}",channel.remoteAddress(),id);
        channels.remove(id);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        /*
        此方法会在服务端出现错误时调用
         */
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        log.error("getError ip = {} id = {}",channel.remoteAddress(),id);
        cause.printStackTrace();
        channels.remove(id);
        channelHandlerContext.close();
    }
     static void sendToAll(Object msg){
         for (Map.Entry<ChannelId, Channel> channelIdChannelEntry : channels.entrySet()) {
             ChannelId id = channelIdChannelEntry.getKey();
             Channel channel = channelIdChannelEntry.getValue();
             channel.writeAndFlush(msg);
             log.info("send message to ip = {} id = {}",channel.remoteAddress(),id);
         }
    }
}

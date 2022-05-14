package cn.wzpmc;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wzp
 * @date 2022/5/14
 * @version 1.0
 */
@Slf4j
public class VoiceFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * 用于存储所有客户端的连接
     */
    public static ConcurrentHashMap<ChannelId, Channel> channels = new ConcurrentHashMap<>();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame){
        /*
            当获取到消息时调用
         */
    }
    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext){
        /*
            当新的客户端链接时调用
         */
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        channels.put(id,channel);
        log.info("Client connected, ip={}, channelId={}",channel.remoteAddress(),id.asShortText());
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext){
        /*
            当客户端正常断开链接时调用
         */
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        channels.remove(id);
        log.info("Client disconnected, ip={}, channelId={}",channel.remoteAddress(),id.asShortText());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext,Throwable throwable){
        /*
            当服务端处理客户端消息时报错时调用
         */
        throwable.printStackTrace();
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        log.error("Client have a error, ip={}, channelId={}",channel.remoteAddress(),id.asShortText());
        log.error("Try close connection! ip={}, channelId={}",channel.remoteAddress(),id.asShortText());
        channelHandlerContext.close();
        channels.remove(id);
        log.info("close connection! ip={}, channelId={}",channel.remoteAddress(),id.asShortText());
    }
}

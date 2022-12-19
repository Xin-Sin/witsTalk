package cn.wzpmc;

import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.xinsin.utils.NettyUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wzp
 * Created On 2022/5/14
 * @version 1.0
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class VoiceFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * 用于保存ChannelId和Channel
     * key 为 ChannelId
     * value 为 Channel
     */
    public static final Map<ChannelId, Channel> users = new ConcurrentHashMap<>();
    /**
     * 用于保存用户登陆状态
     * key 为 ChannelId
     * value 为 是否登陆
     */
    public static final Map<ChannelId, Boolean> loginStatus = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        WebSocketFrame handler = NettyUtils.handler(CommandHandler.class, channelHandlerContext, textWebSocketFrame);
        if (handler != null){
            channelHandlerContext.writeAndFlush(handler);
        }
    }
    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext){
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        users.put(id, channel);
        log.info("User {} connected with channel {}", channel.remoteAddress(), id.asShortText());
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        CommandHandler.handlerConnectionClose(channelHandlerContext);
        users.remove(id);
        log.info("User {} disconnected with channel {}", channel.remoteAddress(), id.asShortText());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext,Throwable throwable) {
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        channelHandlerContext.close();
        users.remove(id);
        log.info("User {} had an error with channel {}", channel.remoteAddress(), id.asShortText());
        throwable.printStackTrace();
    }
}

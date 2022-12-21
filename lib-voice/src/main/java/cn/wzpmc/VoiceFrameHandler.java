package cn.wzpmc;

import cn.wzpmc.services.VoiceCommandHandler;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;
import top.xinsin.utils.NettyUtils;

/**
 * @author wzp
 * Created On 2022/5/14
 * @version 1.0
 */
@Component
@ChannelHandler.Sharable
public class VoiceFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final NettyUtils<VoiceCommandHandler> commandHandler = new NettyUtils<>(VoiceCommandHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        commandHandler.handler(channelHandlerContext, textWebSocketFrame);
    }
    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext){
        commandHandler.handlerJoin(channelHandlerContext);
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        commandHandler.handlerClose(channelHandlerContext);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext,Throwable throwable) {
        commandHandler.handlerError(channelHandlerContext, throwable);
    }
}

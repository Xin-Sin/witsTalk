package cn.wzpmc;
import cn.wzpmc.services.ChatCommandHandler;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import top.xinsin.utils.NettyUtils;
/**
 * @author wzp
 * Created On 2022/5/14
 * @version 1.0
 */
@ChannelHandler.Sharable
@Slf4j
@Component
public class ChatFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static NettyUtils<ChatCommandHandler> commandHandler = null;

    public static void setCommandHandler(ConfigurableApplicationContext configurableApplicationContext) {
        if(ChatFrameHandler.commandHandler == null){
            ChatFrameHandler.commandHandler = new NettyUtils<>(ChatCommandHandler.class, configurableApplicationContext);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        commandHandler.handlerJoin(channelHandlerContext);
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        commandHandler.handlerClose(channelHandlerContext);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        commandHandler.handlerError(channelHandlerContext,cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        commandHandler.handler(channelHandlerContext, textWebSocketFrame);
    }
}

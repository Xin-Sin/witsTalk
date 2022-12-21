package top.xinsin.interfaces;

import io.netty.channel.ChannelHandlerContext;

public interface NettyCommandHandler {
    void handlerClose(ChannelHandlerContext channelHandlerContext);
    void handlerJoin(ChannelHandlerContext channelHandlerContext);
    default void handlerError(ChannelHandlerContext channelHandlerContext, Throwable cause){
        cause.printStackTrace();
    }
}

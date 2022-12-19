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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wzp
 * Created On 2022/5/14
 * @version 1.0
 */
@Component
public class VoiceHandler extends ChannelInitializer<SocketChannel> {
    private final VoiceFrameHandler voiceFrameHandler;
    @Autowired
    public VoiceHandler(VoiceFrameHandler voiceFrameHandler){
        this.voiceFrameHandler = voiceFrameHandler;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel){
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG))
                .addLast(new HttpServerCodec())
                .addLast(new ChunkedWriteHandler())
                .addLast(new HttpObjectAggregator(16384))
                .addLast(new WebSocketServerProtocolHandler("/voice"))
                .addLast(voiceFrameHandler);
    }
}

package cn.wzpmc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import top.xinsin.Utils.JWTTokenUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
public class VoiceFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{
    public static ConcurrentHashMap<ChannelId, Channel> channels = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<ChannelId,Boolean> loginTable= new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame){
    /*
    此方法会在收到消息时调用
     */
    }
    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext){
    /*
    此方法会在客户端连接时调用
     */
        //获取传输通道
        Channel channel = channelHandlerContext.channel();
        //获取通道id
        ChannelId id = channel.id();
        //日志
        log.info("getConnected ip = {} id = {}",channel.remoteAddress(),id.asShortText());
        //将通道放入通道表中
        channels.put(id,channel);
        //将此链接放入登陆表中
        loginTable.put(id,false);
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext){
    /*
    此方法会在客户端断开连接时调用
     */
        //获取消息通道
        Channel channel = channelHandlerContext.channel();
        //获取通道id
        ChannelId id = channel.id();
        //日志
        log.info("client disconnected ip = {} id = {}",channel.remoteAddress(),id.asShortText());
        //将此通道从通道表中移除
        channels.remove(id);
        //将此连接从登陆表中移除
        loginTable.remove(id);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
    /*
    此方法会在服务端出现错误时调用
     */
        //获取通道
        Channel channel = channelHandlerContext.channel();
        //获取通道id
        ChannelId id = channel.id();
        //日志
        log.error("getError ip = {} id = {}",channel.remoteAddress(),id);
        //打印错误信息
        cause.printStackTrace();
        //发送错误详情至客户端
        sendMessage(id,cause.getMessage());
        //将此通道从通道表中移除
        channels.remove(id);
        //将此连接从登陆表中移除
        loginTable.remove(id);
        //结束此通道的链接
        channelHandlerContext.close();
    }
    public static void sendToAll(String msg){
    /*
    发送消息到所有用户
     */
        for (Map.Entry<ChannelId, Channel> channelIdChannelEntry : channels.entrySet()) {
            ChannelId id = channelIdChannelEntry.getKey();
            Channel channel = channelIdChannelEntry.getValue();
            sendMessage(id,msg);
        }
    }
    public static void sendMessage(ChannelId id,String msg){
    /*
    发送消息
     */
        Channel channel = channels.get(id);
        //将消息内容转为TextWebSocketFrame格式并发送
        channel.writeAndFlush(new TextWebSocketFrame(msg));
        log.info("send message to ip = {} id = {}",channel.remoteAddress(),id.asShortText());
    }
    public static void sendMessage(ChannelId id,Object msg) {
    /*
    发送消息
     */
        Channel channel = channels.get(id);
        //将消息内容转为TextWebSocketFrame格式并发送
        channel.writeAndFlush(new TextWebSocketFrame(msg.toString()));
        log.info("send message to ip = {} id = {}", channel.remoteAddress(), id.asShortText());
    }
}

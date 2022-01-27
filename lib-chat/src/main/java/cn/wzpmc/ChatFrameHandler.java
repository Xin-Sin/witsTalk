package cn.wzpmc;

import cn.wzpmc.dao.ChatDao;
import cn.wzpmc.pojo.Message;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author wzp
 * @Date 2022/1/26
 * @Version 1.0
 */
@Slf4j
public class ChatFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static ConcurrentHashMap<ChannelId,Channel> channels = new ConcurrentHashMap<>();
    private final ChatDao chatDao = ChatStart.session.getMapper(ChatDao.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame){
        /*
        此方法会在收到消息时调用
         */

        //获取传输通道
        Channel channel = channelHandlerContext.channel();
        //获取通道ID
        ChannelId id = channel.id();
        //获取消息文本内容
        String data = textWebSocketFrame.text();
        //日志
        log.debug("receive message {} ip = {} id = {}",data,channel.remoteAddress().toString(),id.asShortText());
        //将消息转换为json
        JSONObject json = null;
        try{
            json = JSON.parseObject(data);
        }catch (JSONException e){
            //打印错误
            e.printStackTrace();
            //将syntax error发回至客户端
            sendMessage(id,"syntax error");
            return;
        }
        //获取进行的操作
        String operating = json.getString("op");
        //获取操作的参数
        JSONObject args = json.getJSONObject("args");
        //如果操作为send
        String sendCommand = "send";
        if(Objects.equals(operating, sendCommand)){
            //业务逻辑
            //获取发送者
            String sender = args.getString("sender");
            //获取发送内容
            String content = args.getString("content");
            //获取发送的类型
            String type = args.getString("type");
            //日志
            log.info("sendMessage,sender = {} content = {} type = {}",sender,content,type);
            //定义消息对象
            Message message = new Message(content, sender, type);
            //Dao层发送消息
            chatDao.sendMessage(message);
            //commit至数据库
            ChatStart.session.commit();
            //广播此消息
            sendToAll(JSONObject.toJSONString(message));
        }
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
        //广播连接消息
        sendToAll(channel.remoteAddress().toString() + " connected!");
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
        //广播断开链接
        sendToAll(channel.remoteAddress().toString() + " disconnected!");
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
        //将此通道从通道表中移除
        channels.remove(id);
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
}

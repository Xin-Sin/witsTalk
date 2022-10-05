package cn.wzpmc;
import cn.wzpmc.dao.ChatDao;
import cn.wzpmc.pojo.Message;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import top.xinsin.utils.JwtTokenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @author wzp
 * Created On 2022/5/14
 * @version 1.0
 */
@ChannelHandler.Sharable
@Slf4j
public class ChatFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static ConcurrentHashMap<ChannelId,Channel> channels = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<ChannelId,Boolean> loginTable= new ConcurrentHashMap<>();

    public static SqlSession session;
    public static ChatDao chatDao;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame){
        /*
        此方法会在收到消息时调用
         */
        //
        session = ChatStart.factory.openSession();
        chatDao = session.getMapper(ChatDao.class);
        //获取传输通道
        Channel channel = channelHandlerContext.channel();
        //获取通道ID
        ChannelId id = channel.id();
        //获取消息文本内容
        String data = textWebSocketFrame.text();
        //日志
        log.debug("receive message {} ip = {} id = {}",data,channel.remoteAddress().toString(),id.asShortText());
        //将消息转换为json
        JSONObject json;
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
        String loginCommand = "login";
        String sendCommand = "send";
        String getMessageCommand = "get";
        String getMessageCountCommand = "count";
        String recallMessageCommand = "recall";
        String heartCheckCommand = "heartCheck";
        //登录操作
        if(Objects.equals(operating, loginCommand)){
            Boolean r = JwtTokenUtils.isRight(args.getString("token"));
            log.debug("logon,r = {} ip = {} id = {}",r,channel.remoteAddress().toString(),id.asShortText());
            loginTable.put(id,r);
            sendMessage(id,r.toString());
        }else if(loginTable.get(id)){
            //业务逻辑
//            如果为心跳包，则返回pong
            if (Objects.equals(operating,heartCheckCommand)){
                sendHeartCheckPong(id);
            }
            //如果操作为send
            if(Objects.equals(operating, sendCommand)){
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
                session.commit();
                //获取用户头像
                String b64 = chatDao.getUserHeadPortrait(sender);
                message.setBase64(b64);
                //广播此消息
                String rawJson = JSON.toJSONString(message);
                sendToAll(rawJson);
            }//如果操作为getMessage
            else if(Objects.equals(operating,getMessageCommand)){
                //获取id最小值
                Integer min = args.getInteger("min");
                //获取id最大值
                Integer max = args.getInteger("max");
                //日志
                log.info("getMessage min = {} max = {} ip = {} id = {}",min,max,channel.remoteAddress(),id.asShortText());
                //从数据库获取消息
                ArrayList<Message> messages = chatDao.getMessage(min, max);
                //todo 更改为双标查询获取头像
                //将这些消息转换为json并返回给客户端
                sendMessage(id,JSON.toJSONString(messages));
            }
            //如果操作为getMessageCount
            else if(Objects.equals(operating,getMessageCountCommand)){
                //日志
                log.info("getMessageCount");
                //从数据库获取消息数量
                Integer count = chatDao.getCount().get(0);
                sendMessage(id,count);
            }else if(Objects.equals(operating,recallMessageCommand)){
                int i = args.getInteger("id");
                Message message = new Message(i);
                log.info("Recall Message message={}",message);
                chatDao.recall(message);
                session.commit();
                HashMap<String,String> resp = new HashMap<>(10);
                resp.put("op","recall");
                resp.put("id",String.valueOf(i));
                sendToAll(JSON.toJSONString(resp));
            }
        }else{
            sendMessage(id,"You are not login!");
        }
        session.close();
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
        //将此连接从登陆表中移除
        loginTable.remove(id);
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
    public static void sendMessage(ChannelId id,Object msg){
        /*
        发送消息
         */
        Channel channel = channels.get(id);
        //将消息内容转为TextWebSocketFrame格式并发送
        channel.writeAndFlush(new TextWebSocketFrame(msg.toString()));
        log.info("send message to ip = {} id = {}",channel.remoteAddress(),id.asShortText());
    }
    public static void sendHeartCheckPong(ChannelId id){
        Channel channel = channels.get(id);
        //将消息内容转为TextWebSocketFrame格式并发送
        channel.writeAndFlush(new TextWebSocketFrame("{\"heartCheck\": \"pong\"}"));
    }
}

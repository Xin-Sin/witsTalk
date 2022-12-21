package cn.wzpmc.services;

import cn.wzpmc.dao.ChatDao;
import cn.wzpmc.pojo.Message;
import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xinsin.interfaces.NettyCommandHandler;
import top.xinsin.utils.JwtTokenUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ChatCommandHandler implements NettyCommandHandler {
    private class Checker extends Thread{
        private final ChatCommandHandler chatCommandHandler;
        private Checker(ChatCommandHandler chatCommandHandler){
            this.chatCommandHandler = chatCommandHandler;
        }

        @SneakyThrows
        @Override
        public void run() {
            while (true){
                for (Map.Entry<Channel, Long> channelLongEntry : chatCommandHandler.pingTime.entrySet()) {
                    if (System.currentTimeMillis() - channelLongEntry.getValue() > 1000 * 30){
                        channelLongEntry.getKey().close();
                    }
                }
                Thread.sleep(1000);
            }
        }
    }
    private final ChatDao chatDao;
    private final Map<ChannelId, Channel> users = new ConcurrentHashMap<>();
    private final Map<ChannelId, Boolean> loginTable = new ConcurrentHashMap<>();
    private final Map<Channel, Long> pingTime = new ConcurrentHashMap<>();
    @Autowired
    public ChatCommandHandler(ChatDao chatDao){
        this.chatDao = chatDao;
        new Checker(this).start();
    }
    public JSONObject handlerLogin(ChannelHandlerContext channelHandlerContext, String token){
        Boolean right = JwtTokenUtils.isRight(token);
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        loginTable.put(id, right);
        JSONObject result = new JSONObject();
        result.fluentPut("success", right);
        return result;
    }
    public List<Message> handlerGet(ChannelHandlerContext channelHandlerContext,Integer minId,Integer count){
        if (!loginTable.getOrDefault(channelHandlerContext.channel().id(), false)) {
            return null;
        }
        return chatDao.getMessage(minId, count);
    }
    public List<Message> handlerGetNew(ChannelHandlerContext channelHandlerContext,Integer minId,Integer maxId,Integer count){
        if (!loginTable.getOrDefault(channelHandlerContext.channel().id(), false)) {
            return null;
        }
        return chatDao.getNewMessage(minId, maxId, count);
    }
    public int handlerCount(ChannelHandlerContext channelHandlerContext){
        if (!loginTable.getOrDefault(channelHandlerContext.channel().id(), false)) {
            return -1;
        }
        return chatDao.getCount();
    }
    public JSONObject handlerSend(ChannelHandlerContext channelHandlerContext, String content,String sender,Integer type){
        if (!loginTable.getOrDefault(channelHandlerContext.channel().id(), false)) {
            return null;
        }
        String type_ = "img";
        if (type == 0){
            type_ = "text";
        }
        Message message = new Message(content, sender, type_);
        chatDao.sendMessage(message);
        String base64 = chatDao.getUserHeadPortrait(sender);
        message.setBase64(base64);
        ChannelId id = channelHandlerContext.channel().id();
        JSONObject toAll = new JSONObject().fluentPut("op", "add").fluentPut("data", new JSONObject().fluentPut("message", message));
        for (Map.Entry<ChannelId, Channel> channelIdChannelEntry : users.entrySet()) {
            if (!channelIdChannelEntry.getKey().equals(id)) {
                channelIdChannelEntry.getValue().writeAndFlush(new TextWebSocketFrame(toAll.toJSONString()));
            }
        }

        return new JSONObject().fluentPut("message", message);
    }
    public void handlerRemove(ChannelHandlerContext channelHandlerContext, Integer id){
        if (!loginTable.getOrDefault(channelHandlerContext.channel().id(), false)) {
            return;
        }
        JSONObject toAll = new JSONObject().fluentPut("op", "recall").fluentPut("data", new JSONObject().fluentPut("id", id));
        for (Map.Entry<ChannelId, Channel> channelIdChannelEntry : users.entrySet()) {
            if (!channelIdChannelEntry.getKey().equals(channelHandlerContext.channel().id())) {
                channelIdChannelEntry.getValue().writeAndFlush(new TextWebSocketFrame(toAll.toJSONString()));
            }
        }
        chatDao.recall(id);
    }
    public void handlerPing(ChannelHandlerContext channelHandlerContext){
        Channel channel = channelHandlerContext.channel();
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("op", "pong").fluentPut("data",new JSONObject().fluentPut("time", System.currentTimeMillis()));
        pingTime.put(channel, System.currentTimeMillis());
        channel.writeAndFlush(new TextWebSocketFrame(jsonObject.toJSONString()));
    }
    @Override
    public void handlerClose(ChannelHandlerContext channelHandlerContext) {
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        users.remove(id);
        pingTime.remove(channel);
        log.info("User {} disconnected in channel {}",channel.remoteAddress(), id.asShortText());
    }
    @Override
    public void handlerJoin(ChannelHandlerContext channelHandlerContext) {
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        users.put(id, channel);
        log.info("User {} connected in channel {}",channel.remoteAddress(), id.asShortText());
    }

    @Override
    public void handlerError(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        users.remove(id);
        pingTime.remove(channel);
        log.info("User {} disconnected with error in channel {}",channel.remoteAddress(), id.asShortText());
        cause.printStackTrace();
    }
}
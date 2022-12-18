package cn.wzpmc;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.xinsin.utils.JwtTokenUtils;

import java.util.ArrayList;
import java.util.List;
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
     * 用于存储所有客户端的连接
     */
    public static ConcurrentHashMap<ChannelId, Channel> channels = new ConcurrentHashMap<>();
    private static final String LOGIN_COMMAND = "login";
    private static final String USER_ADD_COMMAND = "uadd";
    private static final String LOGIN_CALLBACK_COMMAND = "lcbak";
    private static final String LEAVE_COMMAND = "leave";
    public static ConcurrentHashMap<ChannelId, String> usernames = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, String> mediaStreamId = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, String> userMediaStreamId = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        /*
            当获取到消息时调用
         */
        String text = textWebSocketFrame.text();
        JSONObject data = JSON.parseObject(text);
        String op = data.getString("op");
        JSONObject jsonObject = new JSONObject();
        JSONObject radio = new JSONObject();
        if (op.equals(LOGIN_COMMAND)) {
            String token = data.getString("token");
            Boolean right = JwtTokenUtils.isRight(token);
            JSONObject returnToThis = new JSONObject();
            returnToThis.fluentPut("right", right);
            if (right) {
                DecodedJWT tokenInfo = JwtTokenUtils.getTokenInfo(token);
                Claim username = tokenInfo.getClaim("username");
                String user = username.asString();
                usernames.put(channelHandlerContext.channel().id(), user);
                String mediaId = data.getString("media");
                List<JSONObject> jsonObjects = new ArrayList<>();
                for (Map.Entry<String, String> stringStringEntry : mediaStreamId.entrySet()) {
                    String key = stringStringEntry.getKey();
                    String value = stringStringEntry.getValue();
                    JSONObject keyValue = new JSONObject();
                    keyValue.fluentPut("media", key).fluentPut("user", value);
                    jsonObjects.add(keyValue);
                }
                userMediaStreamId.put(user, mediaId);
                mediaStreamId.put(mediaId, user);
                returnToThis.fluentPut("username", user);
                returnToThis.fluentPut("data", jsonObjects);
                JSONObject thisUserData = new JSONObject();
                thisUserData.fluentPut("id", mediaId).fluentPut("name", user);
                radio.fluentPut("op", USER_ADD_COMMAND).fluentPut("data", thisUserData);
            }
            jsonObject.fluentPut("op", LOGIN_CALLBACK_COMMAND).fluentPut("data", returnToThis);
        } else {
            radio = data;
        }
        JSONObject finalRadio = radio;
        channels.forEach(((channelId, channel) -> {
            if (!channelId.equals(channelHandlerContext.channel().id())) {
                channel.writeAndFlush(new TextWebSocketFrame(finalRadio.toJSONString()));
            }
        }));
        channelHandlerContext.writeAndFlush(new TextWebSocketFrame(jsonObject.toJSONString()));
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
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        /*
            当客户端正常断开链接时调用
         */
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        channels.remove(id);
        log.info("Client disconnected, ip={}, channelId={}", channel.remoteAddress(), id.asShortText());
        removeFromMediaStreamId(channelHandlerContext, id);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext,Throwable throwable) {
        /*
            当服务端处理客户端消息时报错时调用
         */
        throwable.printStackTrace();
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        log.error("Client have a error, ip={}, channelId={}", channel.remoteAddress(), id.asShortText());
        log.error("Try close connection! ip={}, channelId={}", channel.remoteAddress(), id.asShortText());
        channelHandlerContext.close();
        channels.remove(id);
        removeFromMediaStreamId(channelHandlerContext, id);
        log.info("close connection! ip={}, channelId={}", channel.remoteAddress(), id.asShortText());
    }

    private void removeFromMediaStreamId(ChannelHandlerContext channelHandlerContext, ChannelId id) {
        JSONObject jsonObject = new JSONObject();
        String username = usernames.get(id);
        String mediaStreamIdString = userMediaStreamId.get(username);
        jsonObject.fluentPut("op", LEAVE_COMMAND).fluentPut("user", mediaStreamIdString);
        userMediaStreamId.remove(username);
        usernames.remove(id);
        mediaStreamId.remove(mediaStreamIdString);
        channels.forEach(((channelId, c) -> {
            if (!channelId.equals(channelHandlerContext.channel().id())) {
                c.writeAndFlush(new TextWebSocketFrame());
            }
        }));
    }
}

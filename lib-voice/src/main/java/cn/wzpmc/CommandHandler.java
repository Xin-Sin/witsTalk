package cn.wzpmc;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.xinsin.utils.JwtTokenUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class CommandHandler {
    /**
     * 用于保存用户的ChannelId和username
     * key 为 ChannelId
     * value 为 username
     */
    private static final Map<ChannelId, String> usernames = new ConcurrentHashMap<>();
    /**
     * 用于保存用户的mediaId和username
     * key 为 username
     * value 为 mediaId
     */
    private static final Map<String, String> userMedias = new ConcurrentHashMap<>();
    public static JSONObject handlerLogin(ChannelHandlerContext channelHandlerContext, String token, String media){
        Boolean right = JwtTokenUtils.isRight(token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("success", right);
        if (right) {
            DecodedJWT tokenInfo = JwtTokenUtils.getTokenInfo(token);
            Claim username = tokenInfo.getClaim("username");
            String s = username.asString();
            ChannelId id = channelHandlerContext.channel().id();
            usernames.put(id, s);
            userMedias.put(s, media);
            JSONObject toAll = new JSONObject();
            toAll.fluentPut("op","uadd").fluentPut("username", s).fluentPut("mediaId", media);
            for (Map.Entry<ChannelId, Channel> channelIdChannelEntry : VoiceFrameHandler.users.entrySet()) {
                ChannelId key = channelIdChannelEntry.getKey();
                Channel value = channelIdChannelEntry.getValue();
                if (!id.equals(key)){
                    value.writeAndFlush(new TextWebSocketFrame(toAll.toJSONString()));
                }
            }
            jsonObject.fluentPut("username", s);
            HashMap<String, String> resultHashMap = new HashMap<>();
            for (Map.Entry<String, String> stringStringEntry : userMedias.entrySet()) {
                String key = stringStringEntry.getKey();
                if (!s.equals(key)) {
                    resultHashMap.put(key, stringStringEntry.getValue());
                }
            }
            jsonObject.fluentPut("data", resultHashMap);
        }
        return jsonObject;
    }
    public static void handlerOffer(ChannelHandlerContext channelHandlerContext,JSONObject data,String username){
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        log.info("{} -> {} <=offer=>   {}", usernames.get(id), username, data);
        String from = usernames.get(id);
        JSONObject send = new JSONObject().fluentPut("op", "offer").fluentPut("from", from).fluentPut("data", data);
        sendTo(username, send);
    }
    public static void handlerCandidate(ChannelHandlerContext channelHandlerContext,JSONObject data,String username){
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        log.info("{} -> {} <=candidate=>   {}", usernames.get(id), username, data);
        String from = usernames.get(id);
        JSONObject send = new JSONObject().fluentPut("op", "candidate").fluentPut("from", from).fluentPut("data", data);
        sendTo(username, send);
    }
    public static void handlerAnswer(ChannelHandlerContext channelHandlerContext,JSONObject data,String username){
        Channel channel = channelHandlerContext.channel();
        ChannelId id = channel.id();
        log.info("{} -> {} <=answer=>   {}", usernames.get(id), username, data);
        String from = usernames.get(id);
        JSONObject send = new JSONObject().fluentPut("op", "answer").fluentPut("from", from).fluentPut("data", data);
        sendTo(username, send);
    }
    public static void handlerUsers(ChannelHandlerContext channelHandlerContext,JSONObject data){

    }
    private static void sendTo(String username,JSONObject send){
        for (Map.Entry<ChannelId, String> entry : usernames.entrySet()) {
            if (entry.getValue().equals(username)) {
                Channel toChannel = VoiceFrameHandler.users.get(entry.getKey());
                toChannel.writeAndFlush(new TextWebSocketFrame(send.toJSONString()));
                break;
            }
        }
    }
    public static void handlerConnectionClose(ChannelHandlerContext channelHandlerContext){
        ChannelId id = channelHandlerContext.channel().id();
        String username = usernames.get(id);
        usernames.remove(id);
        JSONObject leaveObject = new JSONObject();
        leaveObject.fluentPut("op", "leave").fluentPut("mediaId", userMedias.get(username)).fluentPut("username", username);
        for (Map.Entry<ChannelId, Channel> channelIdChannelEntry : VoiceFrameHandler.users.entrySet()) {
            channelIdChannelEntry.getValue().writeAndFlush(new TextWebSocketFrame(leaveObject.toJSONString()));
        }
        userMedias.remove(username);
    }
}

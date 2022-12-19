package cn.wzpmc;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import org.springframework.stereotype.Service;
import top.xinsin.utils.JwtTokenUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
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
        JSONObject jsonObject = new JSONObject();
        DecodedJWT tokenInfo = JwtTokenUtils.getTokenInfo(token);
        Boolean right = JwtTokenUtils.isRight(token);
        jsonObject.fluentPut("success", right);
        if (right) {
            Claim username = tokenInfo.getClaim("username");
            String s = username.asString();
            usernames.put(channelHandlerContext.channel().id(), s);
            userMedias.put(s, media);
            //TODO 广播此用户加入
            jsonObject.fluentPut("username", s);
            jsonObject.fluentPut("data", userMedias);

        }
        return jsonObject;
    }
    public static JSONObject handlerOffer(ChannelHandlerContext channelHandlerContext,String username, JSONObject data){
        return null;
    }
    public static JSONObject handlerCandidate(ChannelHandlerContext channelHandlerContext,String username,JSONObject data){
        return null;
    }
    public static void handlerConnectionClose(ChannelHandlerContext channelHandlerContext){
        ChannelId id = channelHandlerContext.channel().id();
        String username = usernames.get(id);
        usernames.remove(id);
        userMedias.remove(username);
    }
}

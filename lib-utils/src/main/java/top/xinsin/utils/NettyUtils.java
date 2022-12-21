package top.xinsin.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class NettyUtils {
    public static WebSocketFrame handler(Class<?> handler,ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame){
        String text = textWebSocketFrame.text();
        JSONObject jsonObject = JSON.parseObject(text);
        String command = jsonObject.getString("op");
        char c = command.charAt(0);
        char c1 = Character.toUpperCase(c);
        String s = command.replaceFirst(String.valueOf(c), String.valueOf(c1));
        jsonObject.remove("op");
        List<Class<?>> types = new ArrayList<>();
        List<Object> args = new ArrayList<>();
        types.add(ChannelHandlerContext.class);
        args.add(channelHandlerContext);
        AtomicBoolean hasNull = new AtomicBoolean(false);
        jsonObject.forEach((key , value) -> {
            if(value == null){
                hasNull.set(true);
            }else{
                types.add(value.getClass());
                args.add(value);
            }
        });
        if(hasNull.get()){
            return null;
        }
        Method declaredMethod;
        try {
            declaredMethod = handler.getDeclaredMethod("handler" + s, types.toArray(new Class[0]));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
        try {
            Object result = declaredMethod.invoke(null, args.toArray());
            if(result != null){
                return new TextWebSocketFrame(new JSONObject().fluentPut("op",command).fluentPut("data", result).toJSONString());
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}

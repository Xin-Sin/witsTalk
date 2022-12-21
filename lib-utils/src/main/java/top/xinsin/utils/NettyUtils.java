package top.xinsin.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import top.xinsin.interfaces.NettyCommandHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
@Component
@NoArgsConstructor
public class NettyUtils<T extends NettyCommandHandler> {
    public static void handler(Class<? extends NettyCommandHandler> handler, NettyCommandHandler commandHandler,ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
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
        for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {
            Object value = stringObjectEntry.getValue();
            if (value == null) {
                hasNull.set(true);
            } else {
                types.add(value.getClass());
                args.add(value);
            }
        }
        if (hasNull.get()) {
            return;
        }
        Method declaredMethod;
        try {
            declaredMethod = handler.getDeclaredMethod("handler" + s, types.toArray(new Class[0]));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return;
        }
        try {
            Object result = declaredMethod.invoke(commandHandler, args.toArray());
            if (result != null) {
                channelHandlerContext.writeAndFlush(new TextWebSocketFrame(new JSONObject().fluentPut("op", command).fluentPut("data", result).toJSONString()));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    @SneakyThrows
    public static void handlerJoin(Class<? extends NettyCommandHandler> handler, NettyCommandHandler commandHandler,ChannelHandlerContext channelHandlerContext) {
        Method handlerJoin = handler.getDeclaredMethod("handlerJoin", ChannelHandlerContext.class);
        handlerJoin.invoke(commandHandler, channelHandlerContext);
    }
    @SneakyThrows
    public static void handlerClose(Class<? extends NettyCommandHandler> handler, NettyCommandHandler commandHandler, ChannelHandlerContext channelHandlerContext) {
        Method handlerJoin = handler.getDeclaredMethod("handlerClose", ChannelHandlerContext.class);
        handlerJoin.invoke(commandHandler, channelHandlerContext);
    }
    @SneakyThrows
    public static void handlerError(Class<? extends NettyCommandHandler> handler, NettyCommandHandler commandHandler, ChannelHandlerContext channelHandlerContext, Throwable cause) {
        Method handlerJoin = handler.getDeclaredMethod("handlerError", ChannelHandlerContext.class, Throwable.class);
        handlerJoin.invoke(commandHandler, channelHandlerContext, cause);
    }
    private Class<T> handler;
    private T obj;
    @SneakyThrows
    public NettyUtils(Class<T> handler){
        this.handler = handler;
        Constructor<T> constructor = handler.getConstructor();
        this.obj = constructor.newInstance();
    }
    public NettyUtils(Class<T> handler, ConfigurableApplicationContext configurableApplicationContext){
        this.handler = handler;
        obj = configurableApplicationContext.getBean(handler);
    }
    public void handler(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        NettyUtils.handler(this.handler, obj, channelHandlerContext, textWebSocketFrame);
    }

    public void handlerJoin(ChannelHandlerContext channelHandlerContext) {
        NettyUtils.handlerJoin(this.handler, obj,channelHandlerContext);
    }

    public void handlerClose(ChannelHandlerContext channelHandlerContext) {
        NettyUtils.handlerClose(this.handler, obj, channelHandlerContext);
    }

    public void handlerError(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        NettyUtils.handlerError(this.handler, obj, channelHandlerContext, cause);
    }

}


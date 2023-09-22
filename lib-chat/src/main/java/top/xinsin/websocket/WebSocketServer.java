package top.xinsin.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author xinsin
 * Created On 2023/9/22 09:34
 * @version 1.0
 */
@Component
@Slf4j
@ServerEndpoint("/chat/{userId}")
public class WebSocketServer {

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        log.info("{}",session);
        log.info(userId);
    }
    @OnClose
    public void onClose() {
        log.error("das");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.error(message);
    }
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        throw new RuntimeException(error);
    }
}

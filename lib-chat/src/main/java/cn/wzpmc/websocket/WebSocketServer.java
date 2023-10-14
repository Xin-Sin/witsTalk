package cn.wzpmc.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    }
    @OnClose
    public void onClose() {
    }

    @OnMessage
    public void onMessage(String message, Session session) {
    }
    @OnError
    public void onError(Session session, Throwable error) {

    }
}

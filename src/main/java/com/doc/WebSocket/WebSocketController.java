package com.doc.WebSocket;

import com.doc.Entity.ConsoleLogInfoEntity.ConsoleLogInfo;
import com.doc.config.LogConfig.MyLog4JAppender.MyLog4JAppender;
import groovy.util.logging.Slf4j;
import jline.internal.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * com.doc.controller.WebSocket 于2020/5/7 由Administrator 创建 .
 */
@Component("WebSocketController")
@ServerEndpoint("/websocket")
@Slf4j
public class WebSocketController {  

    private Session session;
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    private static CopyOnWriteArraySet<WebSocketController>
            websocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        websocketSet.add(this);
        logger.info("websocket connect success ",websocketSet.size());
    }

    @OnClose
    public void onClose() {
        websocketSet.remove(this);
        logger.info("websocket close ",websocketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        logger.info("get message ", message);
    }

    public void sendMessage(String message) {

        for (WebSocketController webSocketController : websocketSet) {
//            logger.info("发送广播消息：{}",message);
            try {
                webSocketController.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}

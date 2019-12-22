package com.ymmihw.libraries;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class SocketHandler extends TextWebSocketHandler {

  List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws InterruptedException, IOException {

    for (WebSocketSession webSocketSession : sessions) {
      if (webSocketSession.isOpen() && !session.getId().equals(webSocketSession.getId())) {
        webSocketSession.sendMessage(message);
      }
    }
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    log.info("add sessoin {}", session);
    sessions.add(session);
  }


  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    log.info("remove sessoin {}", session);
    sessions.remove(session);
  }
}

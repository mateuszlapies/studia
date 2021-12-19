package pl.edu.pk.backend.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Controller
@EnableWebSocket
public class SockHandler {
    @SendTo("/sock/broker")
    @MessageMapping("/sock/endpoint")
    public String send(TextMessage msg) {
        return msg.getPayload();
    }
}

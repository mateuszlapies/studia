package pl.edu.pk.backend.controllers;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import pl.edu.pk.backend.game.Instance;

@Controller
@EnableWebSocket
public class SockHandler {
    private Instance game;

    public SockHandler() {
        game = new Instance();
    }

    @SendTo("/sock/info")
    @MessageMapping("/info")
    public Instance Receive(Message msg) {
        UsernamePasswordAuthenticationToken obj = (UsernamePasswordAuthenticationToken) msg.getHeaders().get("simpUser");
        String id = obj.getPrincipal().toString();
        if(!game.players.contains(id)) {
            game.players.add(id);
            if (game.players.size() == 1)
                game.cezar = game.players.get(0);
        }
        return game;
    }
}

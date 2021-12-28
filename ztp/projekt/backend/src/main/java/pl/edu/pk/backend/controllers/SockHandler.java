package pl.edu.pk.backend.controllers;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import pl.edu.pk.backend.database.Card;
import pl.edu.pk.backend.database.CardRepo;
import pl.edu.pk.backend.game.GameInstance;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@EnableWebSocket
public class SockHandler {
    private final GameInstance game;
    private final List<Card> blackCards;
    private final List<Card> whiteCards;

    public SockHandler(CardRepo cardRepo) {
        this.game = new GameInstance();
        this.blackCards = cardRepo.findAllByColor('b');
        this.whiteCards = cardRepo.findAllByColor('w');
    }

    @SendTo("/sock/info")
    @MessageMapping("/info")
    public GameInstance Receive(Message<Object> msg, Principal user) {
        String id = user.getName();
        if(!game.players.contains(id)) {
            game.players.add(id);
            if (game.players.size() == 1)
                game.cezar = game.players.get(0);
        }
        if(game.started) {
            Cards(msg, user);
        }
        return game;
    }

    @SendTo("/sock/info")
    @MessageMapping("/start")
    public GameInstance Start(Message<Object> msg, Principal user) {
        String id = user.getName();
        if(!game.started && game.players.size() > 1) {
            if (game.cezar.equals(id)) {
                game.started = true;
                Collections.shuffle(blackCards);
                game.blackCard = blackCards.get(0).id;
                blackCards.remove(0);
                Collections.shuffle(whiteCards);
                for (String player : game.players) {
                    List<Card> temp = whiteCards.subList(whiteCards.size() - 12, whiteCards.size() - 1);
                    game.playerCards.put(player, temp.stream().map(Card::getId).collect(Collectors.toList()));
                    whiteCards.removeAll(temp);
                }
            }
        }
        return game;
    }

    @SendToUser("/sock/cards")
    @MessageMapping("/cards")
    public List<String> Cards(@Payload Message<Object> msg, Principal user) {
        return game.playerCards.get(user.getName());
    }
}

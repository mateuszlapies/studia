package pl.edu.pk.backend.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import pl.edu.pk.backend.database.Card;
import pl.edu.pk.backend.database.CardRepo;
import pl.edu.pk.backend.game.GameInstance;

import java.security.Principal;
import java.util.Collections;
import java.util.Hashtable;
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
    public GameInstance Receive(Principal user) {
        String id = user.getName();
        if(!game.players.contains(id)) {
            game.players.put(id, 0);
            if (game.players.size() == 1)
                game.cezar = game.players.keySet().stream().toList().get(0);
        }
        return game;
    }

    @SendTo("/sock/info")
    @MessageMapping("/start")
    public GameInstance Start(Principal user) {
        String id = user.getName();
        if(!game.started && game.players.size() > 1) {
            if (game.cezar.equals(id)) {
                game.started = true;
                Collections.shuffle(blackCards);
                game.blanks = blackCards.get(0).blanks;
                game.blackCard = blackCards.get(0).id;
                blackCards.remove(0);
                Collections.shuffle(whiteCards);
                for(String player : game.players.keySet()) {
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
    public List<String> Cards(Principal user) {
        List<String> cards = game.playerCards.get(user.getName());
        if(cards.size() < 11) {
            List<Card> temp = whiteCards.subList(whiteCards.size() - 12 + cards.size(), whiteCards.size() - 1);
            cards.addAll(temp.stream().map(Card::getId).toList());
            game.playerCards.put(user.getName(), cards);
            whiteCards.removeAll(temp);
        }
        return cards;
    }

    @SendTo("/sock/submitted")
    @MessageMapping("/submitted")
    public String SubmittedCard(@Payload List<String> cards, Principal user) {
        Hashtable<String, List<String>> whiteCards = game.get_whiteCards();
        if(whiteCards.get(user.getName()) == null) {
            game.playerCards.get(user.getName()).removeAll(cards);
            whiteCards.put(user.getName(), cards);
            game.set_whiteCards(whiteCards);
            if(whiteCards.size() == game.players.size() - 1)
                game.chosen = true;
        }
        return user.getName();
    }

    @SendTo("/sock/win")
    @MessageMapping("/select")
    public String SelectCard(@Payload int player, Principal user) {
        if(user.getName().equals(game.cezar)) {
            String p = game.get_whiteCards().keySet().stream().toList().get(player);
            game.players.put(p, game.players.get(p) + 1);
            game.get_whiteCards().clear();
            game.chosen = false;
            List<String> players = game.players.keySet().stream().toList();
            int index = players.indexOf(game.cezar) + 1;
            int size = players.size();
            game.cezar = players.get(index % size);
            game.blanks = blackCards.get(0).blanks;
            game.blackCard = blackCards.get(0).id;
            blackCards.remove(0);
            return p;
        } else
            return "";
    }
}

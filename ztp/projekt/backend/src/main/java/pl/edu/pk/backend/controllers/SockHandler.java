package pl.edu.pk.backend.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import pl.edu.pk.backend.database.Card;
import pl.edu.pk.backend.database.repos.CardRepo;
import pl.edu.pk.backend.database.User;
import pl.edu.pk.backend.database.repos.UserRepo;
import pl.edu.pk.backend.game.GameInstance;
import pl.edu.pk.backend.game.GameParameters;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@EnableWebSocket
public class SockHandler {
    private final GameInstance game;
    private final List<String> queue;
    private final List<Card> blackCards;
    private final List<Card> whiteCards;
    private final UserRepo userRepo;

    public SockHandler(CardRepo cardRepo, UserRepo userRepo) {
        this.game = new GameInstance();
        this.blackCards = cardRepo.findAllByColor('b');
        this.whiteCards = cardRepo.findAllByColor('w');
        this.userRepo = userRepo;
        this.queue = new ArrayList<>();
    }

    @SendTo("/sock/info")
    @MessageMapping("/info")
    public GameInstance Receive(Principal user) {
        String id = user.getName();
        if(!game.players.containsKey(id) && !queue.contains(id)) {
            queue.add(id);
            game.players.put(id, 0);
            if (game.players.size() == 1)
                game.cezar = queue.get(0);
        }
        return game;
    }

    @SendTo("/sock/info")
    @MessageMapping("/start")
    public GameInstance Start(GameParameters parameters, Principal user) {
        String id = user.getName();
        if(!game.started && game.players.size() > 1) {
            if (game.cezar.equals(id)) {
                game.parameters = parameters;
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
                game.timestamp = Timestamp.from(new Date(System.currentTimeMillis() + 1000L * game.parameters.time).toInstant());
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
        Hashtable<List<String>, String> whiteCards = game.get_whiteCards();
        if(whiteCards.get(user.getName()) == null) {
            game.playerCards.get(user.getName()).removeAll(cards);
            whiteCards.put(cards, user.getName());
            game.set_whiteCards(whiteCards);
            if(whiteCards.size() == game.players.size() - 1)
                game.chosen = true;
        }
        return user.getName();
    }

    @SendTo("/sock/win")
    @MessageMapping("/select")
    public String SelectCard(@Payload List<String> cards, Principal user) {
        if(user.getName().equals(game.cezar)) {
            String p = game.get_whiteCards().get(cards);
            game.players.put(p, game.players.get(p) + 1);
            if(game.players.get(p).equals(game.parameters.rounds)) {
                game.ended = true;
                Optional<User> opt = userRepo.findById(user.getName());
                if(opt.isPresent()) {
                    User usr = opt.get();
                    usr.history++;
                    userRepo.save(usr);
                }
            }
            game.get_whiteCards().clear();
            game.chosen = false;
            int index = queue.indexOf(game.cezar) + 1;
            int size = queue.size();
            game.cezar = queue.get(index % size);
            game.blanks = blackCards.get(0).blanks;
            game.blackCard = blackCards.get(0).id;
            blackCards.remove(0);
            game.timestamp = Timestamp.from(new Date(System.currentTimeMillis() + 1000L * game.parameters.time).toInstant());
            return p;
        } else
            return "";
    }

    @SendTo("/sock/info")
    @MessageMapping("/timeout")
    public GameInstance Timeout(Principal user) throws Exception {
        if(user.getName().equals(game.cezar)) {
            if(game.timestamp.before(Timestamp.from(Instant.now()))) {
                game.chosen = true;
                return game;
            }
            throw new Exception("Time still left");
        }
        throw new Exception("Not a cezar");
    }
}

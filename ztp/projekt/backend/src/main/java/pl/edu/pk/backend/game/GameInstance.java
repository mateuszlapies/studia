package pl.edu.pk.backend.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;

public class GameInstance {
    public boolean started;
    public boolean chosen;
    public boolean ended;
    public String cezar;
    public int blanks;
    public String blackCard;
    public Hashtable<String, Integer> players;
    public List<List<String>> whiteCards;
    public Timestamp timestamp;

    @JsonIgnore
    public GameParameters parameters;
    @JsonIgnore
    private Hashtable<String, List<String>> _whiteCards;
    @JsonIgnore
    public Hashtable<String, List<String>> playerCards;

    public GameInstance() {
        started = false;
        chosen = false;
        ended = false;
        players = new Hashtable<>();
        set_whiteCards(new Hashtable<>());
        playerCards = new Hashtable<>();
    }

    private void setWhiteCards(List<List<String>> values) {
        this.whiteCards = values;
    }

    public void set_whiteCards(Hashtable<String, List<String>> dictionary) {
        this._whiteCards = dictionary;
        setWhiteCards(dictionary.values().stream().toList());
    }

    public Hashtable<String, List<String>> get_whiteCards() {
        return _whiteCards;
    }
}

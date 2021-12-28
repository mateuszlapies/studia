package pl.edu.pk.backend.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class GameInstance {
    public boolean started;
    public String cezar;
    public String blackCard;
    public ArrayList<String> players;
    public List<List<String>> whiteCards;

    @JsonIgnore
    private Dictionary<String, List<String>> _whiteCards;
    @JsonIgnore
    public Dictionary<String, List<String>> playerCards;

    public GameInstance() {
        started = false;
        players = new ArrayList<>();
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
}

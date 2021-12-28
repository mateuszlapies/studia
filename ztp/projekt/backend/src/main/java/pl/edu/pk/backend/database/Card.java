package pl.edu.pk.backend.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Card {
    @Id
    public String id;
    public String text;
    public int blanks;
    public char color;

    public String getId() {
        return id;
    }
}
package pl.edu.pk.backend.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.pk.backend.database.enums.Color;

@Document
public class Card {
    @Id
    public String id;
    public String text;
    public int blanks;
    public Color color;

    public String getId() {
        return id;
    }
}
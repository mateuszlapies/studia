package pl.edu.pk.backend.database.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pk.backend.database.Card;

import java.util.List;

public interface CardRepo extends MongoRepository<Card, String> {
    List<Card> findAllByColor(char color);
}

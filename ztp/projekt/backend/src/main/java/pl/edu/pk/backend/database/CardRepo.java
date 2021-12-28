package pl.edu.pk.backend.database;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CardRepo extends MongoRepository<Card, String> {
    List<Card> findAllByColor(char color);
}

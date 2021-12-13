package pl.edu.pk.backend.database;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserRepo extends MongoRepository<User, UUID> {
    User findByUser(String username);
}

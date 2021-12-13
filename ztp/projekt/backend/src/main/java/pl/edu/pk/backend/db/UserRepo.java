package pl.edu.pk.backend.db;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserRepo extends MongoRepository<User, UUID> {

}

package pl.edu.pk.backend.database.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pk.backend.database.User;

public interface UserRepo extends MongoRepository<User, String> {
    User findByUser(String username);
}

package pl.edu.pk.backend.services;

import org.springframework.stereotype.Service;
import pl.edu.pk.backend.database.User;
import pl.edu.pk.backend.database.repos.UserRepo;

@Service
public class UserService {
    private final UserRepo users;

    public UserService(UserRepo users) {
        this.users = users;
    }

    public User getUser(String login) {
        return users.findByUser(login);
    }
}

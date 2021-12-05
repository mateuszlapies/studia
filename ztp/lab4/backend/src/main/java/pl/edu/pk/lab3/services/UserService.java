package pl.edu.pk.lab3.services;

import org.springframework.stereotype.Service;
import pl.edu.pk.lab3.beans.Role;
import pl.edu.pk.lab3.beans.User;

import java.util.HashMap;

@Service
public class UserService {
    private HashMap<String, User> users;

    public UserService() {
        users = new HashMap<>();
        users.put("user", new User("user", "user", Role.USER));
        users.put("admin", new User("admin", "admin", Role.ADMIN));
    }

    public User getUser(String login) {
        return users.get(login);
    }
}

package pl.edu.pk.backend.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.pk.backend.requests.CreateUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document
public class User {
    @Id
    public UUID id;
    @Indexed(unique = true)
    public String user;
    public String pass;
    public Role role;
    public List<Game> history;

    public User() {}
    public User(CreateUser createUser) {
        this.role = Role.User;
        this.user = createUser.user;
        this.pass = createUser.pass;
        this.history = new ArrayList<>();
    }
}

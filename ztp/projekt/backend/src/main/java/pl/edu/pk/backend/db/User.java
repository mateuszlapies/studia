package pl.edu.pk.backend.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.pk.backend.requests.CreateUser;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document
public class User {
    @Id
    public UUID id;
    @NotEmpty(message = "Username must not be empty")
    @Indexed(unique = true)
    public String user;
    @NotEmpty(message = "Password must not be empty")
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

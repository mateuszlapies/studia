package pl.edu.pk.backend.rest;

import com.mongodb.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pk.backend.db.User;
import pl.edu.pk.backend.db.UserRepo;
import pl.edu.pk.backend.requests.CreateUser;
import pl.edu.pk.backend.responses.Response;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class RestHandler {
    private final UserRepo users;

    public RestHandler(UserRepo users) {
        this.users = users;
    }

    @PostMapping
    public Response CreateUser(@RequestBody CreateUser createUser) {
        try {
            users.save(new User(createUser));
            return new Response();
        } catch (DuplicateKeyException e) {
            return new Response(HttpStatus.BAD_REQUEST, "User already exists");
        } catch (Exception e) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @GetMapping
    public Response GetUser(@RequestParam UUID id) {
        try {
            Optional<User> user = users.findById(id);
            if(user.isPresent()) {
                return new Response(HttpStatus.OK, user.get());
            } else {
                throw new Exception("User not found");
            }
        } catch (Exception e) {
            return new Response(HttpStatus.BAD_REQUEST, e);
        }
    }
}

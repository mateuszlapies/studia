package pl.edu.pk.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.pk.backend.database.User;
import pl.edu.pk.backend.database.UserRepo;
import pl.edu.pk.backend.requests.CreateUser;
import pl.edu.pk.backend.responses.Response;

import java.util.Optional;

@RestController()
@CrossOrigin(origins = "http://localhost:3000")
public class RestHandler {
    private final UserRepo users;

    public RestHandler(UserRepo users) {
        this.users = users;
    }

    @GetMapping("/me")
    public ResponseEntity<Response> GetMe(@AuthenticationPrincipal User auth) {
        try {
            User user = users.findByUser(auth.user);
            if(user != null) {
                return new Response(HttpStatus.OK, user).get();
            } else {
                throw new Exception("User not found");
            }
        } catch (Exception e) {
            return new Response(HttpStatus.BAD_REQUEST, e).get();
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Response> GetUser(@PathVariable String id) {
        try {
            Optional<User> user = users.findById(id);
            if(user.isPresent()) {
                return new Response(HttpStatus.OK, user.get()).get();
            } else {
                throw new Exception("User not found");
            }
        } catch (Exception e) {
            return new Response(HttpStatus.BAD_REQUEST, e).get();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Response> CreateUser(@RequestBody CreateUser createUser) {
        try {
            users.save(new User(createUser));
            return new Response().get();
        } catch (Exception e) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, e).get();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Response> DeleteUser(@PathVariable String id) {
        try {
            Optional<User> user = users.findById(id);
            if(user.isPresent()) {
                users.delete(user.get());
                return new Response().get();
            } else {
                throw new Exception("User not found");
            }
        } catch (Exception e) {
            return new Response(HttpStatus.BAD_REQUEST, e).get();
        }
    }
}

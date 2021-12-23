package pl.edu.pk.backend.services;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import pl.edu.pk.backend.database.User;
import pl.edu.pk.backend.database.UserRepo;

import java.util.Collections;

@Component
public class WebSocketService {
    private final UserRepo userRepo;

    public WebSocketService(UserRepo repo) {
        this.userRepo = repo;
    }

    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String  username, final String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
        }

        User user = userRepo.findByUser(username);
        if (!user.pass.equals(password)) {
            throw new BadCredentialsException("Bad credentials for user " + username);
        }

        return new UsernamePasswordAuthenticationToken(
                user.id,
                null,
                user.getAuthorities()
        );
    }
}

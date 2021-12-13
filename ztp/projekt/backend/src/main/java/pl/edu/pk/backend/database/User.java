package pl.edu.pk.backend.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.edu.pk.backend.requests.CreateUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Document
public class User implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return pass;
    }

    @Override
    public String getUsername() {
        return user;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

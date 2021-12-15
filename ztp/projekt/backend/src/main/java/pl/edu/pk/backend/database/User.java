package pl.edu.pk.backend.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public String id;
    @Indexed(unique = true)
    public String user;
    @JsonIgnore
    public String pass;
    @JsonIgnore
    public Role role;
    public List<Game> history;

    public User() {}

    public User(CreateUser createUser) {
        this.id = UUID.randomUUID().toString();
        this.role = Role.User;
        this.user = createUser.user;
        this.pass = createUser.pass;
        this.history = new ArrayList<>();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return pass;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return user;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}

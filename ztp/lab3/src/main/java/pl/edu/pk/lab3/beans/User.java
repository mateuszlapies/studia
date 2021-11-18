package pl.edu.pk.lab3.beans;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.edu.pk.lab3.requests.LoginRequest;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
    protected String userId;
    protected String login;
    protected String pass;
    final protected Role role;

    public User() {role = Role.USER;}

    public User(String login, String pass, Role role) {
        this.login = login;
        this.pass = pass;
        this.role = role;
        this.userId = getBase64FromString(login);
    }

    public User(LoginRequest request, Role role) {
        this.login = request.login;
        this.pass = request.pass;
        this.role = role;
        this.userId = getBase64FromString(login);
    }

    private String getBase64FromString(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public String getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public Role getRole() {
        return role;
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
        return login;
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

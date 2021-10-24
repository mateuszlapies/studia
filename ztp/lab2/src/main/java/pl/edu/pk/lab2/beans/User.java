package pl.edu.pk.lab2.beans;

import pl.edu.pk.lab2.requests.LoginRequest;

import java.util.Base64;

public class User {
    protected String userId;
    protected String login;
    protected String pass;
    final protected Role role;

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
}

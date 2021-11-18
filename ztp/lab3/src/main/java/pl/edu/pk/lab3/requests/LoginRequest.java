package pl.edu.pk.lab3.requests;

public class LoginRequest {
    public String login;
    public String pass;

    public boolean isAdmin() {
        return "admin".equals(login) && "admin".equals(pass);
    }
}

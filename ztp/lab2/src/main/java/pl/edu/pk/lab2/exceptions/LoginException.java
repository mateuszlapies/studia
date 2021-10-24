package pl.edu.pk.lab2.exceptions;

public class LoginException extends Exception {
    public LoginException() {
        super("Invalid login or password");
    }
}

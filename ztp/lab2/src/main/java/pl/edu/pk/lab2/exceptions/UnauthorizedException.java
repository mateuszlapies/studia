package pl.edu.pk.lab2.exceptions;

public class UnauthorizedException extends Exception {
    public UnauthorizedException() {
        super("Unauthorized user");
    }
}

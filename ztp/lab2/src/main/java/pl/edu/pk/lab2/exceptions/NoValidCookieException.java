package pl.edu.pk.lab2.exceptions;

public class NoValidCookieException extends Exception {
    public NoValidCookieException() {
        super("No valid cookie");
    }
}

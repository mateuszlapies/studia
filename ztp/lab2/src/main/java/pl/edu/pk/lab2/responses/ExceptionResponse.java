package pl.edu.pk.lab2.responses;

public class ExceptionResponse extends Response {
    public ExceptionResponse() {}

    public ExceptionResponse(int status, Exception e) {
        super(status, e.getMessage());
    }
}

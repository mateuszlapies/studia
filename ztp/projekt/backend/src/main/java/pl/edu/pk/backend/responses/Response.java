package pl.edu.pk.backend.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
    public int code;
    public Object message;
    private final HttpStatus status;

    public Response() {
        this(HttpStatus.OK);
        this.message = "OK";
    }

    public Response(HttpStatus status) {
        this.status = status;
        this.code = status.value();
    }

    public Response(HttpStatus status, Exception exception) {
        this(status);
        this.message = exception.getMessage();
    }

    public Response(HttpStatus status, Object payload) {
        this(status);
        message = payload;
    }

    public ResponseEntity<Response> get() {
        return new ResponseEntity<>(this, this.status);
    }
}

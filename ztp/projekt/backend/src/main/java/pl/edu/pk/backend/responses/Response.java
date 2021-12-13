package pl.edu.pk.backend.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import pl.edu.pk.backend.utilities.Json;

public class Response {
    public int code;
    public String message;

    public Response() {
        code = HttpStatus.OK.value();
        message = "OK";
    }

    public Response(HttpStatus status, String message) {
        this.code = status.value();
        this.message = message;
    }

    public Response(HttpStatus status, Exception exception) {
        code = status.value();
        message = exception.getMessage();
    }

    public Response(HttpStatus status, Object payload) throws JsonProcessingException {
        code = status.value();
        message = Json.stringify(payload);
    }
}

package pl.edu.pk.backend.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
    private static ObjectMapper objectMapper;

    private static void init() {
        if(objectMapper == null)
            objectMapper = new ObjectMapper();
    }

    public static String stringify(Object item) throws JsonProcessingException {
        init();
        return objectMapper.writeValueAsString(item);
    }

    /*public static <T> T parse(String string, Class<T> c) throws JsonProcessingException {
        init();
        return objectMapper.readValue(string, c);
    }*/
}

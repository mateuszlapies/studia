package pl.edu.pk.backend.security;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import pl.edu.pk.backend.services.WebSocketService;

import java.util.Base64;

@Component
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {
    private static final String AUTH_HEADER = "Authorization";
    private final WebSocketService webSocketService;

    public AuthChannelInterceptorAdapter(final WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT == accessor.getCommand()) {
            final String auth = accessor.getFirstNativeHeader(AUTH_HEADER);
            final String encodedString = auth.replaceAll("Basic ", "");
            final byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
            final String decodedString = new String(decodedBytes);
            final String[] array = decodedString.split(":");
            final String username = array[0];
            final String password = array[1];
            final UsernamePasswordAuthenticationToken user = webSocketService.getAuthenticatedOrFail(username, password);
            accessor.setUser(user);
        }
        return message;
    }
}

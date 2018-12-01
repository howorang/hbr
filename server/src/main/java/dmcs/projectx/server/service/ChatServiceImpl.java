package dmcs.projectx.server.service;

import common.api.ChatService;
import common.api.Credentials;
import dmcs.projectx.server.auth.AuthProvider;
import dmcs.projectx.server.event.events.MessageSentEvent;
import dmcs.projectx.server.event.events.UserLoggedInEvent;
import dmcs.projectx.server.event.events.UserLoggedOutEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final AuthProvider authProvider;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public String logIn(String username) {
        String authToken = authProvider.logIn(username);
        eventPublisher.publishEvent(UserLoggedInEvent.builder()
                .credentials(new Credentials(username, authToken)).build());
        return authToken;
    }

    @Override
    public void sendDirectMessage(Credentials credentials, String targetName, String message) {
        eventPublisher.publishEvent(MessageSentEvent
                .builder()
                .credentials(credentials)
                .message(message)
                .targetName(targetName)
                .build());
    }

    @Override
    public void logOut(Credentials credentials) {
        authProvider.logOut(credentials.getUsername(), credentials.getToken());
        eventPublisher.publishEvent(UserLoggedOutEvent.builder()
                .credentials(credentials)
                .build());
    }

    @Override
    public List<String> getUsers() {
        return new ArrayList<>(authProvider.getActiveUsers());
    }
}

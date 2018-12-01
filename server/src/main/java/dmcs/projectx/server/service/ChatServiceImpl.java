package dmcs.projectx.server.service;

import common.api.ChatService;
import common.api.Credentials;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    @Override
    public boolean logIn(Credentials credentials) {
        return false;
    }

    @Override
    public void sendDirectMessage(Credentials credentials, String message) {

    }

    @Override
    public boolean logOut(Credentials credentials) {
        return false;
    }
}

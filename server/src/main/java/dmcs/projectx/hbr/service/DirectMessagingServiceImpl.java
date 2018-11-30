package dmcs.projectx.hbr.service;

import dmcs.projectx.hbr.auth.Credentials;
import org.springframework.stereotype.Service;

@Service
public class DirectMessagingServiceImpl implements DirectMessagingService {
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

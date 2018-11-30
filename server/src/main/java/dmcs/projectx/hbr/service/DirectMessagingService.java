package dmcs.projectx.hbr.service;

import dmcs.projectx.hbr.auth.Credentials;

public interface DirectMessagingService {
    boolean logIn(Credentials credentials);
    void sendDirectMessage(Credentials credentials, String message);
    boolean logOut(Credentials credentials);
}

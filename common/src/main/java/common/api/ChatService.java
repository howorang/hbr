package common.api;

import java.util.List;

public interface ChatService {
    String logIn(String username);
    void sendDirectMessage(Credentials credentials, String targetName, String message);
    void logOut(Credentials credentials);
    List<String> getUsers();
}

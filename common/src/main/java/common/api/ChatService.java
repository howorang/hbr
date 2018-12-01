package common.api;

public interface ChatService {
    boolean logIn(Credentials credentials);
    void sendDirectMessage(Credentials credentials, String message);
    boolean logOut(Credentials credentials);
}

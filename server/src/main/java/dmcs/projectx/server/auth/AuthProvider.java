package dmcs.projectx.server.auth;

import common.api.exception.exception.AuthExpection;
import common.api.exception.exception.BadCredentialsException;

import java.util.Set;

public interface AuthProvider {

    String logIn(String username) throws AuthExpection;

    void logOut(String username, String authToken) throws BadCredentialsException;

    boolean isAuthenticated(String username, String authToken);

    Set<String> getActiveUsers();

    String getTokenFor(String username);
}

package dmcs.projectx.server.auth;

import common.api.Credentials;
import dmcs.projectx.server.auth.exception.AuthExpection;
import dmcs.projectx.server.auth.exception.BadCredentialsException;

import java.util.Set;

public interface AuthProvider {

    String logIn(String username) throws AuthExpection;

    void logOut(String username, String authToken) throws BadCredentialsException;

    boolean isAuthenticated(String username, String authToken);

    Set<String> getActiveUsers();
}

package dmcs.projectx.server.auth;

import common.api.Credentials;
import dmcs.projectx.server.auth.exception.AuthExpection;
import dmcs.projectx.server.auth.exception.BadCredentialsException;

public interface AuthProvider {

    String logIn(Credentials credentials) throws AuthExpection;

    void logOut(String username, String authToken) throws BadCredentialsException;

    boolean isAuthenticated(String username, String authToken);
}

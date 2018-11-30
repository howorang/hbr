package dmcs.projectx.hbr.auth;

import dmcs.projectx.hbr.auth.exception.AuthExpection;
import dmcs.projectx.hbr.auth.exception.BadCredentialsException;

public interface AuthProvider {

    String logIn(Credentials credentials) throws AuthExpection;

    void logOut(String username, String authToken) throws BadCredentialsException;

    boolean isAuthenticated(String username, String authToken);
}

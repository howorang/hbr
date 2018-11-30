package dmcs.projectx.hbr.auth;

import dmcs.projectx.hbr.auth.exception.AlreadyLoggedInException;
import dmcs.projectx.hbr.auth.exception.AuthExpection;
import dmcs.projectx.hbr.auth.exception.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
class AuthProviderImpl implements AuthProvider {

    private Map<String, String> usernameTokenMap = new HashMap<>();

    @Override
    public String logIn(Credentials credentials) throws AuthExpection {
        String username = credentials.getUsername();
        if(!usernameTokenMap.containsKey(username)) {
            return doLogIn(credentials);
        } else {
            throw handleAlreadyAuthenticated(credentials);
        }
    }

    private AuthExpection handleAlreadyAuthenticated(Credentials credentials) {
        String username = credentials.getUsername();
        if (isAuthenticated(username, credentials.getToken())) {
            return new AlreadyLoggedInException();
        } return new BadCredentialsException();
    }

    private String doLogIn(Credentials credentials) {
        String uuid = UUID.randomUUID().toString();
        usernameTokenMap.put(credentials.getUsername(), uuid);
        return uuid;
    }

    @Override
    public void logOut(String username, String authToken) throws BadCredentialsException {
        if (isAuthenticated(username, authToken)) {
            usernameTokenMap.remove(username);
        } else {
            throw new BadCredentialsException();
        }
    }

    @Override
    public boolean isAuthenticated(String username, String authToken) {
        String token = usernameTokenMap.get(username);
        return token != null && token.equals(authToken);
    }
}

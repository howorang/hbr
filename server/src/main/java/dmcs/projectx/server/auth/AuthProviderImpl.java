package dmcs.projectx.server.auth;

import common.api.Credentials;
import dmcs.projectx.server.auth.exception.AlreadyLoggedInException;
import dmcs.projectx.server.auth.exception.AuthExpection;
import dmcs.projectx.server.auth.exception.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
class AuthProviderImpl implements AuthProvider {

    private Map<String, String> usernameTokenMap = new HashMap<>();

    @Override
    public String logIn(String username) throws AuthExpection {
        if(!usernameTokenMap.containsKey(username)) {
            return doLogIn(username);
        } else {
            String token = usernameTokenMap.get(username);
            throw handleAlreadyAuthenticated(username, token);
        }
    }

    private AuthExpection handleAlreadyAuthenticated(String username, String token) {
        if (isAuthenticated(username, token)) {
            return new AlreadyLoggedInException();
        } return new BadCredentialsException();
    }

    private String doLogIn(String username) {
        String uuid = UUID.randomUUID().toString();
        usernameTokenMap.put(username, uuid);
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

    @Override
    public Set<String> getActiveUsers() {
        return usernameTokenMap.keySet();
    }
}

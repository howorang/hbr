package dmcs.projectx.client;

import common.api.Credentials;
import dmcs.projectx.client.api.PROTOCOL_TYPE;

public class UserContextHolder {
    private static UserContextHolder ourInstance = new UserContextHolder();

    public static UserContextHolder getInstance() {
        return ourInstance;
    }

    private PROTOCOL_TYPE protocolType = PROTOCOL_TYPE.HESSIAN;
    private Credentials credentials;

    public PROTOCOL_TYPE getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(PROTOCOL_TYPE protocolType) {
        this.protocolType = protocolType;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    private UserContextHolder() {
    }
}

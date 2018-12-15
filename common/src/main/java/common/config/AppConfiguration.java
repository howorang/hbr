package common.config;

public interface AppConfiguration {
    String SYSTEM_EVENTS_TOPIC_NAME = "user-changes";
    String SERVER_URL = "http://localhost:8080/";
    String BURLAP_ENDPOINT = "burlap_direct";
    String HESSIAN_ENDPOINT = "hessian_direct";
    String XMLRPC_ENDPOINT = "xmlrpc_direct";
}
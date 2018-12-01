package common.config;

public interface AppConfiguration {
    String USER_EVENTS_EXCHANGE_NAME = "user-events";
    String SYSTEM_EVENTS_QUEUE_NAME = "system";
    String RABBIT_ADMIN_USERNAME = "test";
    String RABBIT_ADMIN_PASSWORD = "test";
    String RABBIT_HOST = "localhost";
    int RABBIT_PORT = 5672;
    String SERVER_URL = "http://localhost:8080/";
    String BURLAP_ENDPOINT = "burlap_direct";
    String HESSIAN_ENDPOINT = "hessian_direct";
    String XMLRPC_ENDPOINT = "xmlrpc_direct";
}
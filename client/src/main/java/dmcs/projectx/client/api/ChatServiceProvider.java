package dmcs.projectx.client.api;

import common.api.ChatService;

import java.util.EnumMap;
import java.util.Map;

import static common.config.AppConfiguration.*;

public class ChatServiceProvider {

    private static ChatServiceProvider INSTANCE = new ChatServiceProvider();

    private ChatServiceProvider() {

    }

    public static ChatServiceProvider getInstance() {
        return INSTANCE;
    }

    private Map<PROTOCOL_TYPE, ChatService> factoryMap = new EnumMap<>(PROTOCOL_TYPE.class);

    public ChatService get() throws Exception {
        return get(PROTOCOL_TYPE.HESSIAN);
    }

    public ChatService get(PROTOCOL_TYPE protocolType) {
        ChatService chatService = factoryMap
                .computeIfAbsent(protocolType, protocolType1 -> createNewFactory(protocolType).get());
        return chatService;
    }

    private ChatServiceFactory createNewFactory(PROTOCOL_TYPE protocolType) {
        switch (protocolType) {
            case BURLAP:
                return new BurlapChatServiceFactory(SERVER_URL + BURLAP_ENDPOINT);
            case HESSIAN:
                return new HessianChatServiceFactory(SERVER_URL + HESSIAN_ENDPOINT);
            case XML_RPC:
                return new XmlRpcChatServiceFactory(SERVER_URL + XMLRPC_ENDPOINT);
            default:
                return new HessianChatServiceFactory(SERVER_URL + HESSIAN_ENDPOINT);
        }
    }
}

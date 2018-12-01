package dmcs.projectx.client.api;

import common.api.ChatService;

import java.util.EnumMap;
import java.util.Map;

import static common.config.AppConfiguration.*;

public class ChatServiceProvider {

    private Map<PROTOCOL_TYPE, ChatServiceFactory> factoryMap = new EnumMap<>(PROTOCOL_TYPE.class);

    public ChatService get(PROTOCOL_TYPE protocolType) throws Exception {
        ChatServiceFactory factory = factoryMap
                .computeIfAbsent(protocolType, protocolType1 -> createNewFactory(protocolType));
        return factory.get();
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
                return null;
        }
    }
}

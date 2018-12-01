package dmcs.projectx.client.api;

import common.api.ChatService;
import common.api.Credentials;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class XmlRpcChatServiceFactory implements ChatServiceFactory{

    private XmlRpcClient xmlRpcClient;

    public XmlRpcChatServiceFactory(String url) {
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL(url));
            xmlRpcClient = new XmlRpcClient();
            xmlRpcClient.setConfig(config);
        } catch (Exception e) {
            throw new XmlRpcProxyException(e);
        }

    }

    @Override
    public ChatService get() throws Exception {
        return new XmlRpcChatServiceProxy(xmlRpcClient);
    }

    static class XmlRpcChatServiceProxy implements ChatService {

        private XmlRpcClient xmlRpcClient;

        private XmlRpcChatServiceProxy(XmlRpcClient xmlRpcClient) {
            this.xmlRpcClient = xmlRpcClient;
        }

        @Override
        public void logIn(String username) {
            try {
                xmlRpcClient.execute("logIn", Collections.singletonList(username));
            } catch (XmlRpcException e) {
                throw new XmlRpcProxyException(e);
            }
        }

        @Override
        public void sendDirectMessage(Credentials credentials, String targetName, String message) {
            try {
                xmlRpcClient.execute("sendDirectMessage", Arrays.asList(credentials, targetName, message));
            } catch (XmlRpcException e) {
                throw new XmlRpcProxyException(e);
            }
        }


        @Override
        public void logOut(Credentials credentials) {
            try {
               xmlRpcClient.execute("logOut", Collections.singletonList(credentials));
            } catch (XmlRpcException e) {
                throw new XmlRpcProxyException(e);
            }
        }

        @Override
        public List<String> getUsers() {
            try {
                return (List<String>) xmlRpcClient.execute("getUsers", Arrays.asList());
            } catch (XmlRpcException e) {
                throw new XmlRpcProxyException(e);
            }
        }
    }
}

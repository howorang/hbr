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

public class XmlRpcChatServiceFactory implements ChatServiceFactory{

    private XmlRpcClient xmlRpcClient;

    public XmlRpcChatServiceFactory(String url) throws MalformedURLException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(url));
        xmlRpcClient = new XmlRpcClient();
        xmlRpcClient.setConfig(config);

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
        public boolean logIn(Credentials credentials) {
            try {
               return (Boolean) xmlRpcClient.execute("logIn", Collections.singletonList(credentials));
            } catch (XmlRpcException e) {
                throw new XmlRpcProxyException(e);
            }
        }

        @Override
        public void sendDirectMessage(Credentials credentials, String message) {
            try {
                xmlRpcClient.execute("sendDirectMessage", Arrays.asList(credentials, message));
            } catch (XmlRpcException e) {
                throw new XmlRpcProxyException(e);
            }
        }

        @Override
        public boolean logOut(Credentials credentials) {
            try {
                return (Boolean) xmlRpcClient.execute("logOut", Collections.singletonList(credentials));
            } catch (XmlRpcException e) {
                throw new XmlRpcProxyException(e);
            }
        }
    }
}

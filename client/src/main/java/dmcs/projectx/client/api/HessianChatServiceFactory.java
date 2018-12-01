package dmcs.projectx.client.api;

import com.caucho.hessian.client.HessianProxyFactory;
import common.api.ChatService;

import java.net.MalformedURLException;

public class HessianChatServiceFactory implements ChatServiceFactory {
    private HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
    private String url;

    public HessianChatServiceFactory(String url) {
        this.url = url;
    }


    @Override
    public ChatService get() {
        try {
            return (ChatService) hessianProxyFactory.create(ChatService.class, url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

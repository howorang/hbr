package dmcs.projectx.server.configuration;

import common.api.ChatService;
import lombok.RequiredArgsConstructor;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.metadata.XmlRpcSystemImpl;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.webserver.XmlRpcServletServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.BurlapServiceExporter;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.web.HttpRequestHandler;


@Configuration
@RequiredArgsConstructor
public class RmiConfiguration {

    private final ChatService chatService;

    @Bean("/hessian_direct")
    public RemoteExporter hessianServiceExporter() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(chatService);
        exporter.setServiceInterface(ChatService.class);
        return exporter;
    }

    @Bean("/burlap_direct")
    public RemoteExporter burlapServiceExporter() {
        BurlapServiceExporter exporter = new BurlapServiceExporter();
        exporter.setService(chatService);
        exporter.setServiceInterface(ChatService.class);
        return exporter;
    }

    @Bean(name = "/xmlrpc_direct")
    HttpRequestHandler xmlRpcService() throws XmlRpcException {
        PropertyHandlerMapping handlerMapping = new PropertyHandlerMapping();
        handlerMapping.setRequestProcessorFactoryFactory(pClass -> pRequest -> chatService);
        handlerMapping.addHandler(ChatService.class.getSimpleName(), ChatService.class);
        XmlRpcSystemImpl.addSystemHandler(handlerMapping);
        XmlRpcServletServer server = new XmlRpcServletServer();
        server.setHandlerMapping(handlerMapping);
        return server::execute;
    }

}
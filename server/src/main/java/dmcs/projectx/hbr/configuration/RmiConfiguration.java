package dmcs.projectx.hbr.configuration;

import dmcs.projectx.hbr.service.DirectMessagingService;
import lombok.RequiredArgsConstructor;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.metadata.XmlRpcSystemImpl;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.webserver.XmlRpcServletServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.BurlapServiceExporter;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.web.HttpRequestHandler;


@Configuration
@RequiredArgsConstructor
public class RmiConfiguration {

    private final DirectMessagingService directMessagingService;

    @Bean("/hessian_direct")
    public RemoteExporter hessianServiceExporter() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(directMessagingService);
        exporter.setServiceInterface(DirectMessagingService.class);
        return exporter;
    }

    @Bean("/burlap_direct")
    public RemoteExporter burlapServiceExporter() {
        BurlapServiceExporter exporter = new BurlapServiceExporter();
        exporter.setService(directMessagingService);
        exporter.setServiceInterface(DirectMessagingService.class);
        return exporter;
    }

    @Bean(name = "/xmlrpc_direct")
    HttpRequestHandler xmlRpcService() throws XmlRpcException {
        PropertyHandlerMapping handlerMapping = new PropertyHandlerMapping();
        handlerMapping.setRequestProcessorFactoryFactory(pClass -> pRequest -> directMessagingService);
        handlerMapping.addHandler(DirectMessagingService.class.getSimpleName(), DirectMessagingService.class);
        XmlRpcSystemImpl.addSystemHandler(handlerMapping);
        XmlRpcServletServer server = new XmlRpcServletServer();
        server.setHandlerMapping(handlerMapping);
        return server::execute;
    }

}
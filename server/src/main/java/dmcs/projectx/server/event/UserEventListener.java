package dmcs.projectx.server.event;

import dmcs.projectx.server.event.events.UserLoggedInEvent;
import dmcs.projectx.server.event.events.UserLoggedOutEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static common.config.AppConfiguration.USER_EVENTS_EXCHANGE_NAME;

@Component
@RequiredArgsConstructor
public class UserEventListener {


    private static final String DIRECT_EXCHANGE = "direct";
    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin rabbitAdmin;

    @EventListener
    public void handleUserLoggedIn(UserLoggedInEvent event) {
        rabbitAdmin.declareQueue(buildQueue(event));
        rabbitAdmin.declareExchange(createExchange(event));
        rabbitAdmin.declareBinding(createBinding(event));
        rabbitTemplate.convertAndSend(USER_EVENTS_EXCHANGE_NAME,
                "",
                "ADDED:"+event.getCredentials().getUsername());
    }

    private Binding createBinding(UserLoggedInEvent event) {
        return new Binding(event.getCredentials().getToken(), Binding.DestinationType.QUEUE, event.getCredentials().getToken(), "", null);
    }

    private Exchange createExchange(UserLoggedInEvent event) {
        return new DirectExchange(event.getCredentials().getToken(), false, false, null);
    }

    private Queue buildQueue(UserLoggedInEvent event) {
        return new Queue(event.getCredentials().getToken(), false, false, false, null);
    }

    @EventListener
    public void handleUserLoggedOut(UserLoggedOutEvent event) {
        rabbitAdmin.deleteQueue(event.getCredentials().getToken());
        rabbitTemplate.convertAndSend(USER_EVENTS_EXCHANGE_NAME,
                "",
                "LEFT" + event.getCredentials().getUsername());
    }
}

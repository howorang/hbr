package dmcs.projectx.server.event;

import common.message.UserLoggedInMesssage;
import common.message.UserLoggedOutMessage;
import dmcs.projectx.server.event.events.UserLoggedInEvent;
import dmcs.projectx.server.event.events.UserLoggedOutEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static common.config.AppConfiguration.USER_EVENTS_EXCHANGE_NAME;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final RabbitTemplate rabbitTemplate;

    @EventListener
    public void handleUserLoggedIn(UserLoggedInEvent event) {
        rabbitTemplate.convertAndSend(USER_EVENTS_EXCHANGE_NAME,
                "",
                new UserLoggedInMesssage(event.getCredentials().getUsername()));
    }

    @EventListener
    public void handleUserLoggedOut(UserLoggedOutEvent event) {
        rabbitTemplate.convertAndSend(USER_EVENTS_EXCHANGE_NAME,
                "",
                new UserLoggedOutMessage(event.getCredentials().getUsername()));
    }
}

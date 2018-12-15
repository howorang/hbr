package dmcs.projectx.server.event;

import dmcs.projectx.server.event.events.UserLoggedInEvent;
import dmcs.projectx.server.event.events.UserLoggedOutEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static common.config.AppConfiguration.SYSTEM_EVENTS_TOPIC_NAME;


@Component
@RequiredArgsConstructor
public class UserEventListener {


    private static final String DIRECT_EXCHANGE = "direct";
    private final JmsTemplate jmsTemplate;

    @EventListener
    public void handleUserLoggedIn(UserLoggedInEvent event) {
         jmsTemplate.convertAndSend(SYSTEM_EVENTS_TOPIC_NAME,
                "ADDED:"+event.getCredentials().getUsername());
    }

    @EventListener
    public void handleUserLoggedOut(UserLoggedOutEvent event) {
        jmsTemplate.convertAndSend(SYSTEM_EVENTS_TOPIC_NAME,
                "LEFT" + event.getCredentials().getUsername());
    }
}

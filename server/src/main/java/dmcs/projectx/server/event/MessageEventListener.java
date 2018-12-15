package dmcs.projectx.server.event;

import dmcs.projectx.server.auth.AuthProvider;
import dmcs.projectx.server.event.events.MessageSentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageEventListener {

    private final JmsTemplate jmsTemplate;

    private final AuthProvider authProvider;

    @EventListener
    public void handleMessageSentEvent(MessageSentEvent event) {
        jmsTemplate.convertAndSend(authProvider.getTokenFor(event.getTargetName()), event.getMessage());
    }
}

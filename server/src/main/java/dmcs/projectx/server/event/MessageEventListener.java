package dmcs.projectx.server.event;

import dmcs.projectx.server.auth.AuthProvider;
import dmcs.projectx.server.event.events.MessageSentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageEventListener {

    private final RabbitTemplate rabbitTemplate;

    private final AuthProvider authProvider;

    @EventListener
    public void handleMessageSentEvent(MessageSentEvent event) {
        rabbitTemplate.convertAndSend(authProvider.getTokenFor(event.getTargetName()), "", event.getMessage());
    }
}

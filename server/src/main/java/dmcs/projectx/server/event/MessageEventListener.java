package dmcs.projectx.server.event;

import dmcs.projectx.server.event.events.MessageSentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageEventListener {

    private final RabbitTemplate rabbitTemplate;

    @EventListener
    public void handleMessageSentEvent(MessageSentEvent event) {
        rabbitTemplate.convertAndSend(event.getCredentials().getToken(), "", event.getMessage());
    }
}

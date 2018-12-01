package dmcs.projectx.server.event;

import dmcs.projectx.server.event.events.MessageSentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageEventListener {

    @EventListener
    public void handleMessageSentEvent(MessageSentEvent event) {

    }
}

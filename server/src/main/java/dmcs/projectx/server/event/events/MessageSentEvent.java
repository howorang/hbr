package dmcs.projectx.server.event.events;

import common.api.Credentials;
import lombok.Builder;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@Builder
public class MessageSentEvent extends ApplicationEvent {
    private Credentials credentials;
    private String message;
    private String targetName;
}

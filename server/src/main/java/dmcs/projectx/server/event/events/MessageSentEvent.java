package dmcs.projectx.server.event.events;

import common.api.Credentials;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MessageSentEvent {
    private Credentials credentials;
    private String message;
    private String targetName;
}

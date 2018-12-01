package dmcs.projectx.server.event.events;

import common.api.Credentials;
import lombok.Builder;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@Builder
public class UserLoggedInEvent {
    private Credentials credentials;
}

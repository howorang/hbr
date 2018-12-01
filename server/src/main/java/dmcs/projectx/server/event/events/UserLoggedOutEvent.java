package dmcs.projectx.server.event.events;

import common.api.Credentials;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserLoggedOutEvent {
    private Credentials credentials;
}

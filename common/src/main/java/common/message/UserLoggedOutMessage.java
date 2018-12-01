package common.message;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class UserLoggedOutMessage {
    private String username;
}

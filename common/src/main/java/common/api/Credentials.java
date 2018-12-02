package common.api;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
public class Credentials implements Serializable {
    private String username;
    private String token;
}

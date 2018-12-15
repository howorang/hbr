package dmcs.projectx.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableAutoConfiguration
@SpringBootApplication
@EnableJms
public class HbrApplication {

    public static void main(String[] args) {
        SpringApplication.run(HbrApplication.class, args);
    }
}

package dmcs.projectx.server.configuration;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Topic;

import static common.config.AppConfiguration.SYSTEM_EVENTS_TOPIC_NAME;

@Configuration
public class QueueConfiguration {

    @Bean
    Topic userChangesTopic() {
        return new ActiveMQTopic(SYSTEM_EVENTS_TOPIC_NAME);
    }

}


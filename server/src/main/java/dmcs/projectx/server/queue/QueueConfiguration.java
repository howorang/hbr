package dmcs.projectx.server.queue;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static common.config.AppConfiguration.SYSTEM_EVENTS_QUEUE_NAME;
import static common.config.AppConfiguration.USER_EVENTS_EXCHANGE_NAME;

@Configuration
public class QueueConfiguration {


    @Bean(name = SYSTEM_EVENTS_QUEUE_NAME)
    Queue queue() {
        return new Queue(SYSTEM_EVENTS_QUEUE_NAME, false);
    }

    @Bean(name = USER_EVENTS_EXCHANGE_NAME)
    FanoutExchange exchange() {
        return new FanoutExchange(USER_EVENTS_EXCHANGE_NAME);
    }

    @Bean
    Binding binding(@Qualifier(SYSTEM_EVENTS_QUEUE_NAME) Queue queue,
                    @Qualifier(USER_EVENTS_EXCHANGE_NAME) FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}


package dmcs.projectx.server.queue;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static common.config.AppConfiguration.*;

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

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(RABBIT_HOST, RABBIT_PORT);
        connectionFactory.setUsername(RABBIT_ADMIN_USERNAME);
        connectionFactory.setPassword(RABBIT_ADMIN_PASSWORD);
        return connectionFactory;
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;
    }
}


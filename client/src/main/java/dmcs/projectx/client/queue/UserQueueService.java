package dmcs.projectx.client.queue;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static common.config.AppConfiguration.SYSTEM_EVENTS_QUEUE_NAME;

public class UserQueueService extends AbstractServiceQueue {
    public UserQueueService() throws IOException, TimeoutException {
    }

    public void listenForUserChanges(Consumer consumer) throws IOException {
        channel.basicConsume(SYSTEM_EVENTS_QUEUE_NAME, false, "", consumer);
    }
}

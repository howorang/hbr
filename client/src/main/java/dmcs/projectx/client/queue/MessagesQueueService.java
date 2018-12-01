package dmcs.projectx.client.queue;

import com.rabbitmq.client.Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessagesQueueService extends AbstractServiceQueue {

    private String authToken;

    public MessagesQueueService(String authToken) throws IOException, TimeoutException {
        this.authToken = authToken;
    }


    public void listenForChatMessages(Consumer consumer) throws IOException {
        channel.basicConsume(authToken, true, authToken, consumer);
    }

    @Override
    public void close() throws IOException, TimeoutException {
        channel.basicCancel(authToken);
        channel.close();
        connection.close();
    }
}

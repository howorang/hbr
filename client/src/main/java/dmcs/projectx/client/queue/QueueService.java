package dmcs.projectx.client.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueService implements AutoCloseable {

    private final Channel channel;
    private final Connection connection;
    private String authToken;

    public QueueService(String authToken) throws IOException, TimeoutException {
        this.authToken = authToken;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
    }


    public void start(Consumer consumer) throws IOException {
        channel.basicConsume(authToken, true, authToken, consumer);
    }

    @Override
    public void close() throws IOException, TimeoutException {
        channel.basicCancel(authToken);
        channel.close();
        connection.close();
    }
}

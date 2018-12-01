package dmcs.projectx.client.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

abstract class AbstractServiceQueue implements AutoCloseable {
    protected final Channel channel;
    protected final Connection connection;

    AbstractServiceQueue() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
    }


    @Override
    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}

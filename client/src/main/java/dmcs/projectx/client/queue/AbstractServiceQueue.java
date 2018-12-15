package dmcs.projectx.client.queue;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

abstract class AbstractServiceQueue implements AutoCloseable {
    protected final Connection connection;
    protected final Session session;

    protected AbstractServiceQueue() throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        this.connection = factory.createConnection();
        connection.start();
        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    protected void listenToQueue(String queue, MessageListener listener) throws JMSException {
        Queue q1 = session.createQueue(queue);
        MessageConsumer consumer = session.createConsumer(q1);
        consumer.setMessageListener(listener);
    }

    @Override
    public void close() throws JMSException {
        session.close();
        connection.close();
    }
}

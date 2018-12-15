package dmcs.projectx.client.queue;

import javax.jms.JMSException;
import javax.jms.MessageListener;

public class MessagesQueueService extends AbstractServiceQueue {

    private String userId;

    public MessagesQueueService(String userId) throws JMSException {
        super();
        this.userId = userId;
    }

    public void listenToUserQueue(MessageListener listener) throws JMSException {
        listenToQueue(userId, listener);
    }


}

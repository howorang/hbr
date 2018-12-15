package dmcs.projectx.client.queue;

import dmcs.projectx.client.UserContextHolder;

import javax.jms.JMSException;
import javax.jms.MessageListener;

public class MessagesQueueService extends AbstractServiceQueue {

    private UserContextHolder contextHolder = UserContextHolder.getInstance();

    public MessagesQueueService() throws JMSException {
        super();
    }

    public void listenToUserQueue(String targetName, MessageListener listener) throws JMSException {
        listenToQueue(contextHolder.getCredentials().getUsername() + targetName, listener);
    }


}

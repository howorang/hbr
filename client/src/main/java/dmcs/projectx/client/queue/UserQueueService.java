package dmcs.projectx.client.queue;

import javax.jms.JMSException;
import javax.jms.MessageListener;

import static common.config.AppConfiguration.SYSTEM_EVENTS_TOPIC_NAME;

public class UserQueueService extends AbstractServiceQueue {
    public UserQueueService() throws JMSException {
        super();
    }

    public void listenForUserChanges(MessageListener listener) throws JMSException {
        listenToQueue(SYSTEM_EVENTS_TOPIC_NAME, listener);
    }
}

package dmcs.projectx.client.gui;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import dmcs.projectx.client.UserContextHolder;
import dmcs.projectx.client.api.ChatServiceProvider;
import dmcs.projectx.client.queue.MessagesQueueService;
import dmcs.projectx.client.queue.SimpleConsumer;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

public class ChatController {

    private ChatServiceProvider chatServiceProvider = ChatServiceProvider.getInstance();
    private UserContextHolder userContextHolder = UserContextHolder.getInstance();
    private MessagesQueueService messagesQueueService;

    public void setTargetNick(String targetNick) {
        this.targetNick = targetNick;
    }

    private String targetNick;

    @FXML
    private Label targetNickLabel;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private Button sendButton;

    @FXML
    private TextField messageTextField;

    public void initialize() throws IOException, TimeoutException {
        messagesQueueService = new MessagesQueueService(userContextHolder.getCredentials().getToken());
        messagesQueueService.listenForChatMessages(new SimpleConsumer() {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, Charset.defaultCharset());
                chatTextArea.append("\n");
                chatTextArea.append(targetNick + " : ");
                chatTextArea.append(message);
            }
        });
        targetNickLabel.setText(targetNick);
        sendButton.addActionListener(evnt -> {
            chatServiceProvider.get(userContextHolder.getProtocolType()).sendDirectMessage(
                    userContextHolder.getCredentials(),
                    targetNick,
                    messageTextField.getName()
            );
        });
    }

}

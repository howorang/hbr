package dmcs.projectx.client.gui;

import dmcs.projectx.client.UserContextHolder;
import dmcs.projectx.client.api.ChatServiceProvider;
import dmcs.projectx.client.queue.MessagesQueueService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class ChatController {

    private ChatServiceProvider chatServiceProvider = ChatServiceProvider.getInstance();
    private UserContextHolder userContextHolder = UserContextHolder.getInstance();

    void setTargetNick(String targetNick) {
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

    public void initialize() {
        initMessageHandling();
        targetNickLabel.setText(targetNick);
        sendButton.setOnAction(evnt -> {
            chatServiceProvider.get(userContextHolder.getProtocolType()).sendDirectMessage(
                    userContextHolder.getCredentials(),
                    targetNick,
                    messageTextField.getText()
            );
        });
    }

    private void initMessageHandling() {
        try {
            MessagesQueueService messagesQueueService = new MessagesQueueService(userContextHolder.getCredentials().getToken());
            messagesQueueService.listenToUserQueue(message -> {
                try {
                    String msg = ((TextMessage) message).getText();
                    handleMessage(msg);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(String msg) {
        chatTextArea.appendText("\n");
        chatTextArea.appendText(targetNick + " : ");
        chatTextArea.appendText(msg);
    }
}

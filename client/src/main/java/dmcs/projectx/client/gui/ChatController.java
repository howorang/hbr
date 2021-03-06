package dmcs.projectx.client.gui;

import dmcs.projectx.client.UserContextHolder;
import dmcs.projectx.client.api.ChatServiceProvider;
import dmcs.projectx.client.queue.MessagesQueueService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    @FXML
    private RadioButton burlapRadio;

    @FXML
    private RadioButton hessianRadio;

    @FXML
    private RadioButton xmlrpcRadio;

    @FXML
    private ToggleGroup protocols;


    public void initialize() {
        Platform.runLater(this::initMessageHandling);
        targetNickLabel.setText(targetNick);
        sendButton.setOnAction(evnt -> {
            appendCurrentMessage();
            chatServiceProvider.get(userContextHolder.getProtocolType()).sendDirectMessage(
                    userContextHolder.getCredentials(),
                    targetNick,
                    messageTextField.getText()
            );
        });
        MainController.bindRadioButtons(protocols, burlapRadio, userContextHolder, hessianRadio, xmlrpcRadio);
    }

    private void appendCurrentMessage() {
        chatTextArea.appendText(userContextHolder.getCredentials().getUsername() + " : ");
        chatTextArea.appendText(messageTextField.getText());
        chatTextArea.appendText("\n");
    }

    private void initMessageHandling() {
        try {
            MessagesQueueService messagesQueueService = new MessagesQueueService();
            messagesQueueService.listenToUserQueue(targetNick, message -> {
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
        chatTextArea.appendText(targetNick + " : ");
        chatTextArea.appendText(msg);
        chatTextArea.appendText("\n");
    }
}

package dmcs.projectx.client.gui;

import dmcs.projectx.client.UserContextHolder;
import dmcs.projectx.client.api.ChatServiceProvider;
import dmcs.projectx.client.api.PROTOCOL_TYPE;
import dmcs.projectx.client.queue.UserQueueService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {

    private ChatServiceProvider chatServiceProvider = ChatServiceProvider.getInstance();
    private UserContextHolder userContextHolder = UserContextHolder.getInstance();

    @FXML
    private Button chatButton;

    @FXML
    private ListView<String> userList;

    private ObservableList<String> users = FXCollections.observableArrayList();

    @FXML
    private RadioButton burlapRadio;

    @FXML
    private RadioButton hessianRadio;

    @FXML
    private RadioButton xmlrpcRadio;

    @FXML
    private ToggleGroup protocols;

    public void initialize() throws Exception {
        bindRadioButtons(protocols, burlapRadio, userContextHolder, hessianRadio, xmlrpcRadio);
        List<String> activeUsers = chatServiceProvider.get().getUsers();
        activeUsers = activeUsers
                .stream()
                .filter(us -> !us.equals(userContextHolder.getCredentials().getUsername()))
                .collect(Collectors.toList());
        users.addAll(activeUsers);
        userList.setItems(users);
        chatButton.setOnAction(event -> openChatWindow());
        initUserQueueHandling();
    }

    private void initUserQueueHandling() throws JMSException {
        UserQueueService userChangesQueueService = new UserQueueService();
        userChangesQueueService.listenForUserChanges(message -> {
            try {
                String msg = ((TextMessage) message).getText();
                handleMsg(msg);

            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleMsg(String msg) {
        if (msg.startsWith("LEFT:")) {
            Platform.runLater(() -> {
                String user = msg.substring(5);
                if (!user.equals(userContextHolder.getCredentials().getUsername())) {
                    users.remove(user);
                }
            });
        } else if (msg.startsWith("ADDED:")) {
            Platform.runLater(() -> {
                String user = msg.substring(6);
                if (!user.equals(userContextHolder.getCredentials().getUsername())) {
                    users.add(user);
                }
            });
        }
    }

    private void openChatWindow() {
        try {
            String targetUser = userList.selectionModelProperty().getValue().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/chat.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(targetUser);
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            ChatController chatController = fxmlLoader.getController();
            chatController.setTargetNick(targetUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void bindRadioButtons(ToggleGroup protocols, RadioButton burlapRadio, UserContextHolder userContextHolder, RadioButton hessianRadio, RadioButton xmlrpcRadio) {
        protocols.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (burlapRadio.isSelected()) {
                userContextHolder.setProtocolType(PROTOCOL_TYPE.BURLAP);
            }
            if (hessianRadio.isSelected()) {
                userContextHolder.setProtocolType(PROTOCOL_TYPE.HESSIAN);
            }
            if (xmlrpcRadio.isSelected()) {
                userContextHolder.setProtocolType(PROTOCOL_TYPE.XML_RPC);
            }
        });
    }
}

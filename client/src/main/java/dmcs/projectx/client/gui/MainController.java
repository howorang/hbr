package dmcs.projectx.client.gui;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import dmcs.projectx.client.UserContextHolder;
import dmcs.projectx.client.api.ChatServiceProvider;
import dmcs.projectx.client.api.PROTOCOL_TYPE;
import dmcs.projectx.client.queue.SimpleConsumer;
import dmcs.projectx.client.queue.UserQueueService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

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

    private UserQueueService userChangesQueueService;

    public void initialize() throws Exception {
        bindRadioButtons(protocols, burlapRadio, userContextHolder, hessianRadio, xmlrpcRadio);
        List<String> activeUsers = chatServiceProvider.get().getUsers();
        users.addAll(activeUsers);
        userList.setItems(users);

        userChangesQueueService = new UserQueueService();
        userChangesQueueService.listenForUserChanges(new SimpleConsumer() {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, Charset.defaultCharset());
                if (message.startsWith("LEFT:")) {
                    String user = message.substring(5);
                    users.remove(user);
                } else if (message.startsWith("ADDED:")) {
                    String user = message.substring(6);
                    users.add(user);
                }
            }
        });
    }

    static void bindRadioButtons(ToggleGroup protocols, RadioButton burlapRadio, UserContextHolder userContextHolder, RadioButton hessianRadio, RadioButton xmlrpcRadio) {
        protocols.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (burlapRadio.isSelected()) {
                    userContextHolder.setProtocolType(PROTOCOL_TYPE.BURLAP);
                }
                if (hessianRadio.isSelected()) {
                    userContextHolder.setProtocolType(PROTOCOL_TYPE.HESSIAN);
                }
                if (xmlrpcRadio.isSelected()) {
                    userContextHolder.setProtocolType(PROTOCOL_TYPE.XML_RPC);
                }
            }
        });
    }

}

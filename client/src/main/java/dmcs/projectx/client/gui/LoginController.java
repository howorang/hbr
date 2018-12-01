package dmcs.projectx.client.gui;

import common.api.Credentials;
import dmcs.projectx.client.UserContextHolder;
import dmcs.projectx.client.api.ChatServiceProvider;
import dmcs.projectx.client.api.PROTOCOL_TYPE;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    ChatServiceProvider chatServiceProvider = ChatServiceProvider.getInstance();
    UserContextHolder userContextHolder = UserContextHolder.getInstance();

    @FXML
    private Button loginButton;

    @FXML
    private TextField nickField;

    @FXML
    private RadioButton burlapRadio;

    @FXML
    private RadioButton hessianRadio;

    @FXML
    private RadioButton xmlrpcRadio;

    @FXML
    private ToggleGroup protocols;


    public void initialize() {
        loginButton.setOnAction(event -> {
            try {
                String authToken = chatServiceProvider.get(userContextHolder.getProtocolType()).logIn(nickField.getText());
                userContextHolder.setCredentials(new Credentials(nickField.getText(), authToken));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

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

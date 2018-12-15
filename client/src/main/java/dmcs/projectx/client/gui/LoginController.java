package dmcs.projectx.client.gui;

import common.api.Credentials;
import dmcs.projectx.client.UserContextHolder;
import dmcs.projectx.client.api.ChatServiceProvider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private ChatServiceProvider chatServiceProvider = ChatServiceProvider.getInstance();
    private UserContextHolder userContextHolder = UserContextHolder.getInstance();

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
                openMainWindow(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        MainController.bindRadioButtons(protocols, burlapRadio, userContextHolder, hessianRadio, xmlrpcRadio);

    }

    private void openMainWindow(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/main.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(root, 450, 450));
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}

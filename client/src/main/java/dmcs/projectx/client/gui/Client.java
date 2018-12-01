package dmcs.projectx.client.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Client extends Application {
    private Parent rootNode;
    private FXMLLoader fxmlLoader;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        fxmlLoader = new FXMLLoader();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmlLoader.setLocation(getClass().getResource("/fxml/login.fxml"));
        rootNode = fxmlLoader.load();
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(rootNode, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

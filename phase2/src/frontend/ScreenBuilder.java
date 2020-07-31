package frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenBuilder {
    public void createScreen(Stage primaryStage, String fileLoc, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fileLoc));
        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }
}

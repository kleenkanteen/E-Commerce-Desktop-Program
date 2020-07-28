package D_presenters;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UserMenuGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * User Login Menu after being redirected from Main Menu
     * @param primaryStage takes a Stage that is given from Main Menu.
     * @throws IOException is thrown when FXML file cannot be loaded.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("User Login");
        AnchorPane newRoot = FXMLLoader.load(getClass().getResource("UserMenuGUI.fxml"));
        Scene scene = new Scene(newRoot);
        stage.setScene(scene);
        stage.show();
    }
}

package frontend.MainMenuGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartScreen extends Application {

    /**
     *
     * @param primaryStage Initial stage of the JavaFX program
     * @throws Exception if stage or stage properties could not be created
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        primaryStage.setTitle("Trade System 2.0");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    /**
     * Used in case the application can not be launched through deployment artifacts
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

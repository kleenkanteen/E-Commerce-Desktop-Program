package D_presenters;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class tst extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Main menu of trading application.
     * @param primaryStage takes in Stage when the program starts up.
     * @throws IOException if it can't load the fxml file.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("guiSample.fxml"));
        AnchorPane root = loader.load();

        // used to be able to have access to the next Stage.
        guiSample controller = (guiSample) loader.getController();
        controller.setPrevStage(primaryStage);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}

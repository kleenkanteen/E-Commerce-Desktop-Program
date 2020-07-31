package frontend;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserSignUpController extends Application implements Initializable {

    // code for method changeScreenButtonPushed is similar to: https://www.youtube.com/watch?v=XCgcQTQCfJQ
    public void changeScreenButtonPushed(ActionEvent actionEvent) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

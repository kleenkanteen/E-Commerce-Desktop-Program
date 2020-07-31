package frontend;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends Application implements Initializable {

    final String userLoginFXMLFile = "UserLogin.fxml";
    final String userSignUpFXMLFile = "UserSignUp.fxml";
    final String adminLoginFXMLFile = "AdminLogin.fxml";
    final String demoLoginFXMLFile = "DemoLogin.fxml";

    // code for method goToOtherScene is similar to: https://www.youtube.com/watch?v=XCgcQTQCfJQ
    public void goToOtherScene(ActionEvent actionEvent, String otherScene) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(otherScene));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void userLoginButtonPressed (ActionEvent actionEvent) throws IOException {
        goToOtherScene(actionEvent, userLoginFXMLFile);
    }

    public void userSignUpButtonPressed(ActionEvent actionEvent) throws IOException {
        goToOtherScene(actionEvent, userSignUpFXMLFile);
    }

    public void adminLoginButtonPressed(ActionEvent actionEvent) throws IOException {
        goToOtherScene(actionEvent, adminLoginFXMLFile);
    }

    public void programDemoButtonPressed(ActionEvent actionEvent) throws IOException {
        goToOtherScene(actionEvent, demoLoginFXMLFile);
    }

    // code for method closeButtonIsPushed is similar to: https://www.youtube.com/watch?v=i4Fk10U7Sks
    public void closeButtonIsPushed(ActionEvent actionEvent) {
        Stage s = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        s.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    @Override
    public void start(Stage primaryStage) throws Exception { }

}

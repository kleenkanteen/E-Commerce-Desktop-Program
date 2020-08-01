package frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminAccountController {
    @FXML private Button ChangePassWordButton;
    @FXML private Button AdminCreationButton;
    @FXML private Button ExitButton;
    @FXML private Button BackFromCreationButton;
    @FXML private Button BackFromChangePasswordButton;

    public void switchScene(ActionEvent actionEvent, String fileName) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fileName));
        Scene newScene= new Scene(root);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }
    public void ChangePassWordButtonPushed(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "AdminChangePassword.fxml");


    }
    public void AdminCreationButtonPushed(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "AdminCreation.fxml");


    }
    public void ExitButtonPushed(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "AdminMenu.fxml");


    }
    public void BackFromCreationButtonPushed(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "AdminAccount.fxml");


    }
    public void BackFromChangePasswordButtonPushed(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "AdminAccount.fxml");


    }


}

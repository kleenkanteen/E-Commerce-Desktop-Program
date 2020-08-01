package frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminAccountController implements Initializable {
    @FXML private Button changePassWordButton;
    @FXML private Button adminCreationButton;
    @FXML private Button exitButton;
    @FXML private Button backFromCreationButton;
    @FXML private Button backFromChangePasswordButton;
    @FXML private Label resultOfPasswordChangeLabel;
    @FXML private Label resultOfCreationLabel;
    @FXML private Button changePasswordButton;
    @FXML private Button addNewPasswordButton;
    @FXML private Button addNewAdminButton;
    @FXML private TextField newPasswordTextField;
    @FXML private TextField confirmNewPasswordField;
    @FXML private TextField newAdminUserNameTextField;
    @FXML private TextField newAdminPasswordTextField;

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

    public void addNewPasswordButtonPushed(ActionEvent actionEvent){
        String newPassword = newPasswordTextField.getAccessibleText();
        String confirmPassword = confirmNewPasswordField.getAccessibleText();
        if (newPassword.equals(confirmPassword)){
            this.resultOfPasswordChangeLabel.setText("Your new password is saved");
        }
        else {
            this.resultOfPasswordChangeLabel.setText("Two passwords don't match, failed to change your password");
        }

    }

    public void addNewAdminButtonPushed(ActionEvent actionEvent){

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultOfCreationLabel.setText("");
         resultOfPasswordChangeLabel.setText("");


    }
}

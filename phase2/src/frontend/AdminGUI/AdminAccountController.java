package frontend.AdminGUI;

import entities.Admin;
import exceptions.InvalidUsernameException;
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
import presenters.AdminAccountPresenter;
import use_cases.AdminManager;
import use_cases.UserManager;

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
    @FXML private Button addNewPasswordButton;
    @FXML private Button addNewAdminButton;
    @FXML private TextField newPasswordTextField;
    @FXML private TextField confirmNewPasswordField;
    @FXML private TextField newAdminUserNameTextField;
    @FXML private TextField newAdminPasswordTextField;

    private Admin admin;

    private AdminAccountPresenter adminAccountPresenter;



    private UserManager userManager;

    private AdminManager adminManager;


    /**
     * Class constructor.
     * Create a new AdminAccountSystem that controls and allows the admin to reply to system messages
     * @param admin the admin of the currently logged in.
     * @param adminManager the AdminManager will be used to change account information

     * @param userManager the UserManager used to check account information
     */


    AdminAccountController(Admin admin, AdminManager adminManager,
                       UserManager userManager){
        this.admin = admin;
        adminAccountPresenter = new AdminAccountPresenter(admin);
        this.adminManager = adminManager;
        this.userManager = userManager;



    }

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

        String password1 = newPasswordTextField.getText();

        String password2 = confirmNewPasswordField.getText();
        if (adminManager.addNewPassWord(password1,password2, admin)){
            resultOfPasswordChangeLabel.setText(adminAccountPresenter.newPasswordCreated());
        }
        else {
            resultOfPasswordChangeLabel.setText(adminAccountPresenter.newPasswordNotSaved());
        }


    }

    public void addNewAdminButtonPushed(ActionEvent actionEvent){

        String newUsername = newAdminUserNameTextField.getText();

        String newPassword = newAdminPasswordTextField.getText();
        if(userManager.isValidUser(newUsername)){
            resultOfCreationLabel.setText(adminAccountPresenter.AdminCreationFailed());
        }
        else {
            try {
                adminManager.addAdmin(newUsername, newPassword);
                resultOfCreationLabel.setText(adminAccountPresenter.newAdminCreated());
            }
            catch (InvalidUsernameException e) {
                resultOfCreationLabel.setText(adminAccountPresenter.AdminCreationFailed());
            }
        }


    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }
}

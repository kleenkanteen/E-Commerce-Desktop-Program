package frontend.AdminGUI;

import entities.Admin;
import exceptions.InvalidUsernameException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presenters.AdminAccountPresenter;
import use_cases.AdminManager;
import use_cases.UserManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminNewAdminController implements Initializable {
    @FXML private Label resultOfCreationLabel;
    @FXML private Button addNewAdminButton;
    @FXML private Button exitButton;
    @FXML private TextField newAdminUserNameTextField;
    @FXML private TextField newAdminPasswordTextField;

    private Admin admin;

    private AdminGUIPresenter adminGUIPresenter;

    private UserManager userManager;

    private AdminManager adminManager;

    AdminNewAdminController(Admin admin, AdminManager adminManager, UserManager userManager){
        this.admin = admin;
        adminGUIPresenter = new AdminGUIPresenter();
        this.adminManager = adminManager;
        this.userManager = userManager;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitButton.setText(adminGUIPresenter.exitButton());
        exitButton.setOnAction(this::close);
        addNewAdminButton.setText(adminGUIPresenter.newAdminButton());
        addNewAdminButton.setOnAction(this::addNewAdminButtonPushed);

    }
    private void close(ActionEvent actionEvent){
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
    private void addNewAdminButtonPushed(ActionEvent actionEvent){

        String newUsername = newAdminUserNameTextField.getText();

        String newPassword = newAdminPasswordTextField.getText();
        if (newUsername.equals("")){
            resultOfCreationLabel.setText(adminGUIPresenter.userNameCannotBeEmpty());

        }
        else if(newPassword.equals("")){
            resultOfCreationLabel.setText(adminGUIPresenter.passwordCannotBeEmpty());
        }
        else if(userManager.isValidUser(newUsername)){
            resultOfCreationLabel.setText(adminGUIPresenter.AdminCreationFailed());
        }
        else {
            try {
                adminManager.addAdmin(newUsername, newPassword);
                resultOfCreationLabel.setText(adminGUIPresenter.newAdminCreated());
            }
            catch (InvalidUsernameException e) {
                resultOfCreationLabel.setText(adminGUIPresenter.AdminCreationFailed());
            }
        }


    }
}

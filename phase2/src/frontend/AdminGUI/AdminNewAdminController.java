package frontend.AdminGUI;

import entities.Admin;
import exceptions.InvalidUsernameException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import presenters.AdminAccountPresenter;
import use_cases.AdminManager;
import use_cases.UserManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminNewAdminController implements Initializable {
    @FXML private Label resultOfCreationLabel;
    @FXML private Button addNewAdminButton;
    @FXML private TextField newAdminUserNameTextField;
    @FXML private TextField newAdminPasswordTextField;

    private Admin admin;

    private AdminAccountPresenter adminAccountPresenter;

    private UserManager userManager;

    private AdminManager adminManager;

    AdminNewAdminController(Admin admin, AdminManager adminManager, UserManager userManager){
        this.admin = admin;
        adminAccountPresenter = new AdminAccountPresenter(admin);
        this.adminManager = adminManager;
        this.userManager = userManager;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addNewAdminButton.setText("Create a new Admin!");
        addNewAdminButton.setOnAction(this::addNewAdminButtonPushed);

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
}

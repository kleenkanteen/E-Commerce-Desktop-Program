package frontend.AdminGUI;

import entities.Admin;
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

public class AdminNewPasswordController implements Initializable {
    @FXML
    private Label resultOfPasswordChangeLabel;
    @FXML private Button addNewPasswordButton;
    @FXML private TextField newPasswordTextField;
    @FXML private TextField confirmNewPasswordField;
    @FXML private Button exitButton;
    private Admin admin;

    private AdminAccountPresenter adminAccountPresenter;

    private UserManager userManager;

    private AdminManager adminManager;
    AdminNewPasswordController(Admin admin, AdminManager adminManager,
                           UserManager userManager){
        this.admin = admin;
        adminAccountPresenter = new AdminAccountPresenter(admin);
        this.adminManager = adminManager;
        this.userManager = userManager;



    }
    public void close(ActionEvent actionEvent){
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitButton.setText("Back to Admin account menu");
        exitButton.setOnAction(this::close);
        addNewPasswordButton.setText("Save changes");
        addNewPasswordButton.setOnAction(this::addNewPasswordButtonPushed);


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
}}

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

    private AdminGUIPresenter adminGUIPresenter;

    private UserManager userManager;

    private AdminManager adminManager;
    AdminNewPasswordController(Admin admin, AdminManager adminManager,
                           UserManager userManager){
        this.admin = admin;
        adminGUIPresenter = new AdminGUIPresenter();
        this.adminManager = adminManager;
        this.userManager = userManager;



    }
    public void close(ActionEvent actionEvent){
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitButton.setText(adminGUIPresenter.exitButton());
        exitButton.setOnAction(this::close);
        addNewPasswordButton.setText(adminGUIPresenter.saveChangeButtonText());
        addNewPasswordButton.setOnAction(this::addNewPasswordButtonPushed);


    }
    public void addNewPasswordButtonPushed(ActionEvent actionEvent){

        String password1 = newPasswordTextField.getText();

        String password2 = confirmNewPasswordField.getText();

        if(password1.equals("")){
            resultOfPasswordChangeLabel.setText(adminGUIPresenter.passwordCannotBeEmpty());
        }
        else if (password2.equals("")){
            resultOfPasswordChangeLabel.setText(adminGUIPresenter.newPasswordNotSaved());
        }
        else if (adminManager.addNewPassWord(password1,password2, admin)){
            resultOfPasswordChangeLabel.setText(adminGUIPresenter.newPasswordCreated(admin));
        }
        else {
            resultOfPasswordChangeLabel.setText(adminGUIPresenter.newPasswordNotSaved());
        }
}}

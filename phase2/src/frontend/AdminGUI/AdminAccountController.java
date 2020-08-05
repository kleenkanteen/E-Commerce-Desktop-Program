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
import javafx.stage.Modality;
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

    private String NewPasswordFXML = "AdminNewPasswordController";
    private String NewAdminFXML = "AdminNewAdminController";




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

    public void close(ActionEvent actionEvent){
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }


    public void ChangePassWordButtonPushed(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Go set a new password!");
        window.setMinWidth(800);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(NewPasswordFXML));

        loader.setController(new AdminNewPasswordController(admin, adminManager, userManager));

        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(scene);

        window.showAndWait();


    }
    public void AdminCreationButtonPushed(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Go create a new pal!");
        window.setMinWidth(800);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(NewAdminFXML));

        loader.setController(new AdminNewAdminController(admin, adminManager, userManager));

        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(scene);

        window.showAndWait();


    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changePassWordButton.setText("Change your password");
        adminCreationButton.setText("Add a new admin account");
        exitButton.setText("Press to go back");
        exitButton.setOnAction(this::close);
        changePassWordButton.setOnAction(e -> {
            try {
                ChangePassWordButtonPushed(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        adminCreationButton.setText("Add a new admin account");
        adminCreationButton.setOnAction(e -> {
            try {
                AdminCreationButtonPushed(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });



    }
}

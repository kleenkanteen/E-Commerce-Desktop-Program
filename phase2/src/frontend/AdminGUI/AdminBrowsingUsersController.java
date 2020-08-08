package frontend.AdminGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import presenters.AdminBrowsingUsersPresenter;
import use_cases.UserManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminBrowsingUsersController implements Initializable {

    @FXML private TextField usernameText;
    @FXML private TextField optionText;
    @FXML private Button searchButton;
    @FXML private Button lendingButton;
    @FXML private Button freezeButton;
    @FXML private Button exitButton;
    @FXML private Button banButton;
    @FXML private Button weeklyButton;
    @FXML private Button incompleteButton;
    @FXML private Button optionButton;
    @FXML private Label mainLabel;
    @FXML private Label userLabel;

    private UserManager users;
    private AdminBrowsingUsersPresenter browse;
    private String user;

    /**
    Construct an instance, takes in an usermanager object, creates a AdminBrowsingUsersPresenter.
    @param system usermanager instance
     */
    public AdminBrowsingUsersController(UserManager system) {
        this.browse = new AdminBrowsingUsersPresenter();
        this.users = system;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lendingButton.setVisible(false);
        freezeButton.setVisible(false);
        weeklyButton.setVisible(false);
        incompleteButton.setVisible(false);
        optionButton.setVisible(false);
        optionText.setVisible(false);
        banButton.setVisible(false);
        userLabel.setVisible(false);
        mainLabel.setVisible(false);

    }

    private void userSearch(ActionEvent actionEvent){
        String input = usernameText.getText();
        if (users.isValidUser(input)){
            user = input;
            lendingButton.setVisible(true);
            freezeButton.setVisible(true);
            weeklyButton.setVisible(true);
            incompleteButton.setVisible(true);
            optionButton.setVisible(true);
            optionText.setVisible(true);
            banButton.setVisible(true);
            userLabel.setText(users.getUserInfo(user));
            userLabel.setVisible(true);
            mainLabel.setText("Choose your option below");
            mainLabel.setVisible(true);
        }
        else {
            mainLabel.setText("Invalid username, try again");
            mainLabel.setVisible(true);
        }
    }
}

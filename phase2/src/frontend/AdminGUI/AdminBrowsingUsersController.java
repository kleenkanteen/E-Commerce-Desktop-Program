package frontend.AdminGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    private String user = "";
    private boolean lendingLimit = false;
    private boolean weeklyLimit = false;
    private boolean incompleteLimit = false;


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

        exitButton.setOnAction(this::exit);
        lendingButton.setOnAction(this::lending);
        freezeButton.setOnAction(this::freeze);
        searchButton.setOnAction(this::search);
        banButton.setOnAction(this::ban);
        weeklyButton.setOnAction(this::weeklyLimit);
        incompleteButton.setOnAction(this::incompleteLimit);
        optionButton.setOnAction(this::optionInput);
    }

    @FXML
    private void search(ActionEvent actionEvent){
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


    @FXML
    private void incompleteLimit(ActionEvent actionEvent) {
        mainLabel.setText("Enter the new limit in bottom right");
        incompleteLimit = true;
    }

    @FXML
    private void weeklyLimit(ActionEvent actionEvent) {
        mainLabel.setText("Enter the new limit in bottom right");
        weeklyLimit = true;
    }

    @FXML
    private void ban(ActionEvent actionEvent) {
        if(users.getUserIsBanned(user)){
  // TODO          users.unbanUserAccount(user);
        }
        else{
            users.banUserAccount(user);
        }
        mainLabel.setText("User banning state has been changed");
    }

    @FXML
    private void freeze(ActionEvent actionEvent) {
        if(users.getUserFrozenStatus(user)){
            users.unFreezeUserAccount(user);
        }
        else{
            users.freezeUserAccount(user);
        }
        mainLabel.setText("User freezing state has been changed");
    }

    @FXML
    private void lending(ActionEvent actionEvent) {
        mainLabel.setText("Enter the new threshold in bottom right");
        lendingLimit = true;
    }

    @FXML
    private void optionInput(ActionEvent actionEvent) {
        if (weeklyLimit){
            String limit = optionText.getText();
            users.setWeeklyTradesForOneUser(user, Integer.parseInt(limit));
            mainLabel.setText("Weekly limit successfully changed");
            incompleteLimit = false;
        }
        else if (incompleteLimit) {
            String limit = optionText.getText();
            users.setLimitOfIncompleteTradesForOneUser(user, Integer.parseInt(limit));
            mainLabel.setText("Incomplete trades limit successfully changed");
            incompleteLimit = false;
        }
        else if (lendingLimit){
            String limit = optionText.getText();
            users.setNewThresholdForOneUser(user, Integer.parseInt(limit));
            mainLabel.setText("Threshold successfully changed");
            lendingLimit = false;
        }
    }

    @FXML
    private void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}

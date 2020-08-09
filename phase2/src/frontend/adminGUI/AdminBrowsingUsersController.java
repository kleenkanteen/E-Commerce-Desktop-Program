package frontend.adminGUI;

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

    @FXML
    private TextField usernameText;
    @FXML
    private TextField optionText;
    @FXML
    private Button searchButton;
    @FXML
    private Button lendingButton;
    @FXML
    private Button freezeButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button banButton;
    @FXML
    private Button weeklyButton;
    @FXML
    private Button incompleteButton;
    @FXML
    private Button optionButton;
    @FXML
    private Label mainLabel;
    @FXML
    private Label userLabel;

    private UserManager users;
    private AdminBrowsingUsersPresenter browse;
    private String user = "";
    private boolean lendingLimit = false;
    private boolean weeklyLimit = false;
    private boolean incompleteLimit = false;


    /**
     * Construct an instance, takes in an usermanager object, creates a AdminBrowsingUsersPresenter.
     *
     * @param system usermanager instance
     */
    public AdminBrowsingUsersController(UserManager system) {
        this.browse = new AdminBrowsingUsersPresenter();
        this.users = system;
    }

    @Override
    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from
     * Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location
     *                 is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
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
    private void search(ActionEvent actionEvent) {
        String input = usernameText.getText();
        if (users.isValidUser(input)) {
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
            mainLabel.setText("     Choose your option below");
            mainLabel.setVisible(true);
        } else {
            mainLabel.setText("     Invalid username, try again");
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
        if (users.getUserIsBanned(user)) {
            users.unFreezeUserAccount(user);
        } else {
            users.banUserAccount(user);
        }
        mainLabel.setText("User banning state has been changed");
        userLabel.setText(users.getUserInfo(user));
    }

    @FXML
    private void freeze(ActionEvent actionEvent) {
        if (users.getUserFrozenStatus(user)) {
            users.unFreezeUserAccount(user);
        } else {
            users.freezeUserAccount(user);
        }
        mainLabel.setText("User freezing state has been changed");
        userLabel.setText(users.getUserInfo(user));
    }

    @FXML
    private void lending(ActionEvent actionEvent) {
        mainLabel.setText("Enter the new threshold in bottom right");
        lendingLimit = true;
    }

    @FXML
    private void optionInput(ActionEvent actionEvent) {

        String limit = optionText.getText();
        if (limit.matches("\\d+")) {
            if (weeklyLimit) {
                users.setWeeklyTradesForOneUser(user, Integer.parseInt(limit));
                mainLabel.setText("Weekly limit successfully changed");
                incompleteLimit = false;
                userLabel.setText(users.getUserInfo(user));
            } else if (incompleteLimit) {
                users.setLimitOfIncompleteTradesForOneUser(user, Integer.parseInt(limit));
                mainLabel.setText("Incomplete trades limit successfully changed");
                incompleteLimit = false;
                userLabel.setText(users.getUserInfo(user));
            } else if (lendingLimit) {
                users.setNewThresholdForOneUser(user, Integer.parseInt(limit));
                mainLabel.setText("Threshold successfully changed");
                lendingLimit = false;
                userLabel.setText(users.getUserInfo(user));
            }
        }
        else { mainLabel.setText("     Enter only numbers");}
    }
        @FXML
        private void exit (ActionEvent actionEvent){
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.close();
        }
    }
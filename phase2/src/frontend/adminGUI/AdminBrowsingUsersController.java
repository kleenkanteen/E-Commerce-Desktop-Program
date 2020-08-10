package frontend.adminGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
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
    @FXML private Label allLabel;

    private UserManager users;
    private AdminBrowsingUsersPresenter browse;
    private String user = "";
    private boolean lendingLimit = false;
    private boolean weeklyLimit = false;
    private boolean incompleteLimit = false;
    private boolean allLimit;

    /**
     * Construct an instance, takes in an usermanager object, creates a AdminBrowsingUsersPresenter.
     *
     * @param system usermanager instance
     */
    public AdminBrowsingUsersController(UserManager system) {
        this.browse = new AdminBrowsingUsersPresenter();
        this.users = system;
    }


    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from
     * Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location
     *                 is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allLimit = true;
        allLabel.setVisible(true);
        freezeButton.setVisible(false);
        banButton.setVisible(false);
        userLabel.setVisible(false);
        mainLabel.setText("");
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
            allLimit = false;
            user = input;
            allLabel.setVisible(false);
            lendingButton.setVisible(true);
            freezeButton.setVisible(true);
            weeklyButton.setVisible(true);
            incompleteButton.setVisible(true);
            banButton.setVisible(true);
            userLabel.setText(users.getUserInfo(user));
            userLabel.setVisible(true);
            mainLabel.setText(browse.optionPrompt());
        } else {
            mainLabel.setText(browse.invalidName());
        }
        mainLabel.setAlignment(Pos.CENTER);

    }


    @FXML
    private void incompleteLimit(ActionEvent actionEvent) {
        mainLabel.setText(browse.enterValuePrompt());
        incompleteLimit = true;
        mainLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    private void weeklyLimit(ActionEvent actionEvent) {
        mainLabel.setText(browse.enterValuePrompt());
        weeklyLimit = true;
        mainLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    private void ban(ActionEvent actionEvent) {
        if (users.getUserIsBanned(user)) {
            users.unFreezeUserAccount(user);
        } else {
            users.banUserAccount(user);
        }
        mainLabel.setText(browse.banStateChangeSuccess());
        userLabel.setText(users.getUserInfo(user));
        mainLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    private void freeze(ActionEvent actionEvent) {
        if (users.getUserFrozenStatus(user)) {
            users.unFreezeUserAccount(user);
        } else {
            users.freezeUserAccount(user);
        }
        mainLabel.setText(browse.freezeStateChangeSuccess());
        userLabel.setText(users.getUserInfo(user));
        mainLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    private void lending(ActionEvent actionEvent) {
        mainLabel.setText(browse.enterValuePrompt());
        lendingLimit = true;
        mainLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    private void optionInput(ActionEvent actionEvent) {

        String limit = optionText.getText();
        if (limit.matches("\\d+")) {
            if (allLimit && weeklyLimit) {
                users.setWeeklyTrades(Integer.parseInt(limit));
                mainLabel.setText(browse.weeklyLimitChangeSuccess());
            }
            else if (allLimit && incompleteLimit) {
                users.setLimitOfIncompleteTrades(Integer.parseInt(limit));
                mainLabel.setText(browse.incompleteLimitChangeSuccess());
            }
            else if (allLimit && lendingLimit) {
                users.setNewThreshold(Integer.parseInt(limit));
                mainLabel.setText(browse.incompleteLimitChangeSuccess());
                mainLabel.setText(browse.thresholdChangeSuccess());
            }
            else if (weeklyLimit) {
                users.setWeeklyTradesForOneUser(user, Integer.parseInt(limit));
                mainLabel.setText(browse.weeklyLimitChangeSuccess());
                incompleteLimit = false;
                userLabel.setText(users.getUserInfo(user));
            } else if (incompleteLimit) {
                users.setLimitOfIncompleteTradesForOneUser(user, Integer.parseInt(limit));
                mainLabel.setText(browse.incompleteLimitChangeSuccess());
                incompleteLimit = false;
                userLabel.setText(users.getUserInfo(user));
            } else if (lendingLimit) {
                users.setNewThresholdForOneUser(user, Integer.parseInt(limit));
                mainLabel.setText(browse.thresholdChangeSuccess());
                lendingLimit = false;
                userLabel.setText(users.getUserInfo(user));
            }
        }
        else { mainLabel.setText(browse.wrongFormat());}
        mainLabel.setAlignment(Pos.CENTER);
    }
        @FXML
        private void exit (ActionEvent actionEvent){
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.close();
        }
    }
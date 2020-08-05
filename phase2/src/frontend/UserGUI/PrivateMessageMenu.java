package frontend.UserGUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presenters.UserPresenter;
import use_cases.MessageBuilder;
import use_cases.UserManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrivateMessageMenu implements Initializable {

    @FXML private TextField usernameInput;
    @FXML private TextArea messageInput;
    @FXML private Label usernamePrompt;
    @FXML private Label messagePrompt;
    @FXML private Label usernameError;
    @FXML private Button send;
    @FXML private Button cancel;
    @FXML private Label confirmation;

    private UserPresenter userPresenter;
    private MessageBuilder messageBuilder;
    private UserManager userManager;
    private String currUser;

    public PrivateMessageMenu(UserManager userManager, String currUser) {
        this.userManager = userManager;
        this.userPresenter = new UserPresenter();
        this.messageBuilder = new MessageBuilder();
        this.currUser = currUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set text
        this.usernamePrompt.setText(this.userPresenter.userMessagePrompt());
        this.messagePrompt.setText(this.userPresenter.userMessagePromptSecundus());
        this.send.setText(this.userPresenter.userLoanPromptConfirm());
        this.cancel.setText(this.userPresenter.userLoanPromptCancel());

        // set button functionality
        this.send.setOnAction(e -> sendMessage());
        this.cancel.setOnAction(this::returnToMenu);
    }

    @FXML
    public void sendMessage() {
        String user = this.usernameInput.getText();
        // if the username is not in the system
        if(!this.userManager.isValidUser(user)) {
            this.usernameError.setText(this.userPresenter.invalidUsername());
        }
        // otherwise
        else {
            this.userManager.addUserMessage(user,
                    this.messageBuilder.getPrivateMessage(this.messageInput.getText(), this.currUser));
            this.confirmation.setText(this.userPresenter.userPrivateMessageConfirmation());
        }
    }

    @FXML
    public void returnToMenu(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
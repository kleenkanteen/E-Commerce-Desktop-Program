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

public class PrivateMessageMenu extends Application implements Initializable {

    @FXML private TextField usernameInput;
    @FXML private TextArea messageInput;
    @FXML private Label usernamePrompt;
    @FXML private Label messagePrompt;
    @FXML private Label usernameError;
    @FXML private Button send;
    @FXML private Button cancel;

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.usernamePrompt.setText(this.userPresenter.userMessagePrompt());
        this.messagePrompt.setText(this.userPresenter.userMessagePromptSecundus());
    }

    /**
     * Switches the scene being viewed
     * @param actionEvent the ActionEvent
     * @param filename the filename of the .fxml file to be loaded
     * @throws IOException for a funky input
     */
    public void switchScene(ActionEvent actionEvent, String filename) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(filename));
        Scene newScene= new Scene(root);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void sendMessage() {
        String user = this.usernameInput.getText();
        if(!this.userManager.isValidUser(user)) {
            this.usernameError.setText(this.userPresenter.invalidUsername());
        }
        else {
            String message = this.messageInput.getText();
            this.userManager.addUserMessage(user, this.messageBuilder.getPrivateMessage(message, this.currUser));
            // return to main menu somehow?
        }
    }

    @FXML
    public void returnToMenu() {
        // cancel somehow?
    }
}

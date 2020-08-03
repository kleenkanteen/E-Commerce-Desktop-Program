package frontend.UserGUI;

import entities.Item;
import entities.Message;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presenters.UserPresenter;
import use_cases.AdminManager;
import use_cases.MessageBuilder;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewItemMenu extends Application implements Initializable {

    private UserPresenter userPresenter;
    private AdminManager adminManager;
    private MessageBuilder messageBuilder;
    private String currUser;

    @FXML private Button confirm;
    @FXML private Button cancel;
    @FXML private Label namePrompt;
    @FXML private Label descriptionPrompt;
    @FXML private TextField nameInput;
    @FXML private TextArea descriptionInput;
    @FXML private Label errorMessage;

    public NewItemMenu(String currUser, AdminManager adminManager) {
        this.currUser = currUser;
        this.adminManager = adminManager;
        this.messageBuilder = new MessageBuilder();
        this.userPresenter = new UserPresenter();
    }

    @Override
    public void start(Stage primaryStage) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.namePrompt.setText(this.userPresenter.createNewItemPrompt(0));
        this.descriptionPrompt.setText(this.userPresenter.createNewItemPrompt(1));
    }

    @FXML
    public void sendNewItemRequest() {
        String itemName = this.nameInput.getText();
        String itemDescription = this.descriptionInput.getText();
        if(itemName.length() == 0 || itemDescription.length() == 0) {
            this.errorMessage.setText("Please fill out both the name and description inputs.");
        }
        else {
            List<Message> adminMessages = this.adminManager.getAdminMessages();
            adminMessages.add(this.messageBuilder.getNewItemRequest("User " + this.currUser +
                            " has created a new item that requires approval.",
                    new Item(itemName, this.currUser, itemDescription)));
            this.adminManager.setAdminMessages(adminMessages);
            this.userPresenter.newItemMessageSentToAdmin();
            // return to the user menu
        }
    }

    @FXML
    public void returnToMainMenu() {

    }
}

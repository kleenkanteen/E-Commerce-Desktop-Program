package frontend.UserGUI;

import entities.Item;
import entities.Message;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

public class NewItemMenu implements Initializable {

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
    @FXML private Label confirmation;

    public NewItemMenu(String currUser, AdminManager adminManager) {
        this.currUser = currUser;
        this.adminManager = adminManager;
        this.messageBuilder = new MessageBuilder();
        this.userPresenter = new UserPresenter();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set text
        this.namePrompt.setText(this.userPresenter.createNewItemPrompt(0));
        this.descriptionPrompt.setText(this.userPresenter.createNewItemPrompt(1));
        this.confirm.setText(this.userPresenter.userLoanPromptConfirm());
        this.cancel.setText(this.userPresenter.menuPromptExit());

        // set button function
        this.confirm.setOnAction(e -> sendNewItemRequest());
        this.cancel.setOnAction(this::returnToMainMenu);
    }

    @FXML
    public void sendNewItemRequest() {
        String itemName = this.nameInput.getText();
        String itemDescription = this.descriptionInput.getText();
        // if user input is wack
        if(itemName.length() == 0 || itemDescription.length() == 0) {
            this.errorMessage.setText("Please fill out both the name and description inputs.");
        }
        // if user correctly filled out inputs
        else {
            List<Message> adminMessages = this.adminManager.getAdminMessages();
            adminMessages.add(this.messageBuilder.getNewItemRequest("User " + this.currUser +
                            " has created a new item that requires approval.",
                    new Item(itemName, this.currUser, itemDescription)));
            this.adminManager.setAdminMessages(adminMessages);
            this.userPresenter.newItemMessageSentToAdmin();
            // tell user that it is safe to exit now
            this.confirmation.setText(this.userPresenter.newItemMessageSentToAdmin());
        }
    }

    @FXML
    public void returnToMainMenu(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}

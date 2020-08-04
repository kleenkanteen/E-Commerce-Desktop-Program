package frontend.MessageReplySystem;

import frontend.ErrorPopUp.ErrorPopUp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import presenters.MessageReplyPresenter;
import entities.Message;
import use_cases.AdminManager;
import use_cases.MessageBuilder;

public class PrivateMessageResponse implements  MessageResponse, Initializable {
    @FXML private Label title;
    @FXML private Label messageContent;
    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private TextField userInput;
    private Stage window;

    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private Message message;
    private List<Message> messageList;
    private AdminManager adminManager;
    private String accountName;


    PrivateMessageResponse(Message message, AdminManager adminManager, List<Message> messageList, String accountName){
        this.message = message;
        this.adminManager = adminManager;
        this.messageList = messageList;
        this.accountName = accountName;
    }

    @Override
    public String[] getActions() {
        return messageReplyPresenter.privateMessageAction(message);
    }

    @Override
    public void doAction(String action){
        String[]validActions = getActions();
        if(action.equals(validActions[0])){
            messageList.remove(message);
        }
        else if(action.equals(validActions[1])){
            try {
                window = new Stage();
                FXMLLoader reportLoader = new FXMLLoader(getClass().getResource("MakeReport.fxml"));
                reportLoader.setController(this);
                Parent root = reportLoader.load();

                window.initModality(Modality.APPLICATION_MODAL);
                window.setScene(new Scene(root));
                window.setTitle(messageReplyPresenter.reportTitle());
                window.show();
            }catch(IOException e){
                new ErrorPopUp();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText(messageReplyPresenter.reportPrompt());
        messageContent.setText(messageReplyPresenter.messageContent(message));
        button1.setText(messageReplyPresenter.exit());
        button1.setOnAction(e -> window.close());
        button2.setText(messageReplyPresenter.report());
        button2.setOnAction(e -> makeReport());
    }

    private void makeReport(){
        MessageBuilder messageBuilder = new MessageBuilder();
        String reason = userInput.getText();
        Message m = messageBuilder.getReportRequest(reason, accountName, message.getContent(), message.getSender());
        adminManager.addMessage(m);
        messageList.remove(message);
    }

}

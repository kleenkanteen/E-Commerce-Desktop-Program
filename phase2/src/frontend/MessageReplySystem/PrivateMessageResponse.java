package frontend.MessageReplySystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import presenters.MessageReplyPresenter;
import entities.Message;

public class PrivateMessageResponse implements  MessageResponse, Initializable {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private Message message;
    @FXML private Label title;
    @FXML private Label messageContent;
    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private TextField userInput;
    private Stage window;

    PrivateMessageResponse(Message message){
        this.message = message;
    }

    @Override
    public String[] getActions() {
        return messageReplyPresenter.privateMessageActionPrompt(message);
    }

    @Override
    public void doAction(String action){
        String[]validActions = getActions();
        if(action.equals(validActions[0])){
            System.out.println("Delete");
        }
        else if(action.equals(validActions[1])){
            System.out.println("Report");
            try {
                window = new Stage();
                FXMLLoader reportLoader = new FXMLLoader(getClass().getResource("MakeReport.fxml"));
                reportLoader.setController(this);
                Parent root = reportLoader.load();

                window.setScene(new Scene(root));
                window.show();
            }catch(IOException e){
                System.out.println("Error");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText("Write your report:");
        messageContent.setText(message.getContent());
        button1.setText("Exit");
        button1.setOnAction(e -> window.close());
        button2.setText("Report");
        button2.setOnAction(e -> makeReport());
    }

    public void makeReport(){
        System.out.println(userInput.getText());
    }

}

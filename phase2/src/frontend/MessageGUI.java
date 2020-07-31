package frontend;
import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.layout.*;

import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.net.URL;
import java.util.ResourceBundle;

import java.util.*;
import entities.*;
import presenters.MessageReplyMenu;

public class MessageGUI implements Initializable{
    //    private Stage window;
//    private Scene scene1, scene2;
    @FXML private Label messageContent;
    @FXML private ButtonBar buttonBar;
    private List<Message> messageList = new ArrayList<>();
    private int counter = 0;
    //private MessageReplyMenu messageReplyMenu = new MessageReplyMenu();

    public void setMessageList(){
        messageList = new ArrayList<>();
        messageList.add(new ContentMessage("Hi"));
        messageList.add(new ContentMessage("nice day"));
        counter = 0;
    }

    public void setUp(){
        buttonBar.getButtons().clear();

        if(messageList.size() <= counter) {
            //messageContent.setText(messageReplyMenu.printNoMessages());
            messageContent.setText("You have no messages to look through");
            Button button1 = new Button("Exit");
            button1.setOnAction(e ->
                    ((Stage) (((Button) e.getSource()).getScene().getWindow())).close());
            buttonBar.getButtons().add(button1);
        }
        else{
            //String[] s = messageReplyMenu.printContentMessagePrompt(messageList.get(counter));
            String[] s = new String[4];
            s[0] = messageList.get(counter).toString();
            s[1] = "Delete";
            s[2] = "Skip";
            s[3] = "Exit";
            messageContent.setText(s[0]);
            Button button1 = new Button(s[1]);
            //button1.setOnAction(
            buttonBar.getButtons().add(button1);
            Button button2 = new Button(s[2]);
            button2.setOnAction(e ->{
                counter++;
                setUp();});
            buttonBar.getButtons().add(button2);
            Button button3 = new Button(s[3]);
            button3.setOnAction(e ->
                    ((Stage) (((Button) e.getSource()).getScene().getWindow())).close());
            buttonBar.getButtons().add(button3);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setMessageList();
        setUp();
    }
}

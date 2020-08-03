package frontend.MessageReplySystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entities.*;
import presenters.MessageReplyPresenter;
import use_cases.AdminManager;
import use_cases.GlobalInventoryManager;
import use_cases.TradeManager;
import use_cases.UserManager;

//make abstract later
public class MessageReplyGUI implements Initializable{
//    private Stage window;
//    private Scene scene1, scene2;
    @FXML private Label messageContent;
    @FXML private ButtonBar buttonBar;

     UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;
    private TradeManager tradeManager;
     AdminManager adminManager;
    String accountUsername;

    private List<Message> messageList = new ArrayList<>();
    private int counter = 0;

    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private MessageResponseFactory factory;
    private MessageResponse messageResponse;

    public MessageReplyGUI(){
        MessageResponseFactory factory = new MessageResponseFactory();
    }

    public MessageReplyGUI(AdminManager adminManager, GlobalInventoryManager globalInventoryManager,
                           TradeManager tradeManager, UserManager userManager, String accountUsername){
        this.adminManager = adminManager;
        this.globalInventoryManager = globalInventoryManager;
        this.tradeManager = tradeManager;
        this.userManager = userManager;
        this.accountUsername = accountUsername;
        factory = new MessageResponseFactory(adminManager, globalInventoryManager, tradeManager,
                userManager, accountUsername);
        setMessageList();

    }

    private void setMessageList(){
        messageList = new ArrayList<>();
        messageList.add(new SystemMessage("Hi"));
        messageList.add(new SystemMessage("nice day"));
        messageList.add(new PrivateMessage("Hi it's me again", "Max"));
        messageList.add(new FreezeRequest("You should freeze this person", "Max"));
        counter = 0;
    }

    //public abstract void setMessage();

    //public abstract void saveMessage();

    public void setUp(){
        buttonBar.getButtons().clear();

        if(messageList.size() <= counter) {
            messageContent.setText(messageReplyPresenter.printNoMessages());
            Button button1 = new Button("Exit");
            button1.setOnAction(e ->
                    ((Stage) (((Button) e.getSource()).getScene().getWindow())).close());
            buttonBar.getButtons().add(button1);
        }
        else{
            messageResponse = factory.getMessageResponse(messageList.get(counter));
            String[] s = messageResponse.getActions();
            messageContent.setText(messageList.get(counter).toString());

            Button[] buttons = new Button[s.length+2];

           for(int i = 0; i < s.length; i++){
               String action = s[i];
               buttons[i] = new Button(action);
               buttons[i].setOnAction(e -> {
                   messageResponse.doAction(action);
               });
           }

            buttons[s.length] = new Button(messageReplyPresenter.returnSkipPrompt());
            buttons[s.length].setOnAction(e ->{
                counter++;
                setUp();});

            buttons[s.length+1] = new Button(messageReplyPresenter.returnExitPrompt());
            buttons[s.length+1].setOnAction(e ->
                    exitGUI(e));

            buttonBar.getButtons().addAll(buttons);
        }
        //Update message list
    }
    private void exitGUI(ActionEvent e){
        //saveMessage();
        ((Stage) (((Button) e.getSource()).getScene().getWindow())).close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setMessageList();
        //change to setMessage();
        setUp();
    }
}

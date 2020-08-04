package frontend.MessageReplySystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
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
public abstract class MessageReplyGUI implements Initializable{
//    private Stage window;
//    private Scene scene1, scene2;
    @FXML private Label messageContent;
    @FXML private ButtonBar buttonBar;

    UserManager userManager;
    //private GlobalInventoryManager globalInventoryManager;
   // private TradeManager tradeManager;
    AdminManager adminManager;
    String accountUsername;

    private List<Message> messageList;
    private int counter = 0;

    //private List<Message> saveMessageList;

    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private MessageResponseFactory factory;
    private MessageResponse messageResponse;

    //public MessageReplyGUI(){
   //     MessageResponseFactory factory = new MessageResponseFactory();
    //}

    MessageReplyGUI(AdminManager adminManager, GlobalInventoryManager globalInventoryManager,
                           TradeManager tradeManager, UserManager userManager, String accountUsername){
        this.adminManager = adminManager;
        //this.globalInventoryManager = globalInventoryManager;
        //this.tradeManager = tradeManager;
        this.userManager = userManager;
        this.accountUsername = accountUsername;
        factory = new MessageResponseFactory(adminManager, globalInventoryManager, tradeManager,
                userManager, accountUsername);
        setMessageDate();
    }

    public abstract List<Message> getMessage();

    public abstract void saveMessage(List<Message> messages);

    public void setUp(){
        buttonBar.getButtons().clear();

        if(messageList.size() <= counter) {
            messageContent.setText(messageReplyPresenter.printNoMessages());
            Button button1 = new Button("Exit");
            button1.setOnAction(this::exitGUI);
            buttonBar.getButtons().add(button1);
        }
        else{
            messageResponse = factory.getMessageResponse(messageList.get(counter));
            String[] s = messageResponse.getActions();
            messageContent.setText(messageReplyPresenter.messageString(messageList.get(counter)));

            messageContent.setWrapText(true);

            Button[] buttons = new Button[s.length+2];

           for(int i = 0; i < s.length; i++){
               String action = s[i];
               buttons[i] = new Button(action);
               buttons[i].setOnAction(e -> {
                   messageResponse.doAction(action);
                   counter++;
                   setUp();

               });
           }

            buttons[s.length] = new Button(messageReplyPresenter.skip());
            buttons[s.length].setOnAction(e ->{
                counter++;
                setUp();});

            buttons[s.length+1] = new Button(messageReplyPresenter.exit());
            buttons[s.length+1].setOnAction(this::exitGUI);

            buttonBar.getButtons().addAll(buttons);
        }
        //Update message list
    }
    private void exitGUI(ActionEvent e){
        saveMessage(factory.getMessageList());
        ((Stage) (((Node) e.getSource()).getScene().getWindow())).close();
    }

    private void setMessageDate(){
        messageList = new ArrayList<>(getMessage());
        factory.setMessageList(new ArrayList<>(messageList));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setMessageDate();
        setUp();
    }
}

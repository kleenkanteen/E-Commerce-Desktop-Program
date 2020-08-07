package frontend.MessageReplySystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import entities.*;
import presenters.MessageReplyPresenter;
import use_cases.AdminManager;
import use_cases.GlobalInventoryManager;
import use_cases.TradeManager;
import use_cases.UserManager;

public abstract class MessageReplyGUI implements Initializable{
    @FXML private Label messageContent;
    @FXML private ButtonBar buttonBar;
    @FXML private ListView messageListView;
    @FXML private Button confirm;

    UserManager userManager;
    AdminManager adminManager;
    String accountUsername;

    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private MessageResponseFactory factory;
    private MessageResponse messageResponse;

    private HashMap<String, Message> formatter = new HashMap<>();

    MessageReplyGUI(AdminManager adminManager, GlobalInventoryManager globalInventoryManager,
                           TradeManager tradeManager, UserManager userManager, String accountUsername){
        this.adminManager = adminManager;
        this.userManager = userManager;
        this.accountUsername = accountUsername;
        factory = new MessageResponseFactory(adminManager, globalInventoryManager, tradeManager,
                userManager, accountUsername);
        factory.setMessageList(new ArrayList<>(getMessage()));
    }

    public abstract List<Message> getMessage();

    public abstract void saveMessage(List<Message> messages);

    public void setUp(){
        buttonBar.getButtons().clear();

        confirm.setText(messageReplyPresenter.confirm());
        confirm.setOnAction(e -> setUp());

        if(factory.getMessageList().isEmpty()){
            messageContent.setText(messageReplyPresenter.printNoMessages());
            Button button1 = new Button(messageReplyPresenter.exit());
            button1.setOnAction(this::exitGUI);
            buttonBar.getButtons().add(button1);
        }
        else if(messageListView.getSelectionModel().getSelectedItems().isEmpty()){
            messageContent.setText(messageReplyPresenter.instructions());
            Button button1 = new Button(messageReplyPresenter.exit());
            button1.setOnAction(this::exitGUI);
            buttonBar.getButtons().add(button1);
        }
        else {
            String key = (String) messageListView.getSelectionModel().getSelectedItems().get(0);
            Message message = formatter.get(key);

            messageResponse = factory.getMessageResponse(message);
            String[] s = messageResponse.getActions();
            messageContent.setText(messageReplyPresenter.messageString(message));
            messageContent.setWrapText(true);

            Button[] buttons = new Button[s.length + 2];

            for (int i = 0; i < s.length; i++) {
                String action = s[i];
                buttons[i] = new Button(action);
                buttons[i].setOnAction(e -> {
                    messageResponse.doAction(action);
                    refresh();
                    setUp();
                });
            }

            buttons[s.length] = new Button(messageReplyPresenter.skip());
            buttons[s.length].setOnAction(e -> {
                refresh();
                setUp();
            });

            buttons[s.length + 1] = new Button(messageReplyPresenter.exit());
            buttons[s.length + 1].setOnAction(this::exitGUI);

            buttonBar.getButtons().addAll(buttons);
        }
    }

    private void refresh(){
        List<Message> messageList = factory.getMessageList();
        messageListView.getItems().clear();

        for(int i = 1; i<=messageList.size(); i++){
            Message m = messageList.get(i-1);
            String key = i+" From "+m.getSender();
            formatter.put(key, m);
            messageListView.getItems().add(key);
        }

        messageListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    private void exitGUI(ActionEvent e){
        saveMessage(factory.getMessageList());
        ((Stage) (((Node) e.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageContent.setWrapText(true);
        refresh();
        setUp();
    }
}

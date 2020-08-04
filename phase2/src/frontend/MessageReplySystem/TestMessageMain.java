package frontend.MessageReplySystem;

import entities.GlobalInventory;
import entities.Message;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import use_cases.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestMessageMain extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MessageGUI.fxml"));
        AdminManager adminManager = new AdminManager(new HashMap<>(), new ArrayList<>());
        UserManager userManager = new UserManager(new HashMap<>());
        GlobalInventoryManager globalInventoryManager =
                new GlobalInventoryManager(new GlobalInventory());
        TradeManager tradeManager = new TradeManager(new HashMap<>());
        String username = "Max";

        userManager.createNewUser("Max", "123");
        userManager.createNewUser("Hello", "123");
        userManager.setUserMessages("Max", setMessageList());

        UserMessageReplyGUI userMessageReplyGUI = new UserMessageReplyGUI(adminManager, globalInventoryManager,
                tradeManager, userManager,username);

        loader.setController(userMessageReplyGUI);
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private List<Message> setMessageList(){
        List<Message> messageList = new ArrayList<>();
        MessageBuilder messageBuilder = new MessageBuilder();
        messageList.add(messageBuilder.getSystemMessage("Hi iueoqiueiqwueoiqwueiqwouiequwoeiuqwioeuqwioueiqw" +
                "qweoiqwueioqwueiquwoeqw qwieuqwioeuqiwoe eiqwoueqiowueoiwqueiqow e qiweuqwioeuwqoe quiweuqwieouqwo"));
        messageList.add(messageBuilder.getSystemMessage("nice day"));
        messageList.add(messageBuilder.getPrivateMessage("Hi it's me again", "Hello"));
        //messageList.add(new FreezeRequest("You should freeze this person", "Max"));
        return messageList;
    }

}

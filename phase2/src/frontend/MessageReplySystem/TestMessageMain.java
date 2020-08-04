package frontend.MessageReplySystem;

import entities.Admin;
import entities.GlobalInventory;
import entities.User;
import frontend.ErrorPopUp.ErrorPopUp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import use_cases.*;

import java.util.ArrayList;
import java.util.HashMap;

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

        UserMessageReplyGUI userMessageReplyGUI = new UserMessageReplyGUI(adminManager, globalInventoryManager,
                tradeManager, userManager,username);

        loader.setController(userMessageReplyGUI);
        Parent root = loader.load();
        //Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
//        initStyle(StageStyle.UNDECORATED);
//        new ErrorPopUp();
    }

}

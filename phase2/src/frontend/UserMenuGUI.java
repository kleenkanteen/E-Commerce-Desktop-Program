package frontend;

import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import controllers.*;
import use_cases.*;
import entities.*;

public class UserMenuGUI extends Application implements Initializable {

    public Button accountInfo;
    public Button globalInventory;
    public Button loanItem;
    public Button messageInbox;
    public Button newItem;
    public Button unfreezeRequest;
    public Button privateMessage;
    public Button logout;
    private UserMenu userMenu;

    @Override
    public void start(Stage primaryStage) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void getAccountInfo() { }

    public void getGlobalInventory() { }

    public void getLoanMenu() { }

    public void getInbox() { }

    public void getNewItemMenu() { }

    public void getUnfreezeRequest() { }

    public void getPrivateMessageMenu() { }

    public void logoff() { }
}

package frontend.UserGUI;

import controllers.GlobalInventoryController;
import exceptions.UserFrozenException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import presenters.UserPresenter;
import use_cases.*;
import entities.*;

public class UserMenuGUI extends Application implements Initializable {

    @FXML private Button accountInfo;
    @FXML private Button globalInventory;
    @FXML private Button loanItem;
    @FXML private Button messageInbox;
    @FXML private Button newItem;
    @FXML private Button unfreezeRequest;
    @FXML private Button privateMessage;
    @FXML private Button logout;
    @FXML private Label systemMessage;

    private String currUser;
    private AdminManager adminManager;
    private UserPresenter userPresenter;
    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;
    private TradeManager tradeManager;
    private MessageBuilder messageBuilder;

    private final String accountFXML = "AccountInfoGUI.fxml";
    private final String loanFXML = "LoanMenuGUI.fxml";
    private final String privateMessageFXML = "PrivateMessageMenuGUI.fxml";
    private final String userFXML = "UserMenuGUI.fxml";


    /**
     * Instantiates a new UserMenu instance
     * @param currUser the String username of the currently logged in user
     * @param userManager the UserManager object
     * @param tradeManager the TradeManager object
     * @param globalInventoryManager the GlobalInventoryManager object
     * @param globalWishlistManager the GlobalWishlistManager object
     * @param adminManager the AdminManager object
     */
    public UserMenuGUI(String currUser, UserManager userManager, TradeManager tradeManager,
                       GlobalInventoryManager globalInventoryManager, GlobalWishlistManager globalWishlistManager,
                       AdminManager adminManager) {
        this.currUser = currUser;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
        this.adminManager = adminManager;
        this.userPresenter = new UserPresenter();
        this.messageBuilder = new MessageBuilder();
    }

    @Override
    public void start(Stage stage) throws IOException {
        
    }

    // set up button text here when I get around to setting up the presenter properly
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // call getUserStatus/confirmIncompleteUserTrades here?
    }

    /**
     * Switches the scene being viewed
     * @param actionEvent the ActionEvent
     * @param filename the filename of the .fxml file to be loaded
     * @throws IOException for a funky input
     */
    public void switchScene(ActionEvent actionEvent, String filename, String type) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        // access account info
        if(type.equals("ACCOUNT_INFO")) {
            loader.setController(new Object());
        }
        // access global inventory
        else if(type.equals("GLOBAL_INVENTORY")) {
            loader.setController(new Object());
        }
        // access loan menu
        else if(type.equals("LOAN_MENU")) {
            loader.setController(new Object());
        }
        // access user messages
        else if(type.equals("USER_MESSAGES")) {
            loader.setController(new Object());
        }
        // access new item menu
        else if(type.equals("NEW_ITEM")) {
            loader.setController(new Object());
        }
        // access private messages
        else if(type.equals("PRIVATE_MESSAGES")) {
            loader.setController(new PrivateMessageMenu(this.userManager, this.currUser));
        }
        Parent root = loader.load();
        Scene newScene= new Scene(root);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(newScene);
        window.show();
    }

    // switch to the Account info menu scene
    @FXML
    public void getAccountInfo() { }

    // switch to the GlobalInventory scene
    @FXML
    public void getGlobalInventory() {
        if(!this.globalInventoryManager.hasNoItem()) {
            // something something something
        }
        else {
            this.systemMessage.setText(this.userPresenter.emptyGlobalInventory());
        }
    }

    // switch to the loan menu scene
    @FXML
    public void getLoanMenu() {
        // get this user's inventory
        List<Item> userInventory = this.globalInventoryManager.getPersonInventory(this.currUser);
        List<String> itemsToLend = this.globalWishlistManager.userWhoWants(userInventory);
        // check to see if they have anything in it
        if(userInventory.size() == 0) {
            // set a label to this text
            this.systemMessage.setText(this.userPresenter.emptyPersonalInventoryWhileLoaning());
        }
        else if(itemsToLend.size() != 0) {
            this.systemMessage.setText(this.userPresenter.itemNotInOtherUsersWishlist());
        }
        else {
            try {
                if(this.userManager.getCanTradeIgnoreBorrowsLoans(this.currUser,
                        this.tradeManager.getIncompleteTimes(this.currUser),
                        this.tradeManager.numberOfTradesCreatedThisWeek(this.currUser))) {
                    // switchScene to LoanMenu
                }
            }
            catch(UserFrozenException ex) {
                this.systemMessage.setText(this.userPresenter.userAccountFrozen());
            }
        }
    }

    // switch to the user message system scene
    @FXML
    public void getInbox() { }

    // switch to the new item scene
    @FXML
    public void getNewItemMenu() { }

    // switch to the unfreeze request scene
    @FXML
    public void getUnfreezeRequest() {
        if(this.userManager.getUserFrozenStatus(this.currUser)) {
            List<Message> adminMessages = this.adminManager.getAdminMessages();
            adminMessages.add(this.messageBuilder.getUnfreezeRequest("User " + this.currUser +
                    " has requested to be unfrozen.", this.currUser));
            this.adminManager.setAdminMessages(adminMessages);
            this.systemMessage.setText(this.userPresenter.unfreezeRequestSent());
        }
        else {
            this.systemMessage.setText(this.userPresenter.userNotFrozen());
        }
    }

    // switch to the private message sending scene
    @FXML
    public void getPrivateMessageMenu() {

    }

    // exit somehow?
    @FXML
    public void logoff() { }
}

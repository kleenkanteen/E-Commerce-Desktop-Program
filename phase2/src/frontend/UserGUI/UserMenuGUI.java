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

public class UserMenuGUI implements Initializable {

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
    private final String newItemFXML = "NewItemMenuGUI.fxml";
    private final String globalInventoryFXML = "";
    private final String userMessagesFXML = "";
    private final String userFXML = "UserMenuGUI.fxml";

    private Type type;

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

    enum Type {
        ACCOUNT_INFO, GLOBAL_INVENTORY, USER_MESSAGES, LOAN_MENU, NEW_ITEM, PRIVATE_MESSAGES
    }

    // set up button text here when I get around to setting up the presenter properly
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // call getUserStatus/confirmIncompleteUserTrades here?

        // set button text
        this.accountInfo.setText(this.userPresenter.userMenuPromptAccountInfo());
        this.globalInventory.setText(this.userPresenter.userMenuPromptGlobalInventory());
        this.loanItem.setText(this.userPresenter.userMenuPromptLoanMenu());
        this.messageInbox.setText(this.userPresenter.userMenuPromptMessageMenu());
        this.newItem.setText(this.userPresenter.userMenuPromptNewItem());
        this.unfreezeRequest.setText(this.userPresenter.userMenuPromptUnfreeze());
        this.privateMessage.setText(this.userPresenter.userMenuPromptPrivateMessage());
        this.logout.setText(this.userPresenter.userMenuPromptLogout());

        // set up button functionality
        this.accountInfo.setOnAction(e -> getAccountInfo());
        this.globalInventory.setOnAction(e -> getGlobalInventory());
        this.loanItem.setOnAction(e -> getLoanMenu());
        this.messageInbox.setOnAction(e -> getInbox());
        this.newItem.setOnAction(e -> getNewItemMenu());
        this.unfreezeRequest.setOnAction(e -> getUnfreezeRequest());
        this.privateMessage.setOnAction(e -> getPrivateMessageMenu());
        this.logout.setOnAction(this::logoff);
    }

    /**
     * Switches the scene being viewed
     * @param filename the filename of the .fxml file to be loaded
     * @throws IOException for a funky input
     */
    public void switchScene(String filename) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        // access account info
        switch (this.type) {
            case ACCOUNT_INFO:
                loader.setController(new AccountInfoMenu(this.currUser, this.userManager, this.tradeManager,
                        this.globalInventoryManager, this.globalWishlistManager));
                break;
            // access global inventory
            case GLOBAL_INVENTORY:
                // loader.setController(new Object());
                break;
            // access loan menu
            case LOAN_MENU:
                List<Item> userInventory = this.globalInventoryManager.getPersonInventory(this.currUser);
                loader.setController(new LoanMenu(userInventory, this.globalWishlistManager.userWhoWants(userInventory),
                        this.currUser, this.userManager, this.tradeManager, this.globalWishlistManager,
                        this.globalInventoryManager));
                break;
            // access user messages
            case USER_MESSAGES:
                // loader.setController(new Object());
                break;
            // access new item menu
            case NEW_ITEM:
                loader.setController(new NewItemMenu(this.currUser, this.adminManager));
                break;
            // access private messages
            case PRIVATE_MESSAGES:
                loader.setController(new PrivateMessageMenu(this.userManager, this.currUser));
                break;
        }
        Parent root = loader.load();
        Scene newScene= new Scene(root);
        Stage window = new Stage();
        window.setScene(newScene);
        window.show();
    }

    // switch to the Account info menu scene
    public void getAccountInfo() {
        try {
            this.type = Type.ACCOUNT_INFO;
            switchScene(this.accountFXML);
        }
        catch (IOException ex) {
            // some kind of error message?
        }
    }

    // switch to the GlobalInventory scene
    public void getGlobalInventory() {
        if(!this.globalInventoryManager.hasNoItem()) {

            try {
                this.type = Type.GLOBAL_INVENTORY;
                switchScene(this.globalInventoryFXML);
            }
            catch(IOException ex) {
                // some kind of error message
            }
        }
        else {
            this.systemMessage.setText(this.userPresenter.emptyGlobalInventory());
        }
    }

    // switch to the loan menu scene
    public void getLoanMenu() {
        // get this user's inventory, the user that wants something and the item that this user wants
        List<Item> userInventory = this.globalInventoryManager.getPersonInventory(this.currUser);
        List<String> itemsToLend = this.globalWishlistManager.userWhoWants(userInventory);
        // check to see if they have anything in it
        if(userInventory.size() == 0) {
            // set a label to this text
            this.systemMessage.setText(this.userPresenter.emptyPersonalInventoryWhileLoaning());
        }
        // see if anyone is interested in this user's items
        else if(itemsToLend.size() != 0) {
            this.systemMessage.setText(this.userPresenter.itemNotInOtherUsersWishlist());
        }
        else {
            try {
                // this user can trade, switch scene to loan menu
                if(this.userManager.getCanTradeIgnoreBorrowsLoans(this.currUser,
                        this.tradeManager.getIncompleteTimes(this.currUser),
                        this.tradeManager.numberOfTradesCreatedThisWeek(this.currUser))) {
                    try {
                        this.type = Type.LOAN_MENU;
                        switchScene(this.loanFXML);
                    }
                    catch(IOException ex) {
                        // some error message idk
                    }

                }
            }
            // if this user is frozen
            catch(UserFrozenException ex) {
                this.systemMessage.setText(this.userPresenter.userAccountFrozen());
            }
        }
    }

    // switch to the user message system scene
    public void getInbox() { }

    // switch to the new item scene
    public void getNewItemMenu() {
        try {
            this.type = Type.NEW_ITEM;
            switchScene(this.newItemFXML);
        }
        catch (IOException ex) {
            // some error message
        }
    }

    // switch to the unfreeze request scene
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
    public void getPrivateMessageMenu() {
        try {
            this.type = Type.PRIVATE_MESSAGES;
            switchScene(this.privateMessageFXML);
        }
        catch (IOException ex) {
            // some kind of error message?
        }
    }

    // exit
    public void logoff(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}

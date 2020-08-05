package frontend.UserGUI;

import exceptions.UserFrozenException;
import frontend.MessageReplySystem.UserMessageReplyGUI;
import frontend.UserGUI.AccountInfo.BrowseThroughUserCollection;
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

    // JavaFX stuff
    @FXML private Button accountInfo;
    @FXML private Button globalInventory;
    @FXML private Button loanItem;
    @FXML private Button messageInbox;
    @FXML private Button newItem;
    @FXML private Button unfreezeRequest;
    @FXML private Button privateMessage;
    @FXML private Button logout;
    @FXML private Label systemMessage;

    // instance variables
    private String currUser;
    private AdminManager adminManager;
    private UserPresenter userPresenter;
    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;
    private TradeManager tradeManager;
    private MessageBuilder messageBuilder;
    String[] errorMessages = {" ", " ", " "};
    List<Trade> incompletes;
    String errorMessage;
    private Type type;

    // FXML locations
    private final String accountFXML = "AccountInfoGUI.fxml";
    private final String loanFXML = "LoanMenuGUI.fxml";
    private final String privateMessageFXML = "PrivateMessageMenuGUI.fxml";
    private final String newItemFXML = "NewItemMenuGUI.fxml";
    private final String globalInventoryFXML = "";
    private final String userMessagesFXML = "/frontend/MessageReplySystem/MessageGUI.fxml";
    private final String unconfirmedTradesFXML = "UnconfirmedTradesPopUp.fxml";
    private final String userStatusFXML = "UserStatusPopUpGUI.fxml";
    private final String errorAlertFXML = "userStatusPopUpGUI.fxml";
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
        this.incompletes = this.tradeManager.tradesToConfirm(this.currUser);
    }

    /**
     * The ENUM values for UserMenu
     */
    enum Type {
        ACCOUNT_INFO, GLOBAL_INVENTORY, USER_MESSAGES, LOAN_MENU, NEW_ITEM, PRIVATE_MESSAGES,
        UNCONFIRMED_TRADES, USER_STATUS_FROZEN, USER_STATUS_NOT_FROZEN, ERROR
    }

    /**
     * Sets up button functionality/labels and calls getUserStatus, brings up unconfirmedTradesMenu
     * @param location the location? idk
     * @param resources ¯\_(ツ)_/¯
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // call getUserStatus/confirmIncompleteUserTrades here?
        confirmIncompleteUserTrades();
        checkUserStatus();

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
     * Switches the scene being viewed via this.type (enum)
     * @param filename the FXML file path (set as final above)
     * @throws IOException for funky input errors
     */
    public void switchScene(String filename) throws IOException {
        // instantiate the FXMLLoader
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        switch (this.type) {
            // access account info
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
                loader.setController(new UserMessageReplyGUI(this.adminManager, this.globalInventoryManager,
                        this.tradeManager, this.userManager, this.currUser));
                break;
            // access new item menu
            case NEW_ITEM:
                loader.setController(new NewItemMenu(this.currUser, this.adminManager));
                break;
            // access private messages
            case PRIVATE_MESSAGES:
                loader.setController(new PrivateMessageMenu(this.userManager, this.currUser));
                break;
            // access unconfirmed trades menu
            case UNCONFIRMED_TRADES:
                loader.setController(new BrowseThroughUserCollection(this.currUser, this.tradeManager,
                        this.globalWishlistManager, this.incompletes));
                break;
            // pop up for user frozen status
            case USER_STATUS_FROZEN:
                loader.setController(new UserStatusPopUp());
                break;
            // pop up for user failing to meet certain criteria (too many trades, borrows v loans, etc.)
            case USER_STATUS_NOT_FROZEN:
                loader.setController(new UserStatusPopUp(this.errorMessages));
                break;
            case ERROR:
                loader.setController(new ErrorPopUp(this.errorMessage));
        }
        // set up the scene and open up a new window
        Parent root = loader.load();
        Scene newScene= new Scene(root);
        Stage window = new Stage();
        window.setScene(newScene);
        window.show();
    }

    /**
     * Open up the accountInfoMenu via switchScene
     */
    public void getAccountInfo() {
        try {
            this.type = Type.ACCOUNT_INFO;
            switchScene(this.accountFXML);
        }
        catch (IOException ex) {
            this.systemMessage.setText("IOException in getAccountInfo");
        }
    }

    /**
     * Open up the globalInventory Scene via switchScene
     */
    public void getGlobalInventory() {
        if(!this.globalInventoryManager.hasNoItem()) {

            try {
                this.type = Type.GLOBAL_INVENTORY;
                switchScene(this.globalInventoryFXML);
            }
            catch(IOException ex) {
                this.systemMessage.setText("IOException in getGlobalInventory");
            }
        }
        else {
            this.systemMessage.setText(this.userPresenter.emptyGlobalInventory());
        }
    }

    /**
     * Switch to the loan menu scene
     */
    public void getLoanMenu() {
        // get this user's inventory, the user that wants something and the item that this user wants
        List<Item> userInventory = this.globalInventoryManager.getPersonInventory(this.currUser);
        List<String> itemsToLend = this.globalWishlistManager.userWhoWants(userInventory);
        // check to see if they have anything in their inventory
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
                        this.systemMessage.setText("IOException in getLoanMenu");
                    }

                }
            }
            // if this user is frozen
            catch(UserFrozenException ex) {
                this.systemMessage.setText(this.userPresenter.userAccountFrozen());
            }
        }
    }

    /**
     * Switch to the UserMessageResponse whatever window via switchScene
     */
    public void getInbox() {
        try  {
            this.type = Type.USER_MESSAGES;
            switchScene(this.userMessagesFXML);
        }
        catch(IOException ex) {
            this.systemMessage.setText("IOException in getInbox");
        }
    }

    /**
     * Switch to the newItemMenu window via switchScene
     */
    public void getNewItemMenu() {
        try {
            this.type = Type.NEW_ITEM;
            switchScene(this.newItemFXML);
        }
        catch (IOException ex) {
            this.systemMessage.setText("IOException in getNewItemMenu");
        }
    }

    /**
     * Allow for unfreeze request sending
     */
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

    /**
     * Open up the PrivateMessageMenu via switchScene
     */
    public void getPrivateMessageMenu() {
        try {
            this.type = Type.PRIVATE_MESSAGES;
            switchScene(this.privateMessageFXML);
        }
        catch (IOException ex) {
            this.systemMessage.setText("IOException in getPrivateMessageMenu");
        }
    }

    /**
     * Checks on a user's trade status
     */
    private void checkUserStatus() {
        boolean tooManyIncomplete = false;
        boolean tooManyBorrowVLoan = false;
        boolean tooManyTrades = false;
        // check to see if this user is frozen
        if (!this.userManager.getUserFrozenStatus(this.currUser)) {
            // check num of incomplete trades
            if(this.tradeManager.tradesToConfirm(this.currUser).size() >=
                    this.userManager.getUserIncompleteTrades(this.currUser)) {
                this.errorMessages[0] = this.userPresenter.tooManyIncompleteTrades();
                tooManyIncomplete = true;
            }
            // check num of borrows v. loans
            if((this.tradeManager.getBorrowedTimes(this.currUser) - this.tradeManager.getLendTimes(this.currUser)) >
                    this.userManager.getUserThreshold(this.currUser)) {
                this.errorMessages[1] = this.userPresenter.tooManyBorrowsVLoans(
                        this.tradeManager.getBorrowedTimes(this.currUser) -
                                this.tradeManager.getLendTimes(this.currUser));
                tooManyBorrowVLoan = true;
            }
            // check num of trades
            if(tradeManager.numberOfTradesCreatedThisWeek(this.currUser) >=
                    this.userManager.getTradesPerWeekForUser(this.currUser)) {
                this.errorMessages[2] = this.userPresenter.tooManyTradesThisWeek();
                tooManyTrades = true;
            }
            // if too many incompletes or too many borrows, request Freeze of this account
            if(tooManyIncomplete || tooManyBorrowVLoan) {
                List<Message> adminMessages = this.adminManager.getAdminMessages();
                adminMessages.add(this.messageBuilder.getFreezeRequest("User " + this.currUser +
                        " should have their account frozen.", this.currUser));
                this.adminManager.setAdminMessages(adminMessages);
            }
            // if any possible errors are true, bring up a pop up
            if(tooManyIncomplete || tooManyBorrowVLoan || tooManyTrades) {
                try {
                    this.type = Type.USER_STATUS_NOT_FROZEN;
                    switchScene(this.userStatusFXML);
                }
                catch(IOException ex) {
                    this.systemMessage.setText("IOException in checkUserStatus NOT frozen if branch");
                }
            }
        }
        // if the user is already frozen
        else {
            try {
                this.type = Type.USER_STATUS_FROZEN;
                switchScene(this.userStatusFXML);
            }
            catch(IOException ex) {
                this.systemMessage.setText("IOException in checkUserStatus FROZEN if branch");
            }
        }
    }

    /**
     * Allows a user to confirm their incomplete trades.
     */
    private void confirmIncompleteUserTrades() {
        // check to make sure that the user has unconfirmed trades
        if (this.incompletes.size() != 0) {
            // instantiate unconfirmed trades
            try {
                this.type = Type.UNCONFIRMED_TRADES;
                switchScene(this.unconfirmedTradesFXML);
            }
            catch(IOException ex) {
                this.systemMessage.setText("IOException in confirmIncompleteUserTrades");
            }
        }
    }

    /**
     * Close the UserMenu window
     * @param actionEvent the ActionEvent object
     */
    public void logoff(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}

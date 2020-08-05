package frontend.UserGUI;

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
import presenters.UserPresenter;
import use_cases.*;
import entities.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AccountInfoMenu implements Initializable {

    // JavaFX stuff
    @FXML private Button tradeHistory;
    @FXML private Button newPassword;
    @FXML private Button tradePartners;
    @FXML private Button recentTrades;
    @FXML private Button inventory;
    @FXML private Button wishlist;
    @FXML private Button exit;
    @FXML private Label systemMessage;

    // instance variables
    private String currUser;
    private UserManager userManager;
    private TradeManager tradeManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;
    private UserPresenter userPresenter;
    List<Trade> userTrades;
    List<Item> userInventory;
    List<Item> userWishlist;
    private Type type;

    // .fxml pathways
    private final String tradeHistoryFXML = "BrowseThroughUserTrades.fxml";
    private final String passwordFXML = "";
    private final String tradePartnersFXML = "";
    private final String recentTradesFXML = "";
    private final String inventoryFXML = "BrowseThroughUserInventory.fxml";
    private final String wishlistFXML = "BrowseThroughUserWishlist.fxml";

    /**
     * Constructs a new BrowseThroughUserInfo object
     * @param currUser String username
     * @param userManager UserManager object
     * @param tradeManager TradeManager object
     * @param globalInventoryManager GlobalInventoryManager object
     * @param globalWishlistManager GlobalWishlistManager object
     */
    public AccountInfoMenu(String currUser, UserManager userManager, TradeManager tradeManager,
                           GlobalInventoryManager globalInventoryManager,
                           GlobalWishlistManager globalWishlistManager) {
        this.currUser = currUser;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
        this.userPresenter = new UserPresenter();
    }

    enum Type {
        TRADE_HISTORY, PASSWORD, TRADE_PARTNERS, RECENT_TRADES, INVENTORY, WISHLIST
    }

    /**
     * Sets up button text/functionality
     * @param location ¯\_(ツ)_/¯
     * @param resources ¯\_(ツ)_/¯
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set text
        this.tradeHistory.setText(this.userPresenter.accountInfoPromptTradeHistory());
        this.newPassword.setText(this.userPresenter.accountInfoPromptPassword());
        this.tradePartners.setText(this.userPresenter.accountInfoPromptTradingPartners());
        this.recentTrades.setText(this.userPresenter.accountInfoPromptRecentTrades());
        this.inventory.setText(this.userPresenter.accountInfoPromptInventory());
        this.wishlist.setText(this.userPresenter.accountInfoPromptWishlist());
        this.exit.setText(this.userPresenter.menuPromptExit());

        // set button function
        this.tradeHistory.setOnAction(e -> viewTradeHistory());
        this.newPassword.setOnAction(e -> setNewPassword());
        this.tradePartners.setOnAction(e -> viewTradePartners());
        this.recentTrades.setOnAction(e -> viewRecentTrades());
        this.inventory.setOnAction(e -> viewInventory());
        this.wishlist.setOnAction(e -> viewWishlist());
        this.exit.setOnAction(this::returnToMainMenu);
    }

    /**
     * Switches the scene being viewed
     * @param filename the filename of the .fxml file to be loaded
     * @throws IOException for a funky input
     */
    public void switchScene(String filename) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        switch(this.type) {
            // view trade history
            case TRADE_HISTORY:
                loader.setController(new BrowseThroughUserCollection(this.userTrades,
                        this.tradeManager, this.currUser));
                break;
            // change password
            case PASSWORD:
                // loader.setController(new Object());
                break;
            // view trade partners
            case TRADE_PARTNERS:
                // loader.setController(new Object());
                break;
            // view 3 most recent trades
            case RECENT_TRADES:
                // loader.setController(new Object());
                break;
            // view user inventory
            case INVENTORY:
                loader.setController(new BrowseThroughUserCollection(this.userInventory,
                        this.globalInventoryManager, this.currUser));
                break;
            // view user wishlist
            case WISHLIST:
                loader.setController(new BrowseThroughUserCollection(this.userWishlist, this.currUser,
                        this.userManager, this.tradeManager, this.globalInventoryManager, this.globalWishlistManager));
                break;
        }
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(newScene);
        window.show();
    }

    /**
     * Accesses tradeHistory menu
     */
    public void viewTradeHistory() {
        this.userTrades = this.tradeManager.getTradeHistory(this.currUser);
        // if nothing in trade history
        if(this.userTrades.size() == 0) {
            this.systemMessage.setText(this.userPresenter.isEmpty("trade history"));
        }
        else {
            try {
                this.type = Type.TRADE_HISTORY;
                switchScene(this.tradeHistoryFXML);
            }
            catch(IOException ex) {
                this.systemMessage.setText("Input error in viewTradeHistory");
            }
        }
    }

    /**
     * Accesses new password menu
     */
    public void setNewPassword() { }

    /**
     * Accesses trade Partners menu
     */
    public void viewTradePartners() { }

    /**
     * Accesses recent trades menu
     */
    public void viewRecentTrades() { }

    /**
     * Accesses viewInventory menu
     */
    public void viewInventory() {
        this.userInventory = this.globalInventoryManager.getPersonInventory(this.currUser);
        // check to see if the inventory populated
        if(this.userInventory.size() == 0) {
            this.systemMessage.setText(this.userPresenter.isEmpty("inventory"));
        }
        // if the inventory is populated
        else {
            try {
                this.type = Type.INVENTORY;
                switchScene(this.inventoryFXML);
            }
            catch(IOException ex) {
                this.systemMessage.setText("Input error in viewInventory");
            }
        }
    }

    /**
     * Accesses viewWishlist menu
     */
    public void viewWishlist() {
        // list of item ids
        List<String> userWishlistIDs = this.globalWishlistManager.getPersonWishlist(this.currUser);
        // check to see if wishlist is empty
        if(userWishlistIDs.size() == 0) {
            this.systemMessage.setText(this.userPresenter.isEmpty("wishlist"));
        }
        // if the wishlist is populated
        else {
            try {
                // construct the user wishlist
                this.userWishlist = new ArrayList<>();
                for(String itemID : userWishlistIDs) {
                    this.userWishlist.add(this.globalInventoryManager.getItemFromGI(itemID));
                }
                this.type = Type.WISHLIST;
                switchScene(this.wishlistFXML);
            }
            catch(IOException ex) {
                this.systemMessage.setText("Input error in viewWishlist");
            }
        }
    }

    /**
     * Returns to main Menu
     * @param actionEvent the ActionEvent object
     */
    public void returnToMainMenu(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}

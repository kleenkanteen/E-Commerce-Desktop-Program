package frontend.UserGUI;

import entities.PermTrade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Item;
import entities.Trade;
import use_cases.GlobalInventoryManager;
import use_cases.GlobalWishlistManager;
import use_cases.TradeManager;
import use_cases.UserManager;
import presenters.UserPresenter;

public class BrowseThroughUserCollection implements Initializable {

    private List<Item> userInventory;
    private List<Item> wishlist;
    private List<Trade> userTrades;
    private List<Trade> unconfirmedTrades;
    private String currUser;
    private GlobalInventoryManager globalInventoryManager;
    private TradeManager tradeManager;
    private UserManager userManager;
    private GlobalWishlistManager globalWishlistManager;
    private UserPresenter userPresenter;
    private int index;

    private Type type;

    // universal label
    @FXML private Label viewItem;
    @FXML private Label systemMessage;

    // unconfirmed trades label
    @FXML private Label unconfirmedTradesPrompt;
    @FXML private Label allDonePrompt;

    // inventory/wishlist/trades button
    @FXML private Button next;
    @FXML private Button previous;

    // inventory/wishlist buttons
    @FXML private Button remove;

    // unconfirmed trades buttons
    @FXML private Button confirm;
    @FXML private Button deny;

    // universal buttons
    @FXML private Button exit;

    /**
     * ENUM of Collection types
     */
    enum Type {
        INVENTORY, WISHLIST, UNCONFIRMED, TRADE_HISTORY
    }

    /**
     * Instantiate a browseThroughUserTrades object
     * @param userTrades the list of user trades
     * @param tradeManager the TradeManager object
     * @param currUser the currently logged in user
     */
    public BrowseThroughUserCollection(List<Trade> userTrades, TradeManager tradeManager, String currUser) {
        this.userTrades = userTrades;
        this.tradeManager = tradeManager;
        this.currUser = currUser;
        this.userPresenter = new UserPresenter();
        this.index = 0;
        this.type = Type.TRADE_HISTORY;
    }

    /**
     * Instantiate a browseThroughUserInventory object
     * @param userInventory the user's inventory
     * @param globalInventoryManager the GlobalInventoryManager object
     * @param currUser the currently logged in user
     */
    public BrowseThroughUserCollection(List<Item> userInventory, GlobalInventoryManager globalInventoryManager,
                                       String currUser) {
        this.userInventory = userInventory;
        this.globalInventoryManager = globalInventoryManager;
        this.currUser = currUser;
        this.userPresenter = new UserPresenter();
        this.index = 0;
        this.type = Type.INVENTORY;
    }

    /**
     * Instantiate a browseThroughUserWishlist object
     * @param userWishlist the user's wishlist
     * @param currUser the currently logged in user
     * @param userManager the UserManager object
     * @param tradeManager the TradeManager object
     * @param globalInventoryManager the GlobalInventoryManager object
     * @param globalWishlistManager the GlobalWishlistManager object
     */
    public BrowseThroughUserCollection(List<Item> userWishlist, String currUser, UserManager userManager,
                                       TradeManager tradeManager, GlobalInventoryManager globalInventoryManager,
                                     GlobalWishlistManager globalWishlistManager) {
        this.wishlist = userWishlist;
        this.currUser = currUser;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
        this.userPresenter = new UserPresenter();
        this.index = 0;
        this.type = Type.WISHLIST;
    }

    /**
     * Instantiate a browseThroughUnconfirmedTrades listener
     * @param currUser the currently logged in user
     * @param tradeManager the TradeManager object
     * @param globalWishlistManager the GlobalWishlistManager object
     */
    public BrowseThroughUserCollection(String currUser, TradeManager tradeManager,
                                       GlobalWishlistManager globalWishlistManager, List<Trade> unconfirmedTrades) {
        this.currUser = currUser;
        this.tradeManager = tradeManager;
        this.globalWishlistManager = globalWishlistManager;
        this.unconfirmedTrades = unconfirmedTrades;
        this.userPresenter = new UserPresenter();
        this.index = 0;
        this.type = Type.UNCONFIRMED;
    }

    /**
     * Set up button functionality/label text
     * @param location idk
     * @param resources lmao idk
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set up exit button
        this.exit.setText(this.userPresenter.menuPromptExit());
        this.exit.setOnAction(this::exit);

        // set up for types INVENTORY/WISHLIST/TRADE_HISTORY
        if(this.type == Type.INVENTORY || this.type == Type.WISHLIST || this.type == Type.TRADE_HISTORY) {
            // set up button text
            this.next.setText(this.userPresenter.menuPromptNext());
            this.previous.setText(this.userPresenter.menuPromptPrevious());
            this.remove.setText(this.userPresenter.menuPromptRemove());

            // set up button functionality
            this.previous.setOnAction(e -> previous());
            this.next.setOnAction(e -> next());
            //TODO set up remove button
        }
        // set up for UNCONFIRMED TRADES
        else {
            // set up text
            this.unconfirmedTradesPrompt.setText(this.userPresenter.checkUnconfirmedTradesPrompt());
            this.confirm.setText(this.userPresenter.confirmMeetingPrompt());
            this.deny.setText(this.userPresenter.denyMeetingPrompt());

            // set up functionality
            this.confirm.setOnAction(e -> confirm());
            this.deny.setOnAction(e -> deny());

            // hide exit button FOR NOW
            this.exit.setVisible(false);
        }

        // set up initial menu text
        itemToString();
    }

    /**
     * Presents toString of the object
     */
    public void itemToString() {
        switch(this.type) {
            case INVENTORY:
                this.viewItem.setText(this.userInventory.get(this.index).toString());
                break;
            case WISHLIST:
                this.viewItem.setText(this.wishlist.get(this.index).toString());
                break;
            case TRADE_HISTORY:
                this.viewItem.setText(this.userTrades.get(this.index).toString());
                break;
            case UNCONFIRMED:
                this.viewItem.setText(this.unconfirmedTrades.get(this.index).toString());
                break;
        }
    }

    /**
     * Go to next item
     * CHECK INDEX FIRST to prevent user from infinitely going to an invalid index
     */
    public void next() {
        switch(this.type) {
            case INVENTORY:
                if(this.index == this.userInventory.size() - 1) {
                    this.systemMessage.setText(this.userPresenter.endOfUserInventory());
                }
                else{
                    this.index++;
                }
                break;
            case WISHLIST:
                if(this.index == this.wishlist.size() - 1) {
                    this.systemMessage.setText(this.userPresenter.endOfUserWishlist());
                }
                else {
                    this.index++;
                }
                break;
            case TRADE_HISTORY:
                if(this.index == this.userTrades.size() - 1) {
                    this.systemMessage.setText(this.userPresenter.userTradeHistoryEndOfIndex());
                }
                else {
                    this.index++;
                }
                break;
            case UNCONFIRMED:
                if(this.index == this.unconfirmedTrades.size() - 1) {
                    this.allDonePrompt.setText(this.userPresenter.unconfirmedMeetingAllDone());
                    this.confirm.setVisible(false);
                    this.deny.setVisible(false);
                    this.exit.setVisible(true);
                }
                else {
                    this.index++;
                }
        }
        itemToString();
    }

    /**
     * Go to previous item
     */
    public void previous() {
        switch(this.type) {
            case INVENTORY:
                if(this.index == 0) {
                    this.systemMessage.setText(this.userPresenter.endOfUserInventory());
                }
                else{
                    this.index--;
                }
                break;
            case WISHLIST:
                if(this.index == 0) {
                    this.systemMessage.setText(this.userPresenter.endOfUserWishlist());
                }
                else {
                    this.index--;
                }
                break;
            case TRADE_HISTORY:
                if(this.index == 0) {
                    this.systemMessage.setText(this.userPresenter.userTradeHistoryEndOfIndex());
                }
                else {
                    this.index--;
                }
                break;
        }
        itemToString();
    }

    /**
     * Method for confirming unconfirmed user trades
     */
    public void confirm() {
        Trade trade = this.unconfirmedTrades.get(this.index);
        this.tradeManager.setConfirm(this.currUser, trade, true);

        // if this trade is a PermTrade
        if (trade instanceof PermTrade) {
            // remove all traderA items from Global wishlist if not empty
            removeTradingItemsFromWishlist(trade);
        }
        next();
    }

    /**
     * Method for denying unconfirmed user trades
     */
    public void deny() {
        Trade trade = this.unconfirmedTrades.get(this.index);
        this.tradeManager.setConfirm(this.currUser, trade, false);
        next();
    }

    /**
     * Helper to remove the items from the global wishlist if necessary
     * @param trade the Trade item to use
     */
    private void removeTradingItemsFromWishlist(Trade trade) {
        if(trade.getTraderAItemsToTrade().size() != 0) {
            for(Item item : trade.getTraderAItemsToTrade()) {
                // check to make sure that this item exists on the global wishlist
                if(this.globalWishlistManager.isItemWanted(item.getItemID())) {
                    this.globalWishlistManager.removeItem(item.getItemID());
                }
            }
        }
        // remove all tradeB items from Global and Personal wishlists if not empty
        if(trade.getTraderBItemsToTrade().size() != 0) {
            for (Item item : trade.getTraderBItemsToTrade()) {
                // check to make sure item exists in global wishlist
                if(this.globalWishlistManager.isItemWanted(item.getItemID())) {
                    this.globalWishlistManager.removeItem(item.getItemID());
                }
            }
        }
    }

    /**
     * Exit the scene
     * @param actionEvent ActionEvent object
     */
    public void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}

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
import javafx.stage.Stage;
import presenters.UserPresenter;
import use_cases.*;
import entities.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountInfoMenu implements Initializable {

    @FXML private Button tradeHistory;
    @FXML private Button newPassword;
    @FXML private Button tradePartners;
    @FXML private Button recentTrades;
    @FXML private Button inventory;
    @FXML private Button wishlist;
    @FXML private Button exit;

    private String currUser;
    private UserManager userManager;
    private TradeManager tradeManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;
    private UserPresenter userPresenter;

    private Type type;

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

    // set  button text when I get the presenter properly set
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
        this.tradePartners.setOnAction(e -> viewTradeHistory());
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
                // loader.setController(new Object());
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
                // loader.setController(new Object());
                break;
            // view user wishlist
            case WISHLIST:
                // loader.setController(new Object());
                break;
        }
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void viewTradeHistory() { }

    @FXML
    public void setNewPassword() { }

    @FXML
    public void viewTradePartners() { }

    @FXML
    public void viewRecentTrades() { }

    @FXML
    public void viewInventory() { }

    @FXML
    public void viewWishlist() { }

    @FXML
    public void returnToMainMenu(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}

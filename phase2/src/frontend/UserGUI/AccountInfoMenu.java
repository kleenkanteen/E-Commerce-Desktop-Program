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
import use_cases.*;
import entities.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountInfoMenu extends Application implements Initializable {

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
    }

    @Override
    public void start(Stage primaryStage) {

    }

    // set  button text when I get the presenter properly set
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Switches the scene being viewed
     * @param actionEvent the ActionEvent
     * @param filename the filename of the .fxml file to be loaded
     * @throws IOException for a funky input
     */
    public void switchScene(ActionEvent actionEvent, String filename) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(filename));
        Scene newScene = new Scene(root);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

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
    public void returnToMainMenu() { }
}

package frontend.UserGUI;

import entities.Item;
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
import use_cases.*;
import presenters.UserPresenter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoanMenu extends Application implements Initializable {

    private List<Item> userInventory;
    private List<String> itemsToLend;
    private String currUser;
    private UserManager userManager;
    private TradeManager tradeManager;
    private GlobalWishlistManager globalWishlistManager;
    private UserPresenter userPresenter;
    private GlobalInventoryManager globalInventoryManager;

    @FXML private Label itemName;
    @FXML private Label recipientUser;
    @FXML private Button confirmTrade;
    @FXML private Button denyTrade;

    public LoanMenu(List<Item> userInventory, List<String> itemsToLend, String currUser, UserManager userManager,
                    TradeManager tradeManager, GlobalWishlistManager globalWishlistManager,
                    GlobalInventoryManager globalInventoryManager) {
        this.userInventory = userInventory;
        this.itemsToLend = itemsToLend;
        this.currUser = currUser;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalWishlistManager = globalWishlistManager;
        this.globalInventoryManager = globalInventoryManager;
    }

    @Override
    public void start(Stage primaryStage) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set up buttons here
        List<Item> userItem = setUpTradeOptions();
        this.itemName.setText(userItem.get(0).getName());
        this.recipientUser.setText(this.itemsToLend.get(1));
    }

    /**
     * Switches the scene being viewed
     * @param actionEvent the ActionEvent
     * @param filename the filename of the .fxml file to be loaded
     * @throws IOException for a funky input
     */
    public void switchScene(ActionEvent actionEvent, String filename) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(filename));
        Scene newScene= new Scene(root);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    private List<Item> setUpTradeOptions() {
        List<Item> userItem = new ArrayList<>();
        for(Item item : this.userInventory) {
            if (item.getItemID().equals(this.itemsToLend.get(0))) {
                userItem.add(item);
            }
        }
        return userItem;
    }

    @FXML
    public void sendTradeRequest() {
        // trade controller call
    }

    @FXML
    public void returnToMainMenu() {
        // some way to return to main menu
    }
}

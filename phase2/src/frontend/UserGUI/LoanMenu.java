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

public class LoanMenu implements Initializable {

    private List<Item> userInventory;
    private List<String> itemsToLend;
    private String currUser;
    private UserManager userManager;
    private TradeManager tradeManager;
    private GlobalWishlistManager globalWishlistManager;
    private UserPresenter userPresenter;
    private GlobalInventoryManager globalInventoryManager;

    @FXML private Label itemPrompt;
    @FXML private Label recipientPrompt;
    @FXML private Label userItem;
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
    public void initialize(URL location, ResourceBundle resources) {
        // set text
        List<Item> userItem = setUpTradeOptions();
        this.userItem.setText(userItem.get(0).getName());
        this.recipientUser.setText(this.itemsToLend.get(1));
        this.itemPrompt.setText(this.userPresenter.userLoanPromptOfferedItemLabel());
        this.recipientPrompt.setText(this.userPresenter.userLoanPromptRecipientLabel());
        this.confirmTrade.setText(this.userPresenter.userLoanPromptConfirm());
        this.denyTrade.setText(this.userPresenter.userLoanPromptCancel());

        // set button function
        this.confirmTrade.setOnAction(e -> sendTradeRequest());
        this.denyTrade.setOnAction(this::returnToMainMenu);
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
    private void sendTradeRequest() {
        // trade controller call
    }

    @FXML
    private void returnToMainMenu(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}

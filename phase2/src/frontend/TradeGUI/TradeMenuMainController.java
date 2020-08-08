package frontend.TradeGUI;

import controllers.TradeController;
import entities.Item;
import entities.Message;
import exceptions.IncompleteTradeException;
import frontend.GlobalInventoryGUI.MultiItemMenu;
import frontend.PopUp.PopUp;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import presenters.TradeMenu;
import use_cases.GlobalInventoryManager;
import use_cases.GlobalWishlistManager;
import use_cases.MessageBuilder;
import use_cases.UserManager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TradeMenuMainController implements Initializable {
    private GlobalInventoryManager globalInventoryManager = null;
    private ObservableList<Item> itemsToTrade = null;
    private UserManager allUsers;
    private String userA;
    private List<Item> itemsToTradeB;
    private ObservableList<Item> itemsToTradeA;
    private GlobalWishlistManager globalWishlistManager;
    private String userB;

    @FXML private Label titleScreen;
    @FXML private TextField timeOfTrade;
    @FXML private TextField meetTrade;
    @FXML private MenuButton typesOfTrade;
    @FXML private MenuItem temporary;
    @FXML private MenuItem permanent;
    @FXML private MenuButton oneOrTwoWayTrade;
    @FXML private MenuItem oneWayTrade;
    @FXML private MenuItem twoWayTrade;
    @FXML private Button submit;
    @FXML private Button returnToMainMenu;
    @FXML private DatePicker primaryDate;

    /**
     * A controller for TradeMenuMain.fxml
     * @param globalInventoryManager is a GlobalInventoryManager that takes in all inventories from different users in the program.
     * @param allUsers is a UserManager that contains all the users in the program
     * @param itemsToTradeB is a List that contains the items to trade from userB to userA.
     * @param userA is a String that contains the current user.
     */
    public TradeMenuMainController(GlobalInventoryManager globalInventoryManager, GlobalWishlistManager globalWishlistManager, UserManager allUsers, List<Item> itemsToTradeB, String userA) {
        this.itemsToTradeB = itemsToTradeB;
        this.userA = userA;
        this.userB = itemsToTradeB.get(0).getName();
        this.allUsers = allUsers;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
    }

    /**
     * This method is used to initialize the text
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // setting up text for GUI.
        titleScreen.setText(TradeMenu.TRADE);
        typesOfTrade.setText(TradeMenu.TRADETYPE);
        temporary.setText(TradeMenu.TEMP);
        permanent.setText(TradeMenu.PERM);
        oneOrTwoWayTrade.setText(TradeMenu.ONETWOWAY);
        oneWayTrade.setText(TradeMenu.ONEWAY);
        twoWayTrade.setText(TradeMenu.TWOWAY);
        submit.setText(TradeMenu.SUBMIT);
        returnToMainMenu.setText(TradeMenu.EXIT);

        // actions
        permanent.setOnAction(this::permChoice);
        temporary.setOnAction(this::tempChoice);
        submit.setOnAction(actionEvent -> {
            try {
                submitAction(actionEvent);
            } catch (IncompleteTradeException error) {
                new PopUp(TradeMenu.ERROR);
            }
        });
        oneWayTrade.setOnAction(this::oneWayChoice);
        twoWayTrade.setOnAction(actionEvent -> {
            try {
                twoWayChoice(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        returnToMainMenu.setOnAction(this::exitProgram);

    }
    
    @FXML
    public void permChoice(ActionEvent actionEvent) {
        typesOfTrade.setText(permanent.getText());
    }

    @FXML
    public void tempChoice(ActionEvent actionEvent) {
        typesOfTrade.setText(temporary.getText());
    }

    @FXML
    public void submitAction(ActionEvent actionEvent) throws IncompleteTradeException {
        LocalDateTime tradeDateTime;
        String placeOfMeeting;
        String tradeType;
        String oneTwoWayTrade;
        if ((primaryDate.getValue() != null) &&
                (timeOfTrade.getText() != null) &&
                (!typesOfTrade.getText().equals(TradeMenu.TRADETYPE)) &&
                (!oneOrTwoWayTrade.getText().equals(TradeMenu.ONETWOWAY)) &&
                (!itemsToTradeA.isEmpty())) {

            // convert LocalDate to LocalDateTime
            LocalDate tradeDate = primaryDate.getValue();
            String datePattern = "H:mm";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
            LocalTime tradeTime = LocalTime.parse(timeOfTrade.getText().replaceAll("\\s+", ""), formatter);
            tradeDateTime = tradeDate.atTime(tradeTime);

            // rest of the inputs from the user.
            placeOfMeeting = meetTrade.getText();
            tradeType = typesOfTrade.getText();
            oneTwoWayTrade = oneOrTwoWayTrade.getText();

        } else {
            throw new IncompleteTradeException();
        }
        String userB = itemsToTradeB.get(0).getName();
        Message tradeRequest = null;
        // push it into TradeRequestManager.
        // TODO find out where to put suggestions in TradeMainMenuController.
        MessageBuilder messageBuilder = new MessageBuilder();
        if (tradeType.equals(TradeMenu.PERM)) {
            tradeRequest = messageBuilder.getTradeRequest("User " + userA + " wants to trade with you.", userA, userA, userB, itemsToTradeB, itemsToTradeA, true);
        } else if (tradeType.equals(TradeMenu.TEMP)) {
            tradeRequest = messageBuilder.getTradeRequest("User " + userA + " wants to trade with you.", userA, userA, userB, itemsToTradeB, itemsToTradeA, false);
        }
        allUsers.addUserMessage(userB, tradeRequest);
    }

    @FXML
    public void oneWayChoice(ActionEvent actionEvent) {
        oneOrTwoWayTrade.setText(oneWayTrade.getText());
    }

    @FXML
    public void twoWayChoice(ActionEvent actionEvent) throws IOException {
        oneOrTwoWayTrade.setText(twoWayTrade.getText());
        PopUp suggestedItems = new PopUp(suggestedItems());

        itemsToTradeB = itemsToTrade;
        MultiItemMenu multiItemMenu = new MultiItemMenu(userA, globalInventoryManager);
        switchScene(multiItemMenu);
        itemsToTradeA = multiItemMenu.getItem();
//        System.out.println(multiItemMenu.getItem());
    }

    private String suggestedItems() {
        StringBuilder suggestedItems = new StringBuilder("Here are a list of items that you should trade to " + userB + ":");
        TradeController tradeController = new TradeController(globalInventoryManager, globalWishlistManager);
        ArrayList<Item> listOfSuggestedItems = (ArrayList<Item>) findSuggestedItems(userA, userB);
        // showing suggestions to the user.
        for (Item suggestion : listOfSuggestedItems) {
            suggestedItems.append(suggestion).append("\n");
        }
        return suggestedItems.toString();
    }

    /**
     * Finds suggested items for the user to use when making a two way trade with another user.
     * @param userA is a String that represents the user making the trade to userB.
     * @param userB is a String that represents the person that is receiving the trade from userA.
     * @return a list of Suggested items for userA to trade with userB.
     */
    public List<Item> findSuggestedItems(String userA, String userB) {
        List<Item> userAInventory = this.globalInventoryManager.getPersonInventory(userA);
        List<Item> suggestedItems = new ArrayList<Item>();
        List<String> interestedItemIDs = this.globalWishlistManager.getInterestedItems(userAInventory, userB);

        if (!interestedItemIDs.isEmpty()) {
            // converting itemIDs into Items.
            for (String itemID : interestedItemIDs) {
                suggestedItems.add(this.globalInventoryManager.getItemFromGI(itemID));
            }
        }
        return suggestedItems;
    }

    private void switchScene(MultiItemMenu multiItemMenu) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MultiItemMenu.fxml"));
        loader.setController(multiItemMenu);
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void exitProgram(ActionEvent actionEvent) {
        System.exit(0);
    }
}

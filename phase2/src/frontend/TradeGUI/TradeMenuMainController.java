package frontend.TradeGUI;

import controllers.TradeController;
import entities.Item;
import entities.Message;
import exceptions.IncompleteTradeException;
import frontend.GlobalInventoryGUI.MultiItemMenu;
import frontend.PopUp.PopUp;
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
import java.util.ResourceBundle;

public class TradeMenuMainController implements Initializable {
    private GlobalInventoryManager globalInventoryManager = null;
    private ArrayList<Item> itemsToTrade = null;
    private UserManager allUsers;
    private String userA;
    private ArrayList<Item> itemsToTradeB;
    private ArrayList<Item> itemsToTradeA;
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


    public TradeMenuMainController(GlobalInventoryManager globalInventoryManager, GlobalWishlistManager globalWishlistManager, UserManager allUsers, ArrayList<Item> itemsToTradeB, String userA) {
        this.itemsToTradeB = itemsToTradeB;
        this.userA = userA;
        this.userB = itemsToTradeB.get(0).getName();
        this.allUsers = allUsers;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
    }

    public void buildController() {

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
        twoWayTrade.setOnAction(this::twoWayChoice);
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
    public void twoWayChoice(ActionEvent actionEvent) {
        oneOrTwoWayTrade.setText(twoWayTrade.getText());
        // TODO open MultiItemMenu to get itemsToTradeA.
        PopUp suggestedItems = new PopUp(suggestedItems());

//        itemsToTradeB = itemsToTrade;
//        MultiItemMenu multiItemMenu = new MultiItemMenu(userA, globalInventoryManager);
//        try {
//            switchScene(multiItemMenu);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        itemsToTradeA = itemsToTrade;
    }

    private String suggestedItems() {
        StringBuilder suggestedItems = new StringBuilder("Here are a list of items that you should trade to " + userB + ":");
        TradeController tradeController = new TradeController(globalInventoryManager, globalWishlistManager);
        ArrayList<Item> listOfSuggestedItems = (ArrayList<Item>) tradeController.findSuggestedItems(userA, userB);
        // showing suggestions to the user.
        for (Item suggestion : listOfSuggestedItems) {
            suggestedItems.append(suggestion).append("\n");
        }
        return suggestedItems.toString();
    }

    private void switchScene(MultiItemMenu multiItemMenu) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MultiItemMenu.fxml"));
        loader.setController(multiItemMenu);
        Parent root = loader.load();
        Scene newScene= new Scene(root);
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

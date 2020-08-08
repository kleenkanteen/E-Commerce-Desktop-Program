package frontend.TradeGUI;

import entities.Item;
import entities.Message;
import exceptions.IncompleteTradeException;
import frontend.PopUp.PopUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import presenters.TradeMenu;
import use_cases.GlobalInventoryManager;
import use_cases.MessageBuilder;
import use_cases.TradeRequestManager;
import use_cases.UserManager;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TradeMenuMainLendController implements Initializable {

    private List<Item> itemsToTradeB;
    private String userA;
    private UserManager allUsers;
    private GlobalInventoryManager globalInventoryManager;

    private String tradeType;
    private String placeOfMeeting;
    private Message tradeRequest;
    private LocalDateTime tradeDateTime;

    @FXML private Label titleScreen;
    @FXML private TextField timeOfTrade;
    @FXML private TextField meetTrade;
    @FXML private MenuButton typesOfTrade;
    @FXML private MenuItem temporary;
    @FXML private MenuItem permanent;
    @FXML private Button submit;
    @FXML private Button returnToMainMenu;
    @FXML private DatePicker primaryDate;

    /**
     * A controller for TradeMenuMainLend.fxml
     * @param globalInventoryManager is a GlobalInventoryManager that takes in all inventories from different users in the program.
     * @param allUsers is a UserManager that contains all the users in the program
     * @param itemsToTradeB is a List that contains the items to trade from userB to userA.
     * @param userA is a String that contains the current user.
     */
    public TradeMenuMainLendController(GlobalInventoryManager globalInventoryManager, UserManager allUsers, List<Item> itemsToTradeB, String userA) {
        this.itemsToTradeB = itemsToTradeB;
        this.userA = userA;
        this.allUsers = allUsers;
        this.globalInventoryManager = globalInventoryManager;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // setting up text for GUI.
        titleScreen.setText(TradeMenu.TRADELEND);
        typesOfTrade.setText(TradeMenu.TRADETYPE);
        temporary.setText(TradeMenu.TEMP);
        permanent.setText(TradeMenu.PERM);
        submit.setText(TradeMenu.SUBMIT);
        returnToMainMenu.setText(TradeMenu.EXIT);

        permanent.setOnAction(this::permChoice);
        temporary.setOnAction(this::tempChoice);
        submit.setOnAction(actionEvent -> {
            try {
                submitAction(actionEvent);
            } catch (IncompleteTradeException error) {
                new PopUp(TradeMenu.ERROR);
            }
        });
        returnToMainMenu.setOnAction(this::exitProgram);
    }

    @FXML
    private void permChoice(ActionEvent actionEvent) {
        typesOfTrade.setText(permanent.getText());
    }

    @FXML
    private void tempChoice(ActionEvent actionEvent) {
        typesOfTrade.setText(temporary.getText());
    }

    @FXML
    private void submitAction(ActionEvent actionEvent) throws IncompleteTradeException {
        TradeRequestManager tradeRequestManager;
        if ((primaryDate.getValue() != null) &&
                (timeOfTrade.getText() != null) &&
                (!typesOfTrade.getText().equals(TradeMenu.TRADETYPE))) {

            // convert LocalDate to LocalDateTime
            LocalDate tradeDate = primaryDate.getValue();
            String datePattern = "H:mm";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
            LocalTime tradeTime = LocalTime.parse(timeOfTrade.getText().replaceAll("\\s+", ""), formatter);
            tradeDateTime = tradeDate.atTime(tradeTime);

            // rest of the inputs from the user.
            placeOfMeeting = meetTrade.getText();
            tradeType = typesOfTrade.getText();
        } else {
            throw new IncompleteTradeException();
        }

        String userB = itemsToTradeB.get(0).getName();

        // push it into TradeRequestManager.
        MessageBuilder messageBuilder = new MessageBuilder();
        if (tradeType.equals(TradeMenu.PERM)) {
            tradeRequest = messageBuilder.getTradeRequest("User " + userA + " wants to trade with you.", userA, userA, userB, itemsToTradeB, new ArrayList<Item>(), true);
        } else if (tradeType.equals(TradeMenu.TEMP)) {
            tradeRequest = messageBuilder.getTradeRequest("User " + userA + " wants to trade with you.", userA, userA, userB, itemsToTradeB, new ArrayList<Item>(), false);
        }
        allUsers.addUserMessage(userB, tradeRequest);

    }

    @FXML
    private void exitProgram(ActionEvent actionEvent) {
        System.exit(0);
    }

}

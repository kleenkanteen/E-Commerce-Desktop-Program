package frontend.TradeGUI;

import exceptions.IncompleteTradeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import presenters.TradeMenu;
import use_cases.TradeRequestManager;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TradeMenuMainController implements Initializable {
    @FXML public Label titleScreen;
    @FXML public TextField timeOfTrade;
    @FXML public TextField meetTrade;
    @FXML public MenuButton typesOfTrade;
    @FXML public MenuItem temporary;
    @FXML public MenuItem permanent;
    @FXML public MenuButton oneOrTwoWayTrade;
    @FXML public MenuItem oneWayTrade;
    @FXML public MenuItem twoWayTrade;
    @FXML public Button submit;
    @FXML public Button returnToMainMenu;
    @FXML DatePicker primaryDate;
    /**
     * This method is used to initialize the text
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleScreen.setText(TradeMenu.trade);
        typesOfTrade.setText(TradeMenu.tradeType);
        temporary.setText(TradeMenu.temp);
        permanent.setText(TradeMenu.perm);
        oneOrTwoWayTrade.setText(TradeMenu.oneTwoWay);
        oneWayTrade.setText(TradeMenu.oneWay);
        twoWayTrade.setText(TradeMenu.twoWay);
        submit.setText(TradeMenu.submit);
        returnToMainMenu.setText(TradeMenu.exit);
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
        if ((primaryDate.getValue() != null) &&
                (timeOfTrade.getText() != null) &&
                (!typesOfTrade.getText().equals(TradeMenu.tradeType)) &&
                (!oneOrTwoWayTrade.getText().equals(TradeMenu.oneTwoWay))) {

            // convert LocalDate to LocalDateTime
            LocalDate tradeDate = primaryDate.getValue();
            String datePattern = "H:mm";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
            LocalTime tradeTime = LocalTime.parse(timeOfTrade.getText().replaceAll("\\s+", ""), formatter);
            LocalDateTime tradeDateTime = tradeDate.atTime(tradeTime);

            // rest of the inputs from the user.
            String placeOfMeeting = meetTrade.getText();
            String tradeType = typesOfTrade.getText();
            String oneTwoWayTrade = oneOrTwoWayTrade.getText();
        } else {
            throw new IncompleteTradeException();
        }

        // push it into TradeRequestManager.
    }

    @FXML
    public void oneTwoWayDropDown(ActionEvent actionEvent) {
    }

    @FXML
    public void oneWayChoice(ActionEvent actionEvent) {
        oneOrTwoWayTrade.setText(oneWayTrade.getText());
    }

    @FXML
    public void twoWayChoice(ActionEvent actionEvent) {
        oneOrTwoWayTrade.setText(twoWayTrade.getText());
    }

    @FXML
    public void exitProgram(ActionEvent actionEvent) {
        System.exit(0);
    }
}

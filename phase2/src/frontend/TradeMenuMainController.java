package frontend;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleScreen.setText("Trade");
        typesOfTrade.setText("What type of trade?");
        temporary.setText("Temporary");
        permanent.setText("Permanent");
        oneOrTwoWayTrade.setText("Is it a one way or two way trade?");
        oneWayTrade.setText("One way trade");
        twoWayTrade.setText("Two way trade");
        submit.setText("Submit");
        returnToMainMenu.setText("Return to Main Menu");
    }


}

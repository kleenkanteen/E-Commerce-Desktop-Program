package frontend.DemoUserGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DemoUserTradeMenu implements Initializable {
    DemoUserfxPresenter demoUserPresenter = new DemoUserfxPresenter();
    @FXML
    public Label titleScreen;
    @FXML
    public TextField timeOfTrade;
    @FXML
    public TextField meetTrade;
    @FXML
    public MenuButton typesOfTrade;
    @FXML
    public MenuItem temporary;
    @FXML
    public MenuItem permanent;
    @FXML
    public MenuButton oneOrTwoWayTrade;
    @FXML
    public MenuItem oneWayTrade;
    @FXML
    public MenuItem twoWayTrade;
    @FXML
    public Button submit;
    @FXML
    public Button returnToMainMenu;
    @FXML
    DatePicker primaryDate;
    @FXML Label message;


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
        returnToMainMenu.setOnAction(this::exit);
        submit.setOnAction(this::demoTrade);
    }

    @FXML
    public void exit(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void demoTrade(ActionEvent event){
        message.setText(demoUserPresenter.createAcc());
    }
}


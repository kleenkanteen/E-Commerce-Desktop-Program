package frontend;

import entities.Item;
import entities.PermTrade;
import entities.Trade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TradeUndoController implements Initializable {
    @FXML private Button searchUserButton;
    @FXML private Button goBackButton;


    public void switchScene(ActionEvent actionEvent, String fileName) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fileName));
        Scene newScene= new Scene(root);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }
    private void openTradeWindow(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("User's Unstarted Trades");
        window.setMinWidth(800);
        Parent root = FXMLLoader.load(getClass().getResource("UndoUnstartedTradeMenu.fxml"));
        Scene newScene= new Scene(root);
        window.setScene(newScene);
        window.showAndWait();
    }







    public void searchUserButtonPushed(ActionEvent actionEvent) throws IOException {
        openTradeWindow(actionEvent);

    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        if(location.toString().matches("UndoUnstartedTradeMenu.fxml")){
//        column1.setCellValueFactory(new PropertyValueFactory<Trade, String>("TraderA"));
//        column2.setCellValueFactory(new PropertyValueFactory<Trade, String>("TraderB"));
//        column3.setCellValueFactory(new PropertyValueFactory<Trade, LocalDateTime>("startDate"));
//        tableView.setItems(getTrade());}

    }
}

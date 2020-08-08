
package frontend.AdminGUI;

import entities.Item;
import entities.PermTrade;
import entities.Trade;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class UndoUnstartedTradeMenuController implements Initializable {
    @FXML private Button deleteTradeButton;
    @FXML private Button exitButton;
    @FXML
    private TableView<Trade> tableView;
    @FXML private TableColumn<Trade, String> column1;
    @FXML private TableColumn<Trade, String> column2;
    @FXML private TableColumn<Trade, ArrayList<Item>> column3;
    @FXML private TableColumn<Trade, ArrayList<Item>> column4;
    @FXML private TableColumn<Trade, LocalDateTime> column5;
    private String currentUserName;
    private AdminGUIPresenter adminGUIPresenter;

    private UserManager usermanager;
    private TradeManager tradeManager;
    UndoUnstartedTradeMenuController(String currentUserName, TradeManager tradeManager, UserManager userManager){
        this.currentUserName = currentUserName;
        this.usermanager = userManager;
        this.tradeManager = tradeManager;
        adminGUIPresenter = new AdminGUIPresenter();
    }


    public void deleteTradeButtonPushed(){
        ObservableList<Trade> allTrades;
        Trade selectedRow;

        allTrades = tableView.getItems();
        selectedRow = tableView.getSelectionModel().getSelectedItem();

        allTrades.remove(selectedRow);
        tradeManager.removeTrade(selectedRow);
    }

    public void close(ActionEvent actionEvent){
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(getTrade());
        exitButton.setText(adminGUIPresenter.exitButton());
        exitButton.setOnAction(this::close);
        deleteTradeButton.setText(adminGUIPresenter.deleteSelectedTradeButton());
        column1.setCellValueFactory(new PropertyValueFactory<Trade, String>("TraderA"));
        column2.setCellValueFactory(new PropertyValueFactory<Trade, String>("TraderB"));
        column3.setCellValueFactory(new PropertyValueFactory<Trade, ArrayList<Item>>("traderAItemsToTrade"));
        column4.setCellValueFactory(new PropertyValueFactory<Trade, ArrayList<Item>>("traderBItemsToTrade"));
        column5.setCellValueFactory(new PropertyValueFactory<Trade, LocalDateTime>("startDate"));
        deleteTradeButton.setOnAction(e -> deleteTradeButtonPushed());

    }
    public ObservableList<Trade> getTrade(){
        ObservableList<Trade> unstartedTrade = FXCollections.observableArrayList();
        unstartedTrade.addAll(tradeManager.getUnstartTrades(currentUserName));
        return unstartedTrade;
//        ObservableList<Trade> demo = FXCollections.observableArrayList();
//
//        Item item1 = new Item("blood", "leo", "suck it");
//        Item item2 = new Item("blood", "leo", "suck it");
//        Item item3 = new Item("blood", "clara", "suck it");
//        Item item4 = new Item("blood", "clara", "suck it");
//        ArrayList<Item> iList = new ArrayList<>();
//        ArrayList<Item> uList = new ArrayList<>();
//        iList.add(item1);
//        iList.add(item2);
//        uList.add(item3);
//        uList.add(item4);
//        LocalDateTime a = LocalDateTime.now();
//        Trade trade1 = new PermTrade("Leo", "clara", iList, uList, a);
//        Trade trade2 = new PermTrade("Leo", "clara", iList, uList, a);
//        Trade trade3 = new PermTrade("Leo", "clara", iList, uList, a);
//        Trade trade4 = new PermTrade("Leo", "clara", iList, uList, a);
//        demo.add(trade1);
//        demo.add(trade2);
//        demo.add(trade3);
//        demo.add(trade4);
//        return demo;
    }


}


package frontend.TradeGUI;

import entities.Item;
import frontend.PopUp.PopUp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import frontend.GlobalInventoryGUI.GlobalInventoryMenuPresenter;
import use_cases.GlobalInventoryManager;
import use_cases.UserManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MultiTradeItemMenu implements Initializable {
    private UserManager allUsers;
    private GlobalInventoryMenuPresenter globalInventoryMenuPresenter= new GlobalInventoryMenuPresenter();
    private String user;
    private GlobalInventoryManager globalInventoryManager;
    private List<Item> items;
    private ObservableList<Item> selectedItems = FXCollections.observableArrayList();
    private ObservableList<Item> userItems = FXCollections.observableArrayList();

    public MultiTradeItemMenu(String user, GlobalInventoryManager globalInventoryManager, UserManager allUsers) {
        this.user = user;
        this.globalInventoryManager = globalInventoryManager;
        this.items = new ArrayList<>();
        this.allUsers = allUsers;
    }

    @FXML TableView<Item> userItem;
    @FXML private TableColumn<Item, String> itemName;
    @FXML private TableColumn<Item,String> itemOwner;
    @FXML private TableColumn<Item, String> itemDescription;

    @FXML private TableColumn<Item, String> tradingitemName;
    @FXML private TableColumn<Item,String> tradingitemOwner;
    @FXML private TableColumn<Item, String> tradingitemDescription;
    @FXML private TableView<Item> tradingItem;
    @FXML private Button select;
    @FXML private Button remove;
    @FXML private Button trade;
    @FXML private Label title;
    @FXML private Label message;
    @FXML private Label userItemLabel;
    @FXML private Label tradingItemLabel;
    @FXML private Button exit;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userItemLabel.setText(user + "'s inventory");
        tradingItemLabel.setText("Items selected");
        title.setText("Please select from your items the items you want to trade");
        itemName.setText(globalInventoryMenuPresenter.itemName());
        itemDescription.setText(globalInventoryMenuPresenter.itemDescription());
        select.setText("Select");
        remove.setText("Remove");
        trade.setText("Trade");
        exit.setText(globalInventoryMenuPresenter.menuPromptExit());

        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));

        tradingitemName.setText(globalInventoryMenuPresenter.itemName());
        tradingitemDescription.setText(globalInventoryMenuPresenter.itemDescription());
        tradingitemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        tradingitemDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));

        userItem.setOnMouseClicked(this::selected);

        exit.setOnAction(this::exit);
        //load data
        loadData();
        userItem.setItems(userItems);
        tradingItem.setItems(getItem());
        select.setOnAction(this::select);
        remove.setOnAction(this::remove);

        trade.setOnAction(e-> {
            try {
                tradeRequest(e);
            } catch (IOException ex) {
                new PopUp("Error");
                ((Stage)((Node) (e.getSource())).getScene().getWindow()).close();
            }
        });
    }


    private void loadData(){
        List<Item> useritemlist =  globalInventoryManager.getPersonInventory(user);
        //useritemlist.remove(useritemlist.indexOf(items.get(0)));
        userItems.addAll(useritemlist);
    }

    public ObservableList<Item> getItem(){
        //selectedItems.add(items.get(0));
        return selectedItems;
    }

    public List<Item> getItems(){
        return selectedItems;
    }

    private void selected(javafx.scene.input.MouseEvent mouseEvent) {
        Item itemselected = userItem.getSelectionModel().getSelectedItem();
        if (itemselected == null){
            message.setText(globalInventoryMenuPresenter.noItemSelected());
        }
        else {
            message.setText(itemselected.getName() +" is selected");
        }
    }

    private void select(ActionEvent event){
        Item itemselected = userItem.getSelectionModel().getSelectedItem();
        if (!(itemselected == null)) {
            userItem.getItems().remove(itemselected);
            tradingItem.getItems().add(itemselected);
        }
    }

    private void remove(ActionEvent event){
        Item itemselected = tradingItem.getSelectionModel().getSelectedItem();
        if (!(itemselected == null)) {
            tradingItem.getItems().remove(itemselected);
            userItem.getItems().add(itemselected);
        }
    }

    private void tradeRequest(ActionEvent event) throws IOException {
        List<Item> items = new ArrayList<Item>();
        for (Item i : selectedItems){
            items.add(i);
        }
        if (selectedItems.size() > 0){
            exit(event);
        }
        else message.setText(globalInventoryMenuPresenter.noItemSelected());

    }

    @FXML
    public void exit(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

}

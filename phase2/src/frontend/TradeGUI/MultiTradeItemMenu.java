package frontend.TradeGUI;

import entities.Item;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import presenters.GlobalInventoryMenuPresenter;
import use_cases.GlobalInventoryManager;
import use_cases.GlobalWishlistManager;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MultiTradeItemMenu implements Initializable {
    private GlobalWishlistManager globalWishlistManager;
    private UserManager allUsers;
    private GlobalInventoryMenuPresenter globalInventoryMenuPresenter= new GlobalInventoryMenuPresenter();
    private String user;
    private TradeManager tradeManager;
    private GlobalInventoryManager globalInventoryManager;
    private List<Item> items;
    private ObservableList<Item> selectedItems = FXCollections.observableArrayList();
    private ObservableList<Item> userItems = FXCollections.observableArrayList();

    public MultiTradeItemMenu(List<Item> items, String user, GlobalInventoryManager globalInventoryManager, GlobalWishlistManager globalWishlistManager, UserManager allUsers) {
        this.user = user;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
        this.items = items;
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
        userItemLabel.setText(items.get(0).getOwnerName() + "'s inventory");
        tradingItemLabel.setText("Items selected");
        title.setText("You are Trading with " + items.get(0).getOwnerName() + "\n please select the items you want to trade");
        itemName.setText(globalInventoryMenuPresenter.itemName());
        itemOwner.setText(globalInventoryMenuPresenter.itemOwner());
        itemDescription.setText(globalInventoryMenuPresenter.itemDescription());
        select.setText("Select");
        remove.setText("Remove");
        trade.setText("Trade");
        exit.setText(globalInventoryMenuPresenter.menuPromptExit());

        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemOwner.setCellValueFactory(new PropertyValueFactory<Item, String>("ownerName"));
        itemDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));

        tradingitemName.setText(globalInventoryMenuPresenter.itemName());
        tradingitemOwner.setText(globalInventoryMenuPresenter.itemOwner());
        tradingitemDescription.setText(globalInventoryMenuPresenter.itemDescription());
        tradingitemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        tradingitemOwner.setCellValueFactory(new PropertyValueFactory<Item, String>("ownerName"));
        tradingitemDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));

        userItem.setOnMouseClicked(this::selected);

        exit.setOnAction(this::exit);
        //load data
        loadData();
        userItem.setItems(userItems);
        System.out.println("hi");
        tradingItem.setItems(getItem());
        select.setOnAction(this::select);
        remove.setOnAction(this::remove);

        trade.setOnAction(e-> {
            try {
                tradeRequest(e);
            } catch (IOException ex) {
                System.out.println("Frozen account");
            }
        });
    }


    private void loadData(){
        List<Item> useritemlist =  globalInventoryManager.getPersonInventory(items.get(0).getOwnerName());
        useritemlist.remove(useritemlist.indexOf(items.get(0)));
        userItems.addAll(useritemlist);
    }

    public ObservableList<Item> getItem(){
        selectedItems.add(items.get(0));
        return selectedItems;
    }

    public void selected(javafx.scene.input.MouseEvent mouseEvent) {
        Item itemselected = userItem.getSelectionModel().getSelectedItem();
        if (itemselected == null){
            message.setText(globalInventoryMenuPresenter.noItemSelected());
        }
        else {
            message.setText(itemselected.getName() +" is selected");
        }
    }

    public void select(ActionEvent event){
        Item itemselected = userItem.getSelectionModel().getSelectedItem();
        if (!(itemselected == null)) {
            userItem.getItems().remove(itemselected);
            tradingItem.getItems().add(itemselected);
        }
    }

    public void remove(ActionEvent event){
        Item itemselected = tradingItem.getSelectionModel().getSelectedItem();
        if (!(itemselected == null)) {
            tradingItem.getItems().remove(itemselected);
            userItem.getItems().add(itemselected);
        }
    }

    public void tradeRequest(ActionEvent event) throws IOException {
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

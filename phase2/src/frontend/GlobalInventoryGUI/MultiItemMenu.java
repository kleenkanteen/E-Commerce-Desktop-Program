package frontend.GlobalInventoryGUI;

import entities.Item;
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
import presenters.GlobalInventoryMenuPresenter;
import use_cases.GlobalInventoryManager;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MultiItemMenu implements Initializable {
    private GlobalInventoryMenuPresenter globalInventoryMenuPresenter= new GlobalInventoryMenuPresenter();
    private String user;
    private TradeManager tradeManager;
    private GlobalInventoryManager globalInventoryManager;
    private Item item;
    private ObservableList<Item> selectedItems = FXCollections.observableArrayList();
    private ObservableList<Item> userItems = FXCollections.observableArrayList();

    public MultiItemMenu(Item item, String user, GlobalInventoryManager globalInventoryManager, UserManager userManager,
                         TradeManager tradeManager) {
        this.user = user;
        this.globalInventoryManager = globalInventoryManager;
        this.item = item;
    }

    public MultiItemMenu(String user, GlobalInventoryManager globalInventoryManager) {
        this.user = user;
        this.globalInventoryManager = globalInventoryManager;
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
        userItemLabel.setText(item.getOwnerName() + "'s inventory");
        tradingItemLabel.setText("Items selected");
        title.setText("You are Trading with " + item.getOwnerName() + "\n please select the items you want to trade");
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
        List<Item> useritemlist =  globalInventoryManager.getPersonInventory(item.getOwnerName());
        useritemlist.remove(useritemlist.indexOf(item));
        userItems.addAll(useritemlist);
    }

    private ObservableList<Item> getItem(){
        selectedItems.add(item);
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
            try{
                String trademenuFXML = "TradeMenu.fxml";
                switchScene(trademenuFXML, items);
            }
            catch (IOException ex) {
                //error
            }
        }
        else message.setText(globalInventoryMenuPresenter.noItemSelected());

    }

    public void switchScene(String filename, List<Item> items) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
//        loader.setController(new TradeMenuMainController(items, user));// call trade
//        Parent root = loader.load();
//        Scene newScene= new Scene(root);
//        Stage window = new Stage();
//        window.initModality(Modality.APPLICATION_MODAL);
//        window.setScene(newScene);
//        window.show();
    }

    @FXML
    public void exit(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

//
////    public List<Item> getSelectedItems() throws IncompleteTradeException {
////        List<Item> items = new ArrayList<Item>();
////        for (Item i : selectedItems){
////            items.add(i);
////        }
////        try{
////            if (items.size()> 0){
////                return items;
////            }
////            else
////        }
//    }

}

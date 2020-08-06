package frontend.GlobalInventoryGUI;

import entities.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @FXML
    TableView<Item> userItem;
    @FXML private TableColumn<Item, String> itemName;
    @FXML private TableColumn<Item,String> itemOwner;
    @FXML private TableColumn<Item, String> itemDescription;
    @FXML private TableColumn<Item, String> tradingitemName;
    @FXML private TableColumn<Item,String> tradingitemOwner;
    @FXML private TableColumn<Item, String> tradingitemDescription;
    @FXML TableView<Item> tradingItem;
    @FXML Button select;
    @FXML Button remove;
    @FXML Button trade;
    @FXML Label title;
    @FXML Label message;



    private GlobalInventoryMenuPresenter globalInventoryMenuPresenter= new GlobalInventoryMenuPresenter();
    private UserManager userManager;
    private String user;
    private TradeManager tradeManager;
    private String TrademenuFXML = "TradeMenu.fxml";
    private GlobalInventoryManager globalInventoryManager;
    private Item item;
    private ObservableList<Item> selectedItems = FXCollections.observableArrayList();;


    public MultiItemMenu(Item item, String user, GlobalInventoryManager globalInventoryManager, UserManager userManager,
                         TradeManager tradeManager) {
        this.globalInventoryManager = globalInventoryManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.item = item;
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemName.setText(globalInventoryMenuPresenter.itemName());
        itemOwner.setText(globalInventoryMenuPresenter.itemOwner());
        itemDescription.setText(globalInventoryMenuPresenter.itemDescription());
        select.setText("Select");
        remove.setText("Remove");
        trade.setText("Trade");

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
        //load data
        userItem.setItems(loadData());
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


    private ObservableList<Item> loadData(){
        ObservableList<Item> items = FXCollections.observableArrayList();
        List<Item> userItems =  globalInventoryManager.getPersonInventory(user);
        for (Item i : userItems){
            items.add(i);
        }
        return items;
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
            message.setText(itemselected.getName() +"is selected");
        }
    }

    public void select(ActionEvent event){
        Item itemselected = userItem.getSelectionModel().getSelectedItem();
        selectedItems.add(itemselected);
        tradingItem.setItems(selectedItems);
    }

    public void remove(ActionEvent event){
        Item itemselected = tradingItem.getSelectionModel().getSelectedItem();
        selectedItems.remove(itemselected);
        tradingItem.setItems(selectedItems);
    }

    public void tradeRequest(ActionEvent event) throws IOException {
        List<Item> items = new ArrayList<Item>();
        for (Item i : selectedItems){
            items.add(i);
        }
        try{
            switchScene(TrademenuFXML, items);
        }
        catch (IOException ex) {
            //error
        }

    }

    public void switchScene(String filename, List<Item> items) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
//        loader.setController(new TradeMenuMainController());// call trade
//        Parent root = loader.load();
//        Scene newScene= new Scene(root);
//        Stage window = new Stage();
//        window.initModality(Modality.APPLICATION_MODAL);
//        window.setScene(newScene);
//        window.show();
    }

}

package frontend.GlobalInventoryGUI;

import entities.Item;
import frontend.PopUp.PopUp;
import frontend.TradeGUI.TradeMenuMainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import use_cases.GlobalInventoryManager;
import use_cases.GlobalWishlistManager;
import use_cases.UserManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MultiItemMenu implements Initializable {
    private GlobalInventoryMenuPresenter globalInventoryMenuPresenter= new GlobalInventoryMenuPresenter();
    private String user;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;
    private UserManager userManager;
    private Item item;
    private ObservableList<Item> selectedItems = FXCollections.observableArrayList();
    private ObservableList<Item> userItems = FXCollections.observableArrayList();

    /**
     * constructor for MultiItemMenu
     * @param item item user selected in globalInventoryMenu
     * @param user user name of current user
     * @param globalInventoryManager globalInventoryManager object
     * @param userManager userManager Object
     * @param globalWishlistManager globalWishlistManager object
     */
    public MultiItemMenu(Item item, String user, GlobalInventoryManager globalInventoryManager, UserManager userManager,
                         GlobalWishlistManager globalWishlistManager) {
        this.user = user;
        this.globalInventoryManager = globalInventoryManager;
        this.item = item;
        this.userManager = userManager;
        this.globalWishlistManager = globalWishlistManager;
    }


    @FXML TableView<Item> userItem;
    @FXML private TableColumn<Item, String> itemName;
    @FXML private TableColumn<Item, String> itemDescription;
    @FXML private TableColumn<Item, String> tradingitemName;
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


    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userItemLabel.setText(globalInventoryMenuPresenter.userItemLabel(item));
        tradingItemLabel.setText(globalInventoryMenuPresenter.itemSelected());
        title.setText(globalInventoryMenuPresenter.selectItem(item));
        itemName.setText(globalInventoryMenuPresenter.itemName());

        itemDescription.setText(globalInventoryMenuPresenter.itemDescription());
        select.setText(globalInventoryMenuPresenter.select());
        remove.setText(globalInventoryMenuPresenter.remove());
        trade.setText(globalInventoryMenuPresenter.trade());
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
                new PopUp(globalInventoryMenuPresenter.frozenAcc());
            }
        });
    }

    /**
     * load the data from the globalInventory to TableView
     */
    private void loadData(){
        List<Item> useritemlist =  globalInventoryManager.getPersonInventory(item.getOwnerName());
        useritemlist.remove(useritemlist.indexOf(item));
        userItems.addAll(useritemlist);
    }

    /**
     * put the item user selected in global inventory into trading Item
     * @return a ObservableList of item user selected
     */
    private ObservableList<Item> getItem(){
        selectedItems.add(item);
        return selectedItems;
    }

    /**
     * check if user selected the item on screen
     * @param mouseEvent mouse click
     */
    private void selected(javafx.scene.input.MouseEvent mouseEvent) {
        Item itemselected = userItem.getSelectionModel().getSelectedItem();
        if (itemselected == null){
            message.setText(globalInventoryMenuPresenter.noItemSelected());
        }
        else {
            message.setText(globalInventoryMenuPresenter.itemSelected(itemselected));
        }
    }

    /**
     * select item in the userItem TableView to tradingItem TableView
     * @param event mouse click
     */
    private void select(ActionEvent event){
        Item itemselected = userItem.getSelectionModel().getSelectedItem();
        if (!(itemselected == null)) {
            userItem.getItems().remove(itemselected);
            tradingItem.getItems().add(itemselected);
        }
    }

    /**
     * remove item in the tradingItem TableView to userItem TableView
     * @param event mouse click
     */
    private void remove(ActionEvent event){
        Item itemselected = tradingItem.getSelectionModel().getSelectedItem();
        if (!(itemselected == null)) {
            tradingItem.getItems().remove(itemselected);
            userItem.getItems().add(itemselected);
        }
    }

    /**
     * switch scene to tradeMenu when user click on trade button
     * @param event mouse click
     * @throws IOException something went wrong
     */
    private void tradeRequest(ActionEvent event) throws IOException {
        List<Item> items = new ArrayList<Item>();
        for (Item i : selectedItems){
            items.add(i);
        }
        if (selectedItems.size() > 0){
            try{
                String trademenuFXML = "/frontend/TradeGUI/TradeMenu.fxml";
                switchScene(trademenuFXML, items, event);
            }
            catch (IOException ex) {
                new PopUp(globalInventoryMenuPresenter.error());
            }
        }
        else message.setText(globalInventoryMenuPresenter.noItemSelected());

    }

    /**
     * to switch to MultiItemMenu when user clicked on trade button
     * @param filename file name of the MultiItemMenu FXML file
     * @param items the item user selected
     * @param e close window
     * @throws IOException something went wrong
     */
    private void switchScene(String filename, List<Item> items, ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        loader.setController(new TradeMenuMainController(globalInventoryManager, globalWishlistManager, userManager, items, user));// call tradeParent root = loader.load();
        Scene newScene= new Scene(loader.load());
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(newScene);
        window.showAndWait();
        ((Stage)((Node) e.getSource()).getScene().getWindow()).close();
    }

    /**
     * Exit the global inventory menu
     * @param event mouse click on Exit button
     */
    @FXML
    private void exit(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }



}

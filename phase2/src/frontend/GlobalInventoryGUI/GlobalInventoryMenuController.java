package frontend.GlobalInventoryGUI;

import entities.GlobalWishlist;
import entities.Item;
import exceptions.UserFrozenException;
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
import use_cases.GlobalInventoryManager;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GlobalInventoryMenuController implements Initializable {


    private GlobalInventoryManager globalInventoryManager;
    private String DemoUserTradeMenuFXML = "MultiItemMenu.fxml";
    private GlobalInventoryMenuPresenter globalInventoryMenuPresenter= new GlobalInventoryMenuPresenter();
    private UserManager userManager;
    private String user;
    private TradeManager tradeManager;
    private GlobalWishlist globalWishlist;
    private String MultiItemMenuFXML = "MultiItemMenu.fxml";

    public GlobalInventoryMenuController(GlobalInventoryManager globalInventoryManager, UserManager userManager,
                                         TradeManager tradeManager, GlobalWishlist globalWishlist) {
        this.globalInventoryManager = globalInventoryManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalWishlist = globalWishlist;
    }

    @FXML private TableView<Item> tableView;
    @FXML private TableColumn<Item, String> itemName;
    @FXML private TableColumn<Item,String> itemOwner;
    @FXML private TableColumn<Item, String> itemDescription;
    @FXML private Button addToWishlist;
    @FXML private Button trade;
    @FXML private Button exit;
    @FXML private Label message;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemName.setText(globalInventoryMenuPresenter.itemName());
        itemOwner.setText(globalInventoryMenuPresenter.itemOwner());
        itemDescription.setText(globalInventoryMenuPresenter.itemDescription());
        addToWishlist.setText(globalInventoryMenuPresenter.addToWishlist());
        trade.setText(globalInventoryMenuPresenter.sendTradeReqeust());
        exit.setText(globalInventoryMenuPresenter.menuPromptExit());

        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemOwner.setCellValueFactory(new PropertyValueFactory<Item, String>("ownerName"));
        itemDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));

        tableView.setOnMouseClicked(e -> selected(e));
        //load data
        tableView.setItems(getItem());
        addToWishlist.setOnAction(e -> addToWishlist(e));
        exit.setOnAction(e -> exit(e));
        trade.setOnAction(e-> {
            try {
                MultiItemMenu(e);
            } catch (UserFrozenException ex) {
                System.out.println("Frozen account");
            }
        });
    }

    private ObservableList<Item> loadData(){
        ObservableList<Item> items = FXCollections.observableArrayList();
        List<String> itemids =  globalInventoryManager.getGlobalInventoryData().getItemIdCollection();
        for (Item i : globalInventoryManager.getItemsFromGI((ArrayList<String>)itemids)){
            items.add(i);
        }
        return items;
    }

    public void selected(javafx.scene.input.MouseEvent mouseEvent) {
        Item itemselected = tableView.getSelectionModel().getSelectedItem();
        if (itemselected == null){
            message.setText(globalInventoryMenuPresenter.noItemSelected());
        }
        else {
            message.setText(globalInventoryMenuPresenter.whatToDo(itemselected));
        }
    }

    public void switchScene(String filename, Item item) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        loader.setController(new MultiItemMenu(item, user, globalInventoryManager, userManager, tradeManager));// call Multimenu
        Parent root = loader.load();
        Scene newScene= new Scene(root);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(newScene);
        window.show();
    }

    private ObservableList<Item> getItem(){
        ObservableList<Item> items = FXCollections.observableArrayList();
        // for demonstration purpose
//        Item itema = new Item("pen", "Jerry", "a pen");
//        Item itemb = new Item("desk", "Jerry", "a desk");
//        items.addAll(itema, itemb);
         //using GlobalinventorManager
        List<String> itemids =  globalInventoryManager.getGlobalInventoryData().getItemIdCollection();
        for (Item i : globalInventoryManager.getItemsFromGI((ArrayList<String>)itemids)){
            items.add(i);
        }
        return items;
    }

    public void addToWishlist(ActionEvent event){
        Item itemselected = tableView.getSelectionModel().getSelectedItem();
        if (itemselected == null) {
            message.setText(globalInventoryMenuPresenter.noItemSelected());
        }
        else if (globalWishlist.getPersonWishlist(user).contains(itemselected)){
            message.setText(globalInventoryMenuPresenter.alreadyHave());
        }
        else {
            message.setText(globalInventoryMenuPresenter.addedToWishlist(itemselected));
            globalWishlist.addWish(itemselected.getItemID(), user);
        }


    }

    public void MultiItemMenu(ActionEvent event) throws UserFrozenException {
        Item itemselected = tableView.getSelectionModel().getSelectedItem();
        if (itemselected == null) {
            message.setText(globalInventoryMenuPresenter.noItemSelected());
        } else if (globalInventoryManager.contains(itemselected)){
            message.setText(globalInventoryMenuPresenter.ownItem());
        }
        else {
            if (userManager.getCanTrade(user, tradeManager.getBorrowedTimes(user),
                    tradeManager.getLendTimes(user), tradeManager.getIncompleteTimes(user),
                    tradeManager.numberOfTradesCreatedThisWeek(user))){
                try {
                    switchScene(MultiItemMenuFXML, itemselected);
                }
                catch (IOException ex) {
                    //error
                }
            }
            else message.setText(globalInventoryMenuPresenter.FrozenAcc());
        }
    }

    @FXML
    public void exit(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

}

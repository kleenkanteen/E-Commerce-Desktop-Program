package frontend.DemoUserGUI;

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
import javafx.stage.Stage;
import use_cases.DemoUserManager;
import use_cases.GlobalInventoryManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DemoUserGlobalInventoryMenu implements Initializable {

    GlobalInventoryManager globalInventoryManager;
    DemoUserManager demoUserManager;

    @FXML private TableView<Item> tableView;
    @FXML private TableColumn<Item, String> itemName;
    @FXML private TableColumn<Item,String> itemOwner;
    @FXML private TableColumn<Item, String> itemDescription;
    @FXML private Button addToWishlist;
    @FXML private Button trade;
    @FXML private Button exit;
    @FXML private Label message;

    // for testing
    public DemoUserGlobalInventoryMenu() {
    }

    public DemoUserGlobalInventoryMenu(String username, String password, GlobalInventoryManager globalInventoryManager) {
        this.demoUserManager = new DemoUserManager(username, password);
        this.globalInventoryManager = globalInventoryManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemName.setText("Item Name");
        itemOwner.setText("Item Owner");
        itemDescription.setText("Item Description");
        addToWishlist.setText("Add to wish-list");
        trade.setText("Send a trade request");
        exit.setText("exit");

        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemOwner.setCellValueFactory(new PropertyValueFactory<Item, String>("ownerName"));
        itemDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));

        //load data
        tableView.setItems(getItem());
//        addToWishlist.setOnAction();
        exit.setOnAction(e -> exit(e));

        //TODO set action for addToWishList buttom and send a trade reqeust buttom
        //TODO add "please select a item label, and separate the presenter
    }
    public void switchScene(String filename) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        loader.setController(new Object());// call trademenu
        Parent root = loader.load();
        Scene newScene= new Scene(root);
        Stage window = new Stage();
        window.setScene(newScene);
        window.show();
    }


    private ObservableList<Item> getItem(){
        ObservableList<Item> items = FXCollections.observableArrayList();
        // for demonstration purpose
        Item itema = new Item("pen", "Jerry", "a pen");
        Item itemb = new Item("desk", "Jerry", "a desk");
        items.addAll(itema, itemb);
        // using GlobalinventorManager
//        List<String> itemids =  globalInventoryManager.getGlobalInventoryData().getItemIdCollection();
//        for (Item i : globalInventoryManager.getItemsFromGI((ArrayList<String>)itemids)){
//            items.add(i);
//        }
        return items;
    }

    public void selected(javafx.scene.input.MouseEvent mouseEvent) {
        Item itemselected = tableView.getSelectionModel().getSelectedItem();
        if (itemselected == null){
            message.setText("No item selected");
        }
        else {
            message.setText(itemselected.getName() + " is selected! \nWhat do you want to do with this item?");
        }
    }

    @FXML
    public void exit(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }





}
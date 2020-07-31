package frontend;

import entities.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GlobalInventoryMenuController implements Initializable {
    ObservableList list = FXCollections.observableArrayList();
    @FXML private ListView<Item> item;
    @FXML private TextField screen;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    private void loadData(){
        list.removeAll(list);
        Item itema = new Item("pen", "Jerry", "a pen");
        Item itemb = new Item("desk", "Jerry", "a desk");
        Item c = new Item("desk", "Jerry", "a desk");
        Item d = new Item("desk", "Jerry", "a desk");
        Item e = new Item("desk", "Jerry", "a desk");
        Item f = new Item("desk", "Jerry", "a desk");
        Item g = new Item("desk", "Jerry", "a desk");

        list.addAll(itema, itemb,c,d,e,f,g);
        item.getItems().addAll(list);
    }

    public void selected(javafx.scene.input.MouseEvent mouseEvent) {
        Item itemselected = item.getSelectionModel().getSelectedItem();
        if (itemselected == null){
            screen.setText("No item selected");
        }
        else {
            screen.setText(itemselected.getName() + " is selected! \nWhat do you want to do with this item?");
        }
    }
}

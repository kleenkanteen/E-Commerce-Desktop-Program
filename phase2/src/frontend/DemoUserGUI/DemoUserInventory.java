package frontend.DemoUserGUI;

import entities.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import use_cases.DemoUserManager;

import java.net.URL;
import java.util.ResourceBundle;

public class DemoUserInventory implements Initializable {
    private DemoUserfxPresenter demoUserPresenter = new DemoUserfxPresenter();
    private DemoUserManager demoUserManager;
    private Item selectedItem = null;


    @FXML private Label systemMessage;
    @FXML private Button remove;
    @FXML private Button exit;
    @FXML private ListView<Item> itemList;


    public DemoUserInventory(DemoUserManager demoUserManager) {
        this.demoUserManager = demoUserManager;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set up exit button
        this.exit.setText(this.demoUserPresenter.menuPromptExit());
        this.exit.setOnAction(this::exit);
        remove.setText(demoUserPresenter.menuPromptRemove());
        this.remove.setOnAction(e -> remove());
        // set up list view
        this.itemList.getItems().addAll(demoUserManager.getUserInventory());
        this.itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                this.selectedItem = newValue);

    }


    public void remove(){
        // if there are no items left
        if(this.itemList.getItems().size() == 0) {
            this.systemMessage.setText(demoUserPresenter.inventoryIsEmpty());
        }
        // if there is no item selected
        else if(this.selectedItem == null) {
            this.systemMessage.setText(demoUserPresenter.noItemSelected());
        }
        else {
            demoUserManager.getUserInventory().remove(selectedItem);
        }
        this.itemList.getItems().remove(this.selectedItem);
        this.selectedItem = null;
        }


    public void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}

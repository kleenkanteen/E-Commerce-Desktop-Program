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

public class DemoUserWishlist implements Initializable {

    private DemoUserfxPresenter demoUserPresenter = new DemoUserfxPresenter();
    private DemoUserManager demoUserManager;
    private Item selectedItem;

    @FXML private Label systemMessage;
    @FXML private ListView<Item> itemList;
    @FXML private Button remove;
    @FXML private Button exit;
    @FXML private Button sendTradeRequest;



    public DemoUserWishlist(DemoUserManager demoUserManager) {
        this.demoUserManager = demoUserManager;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set up exit button
        this.exit.setText(this.demoUserPresenter.menuPromptExit());
        this.exit.setOnAction(this::exit);
        this.remove.setText(this.demoUserPresenter.menuPromptRemove());
        this.remove.setOnAction(e -> remove());
        this.sendTradeRequest.setText(this.demoUserPresenter.sendTradeReqeust());
        this.sendTradeRequest.setOnAction(e -> sendTradeRequest());

        // set up list view
        this.itemList.getItems().addAll(demoUserManager.getUserWishlist());
        this.itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                this.selectedItem = newValue);

    }

    public void remove(){
        // if there are no items left
        if(this.itemList.getItems().size() == 0) {
            this.systemMessage.setText(demoUserPresenter.wishlistIsEmpty());
        }
        // if there is no item selected
        else if(this.selectedItem == null) {
            this.systemMessage.setText(demoUserPresenter.noItemSelected());
        }
        else {
            demoUserManager.getUserWishlist().remove(selectedItem);
            systemMessage.setText(demoUserPresenter.itemRemoved());
            }
            this.itemList.getItems().remove(this.selectedItem);
            this.selectedItem = null;
        }


    public void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }

    public void sendTradeRequest(){
        systemMessage.setText(demoUserPresenter.noAccess());
    }
}

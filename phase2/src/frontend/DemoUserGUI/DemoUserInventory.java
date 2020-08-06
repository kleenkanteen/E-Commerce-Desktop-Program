package frontend.DemoUserGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import use_cases.DemoUserManager;

import java.net.URL;
import java.util.ResourceBundle;

public class DemoUserInventory implements Initializable {
    private DemoUserfxPresenter demoUserPresenter = new DemoUserfxPresenter();
    private DemoUserManager demoUserManager;
    private int index;



    @FXML private Label viewItem;
    @FXML private Label systemMessage;
    @FXML private Label unconfirmedTradesPrompt;
    @FXML private Label allDonePrompt;
    @FXML private Button next;
    @FXML private Button previous;
    @FXML private Button remove;
    @FXML private Button confirm;
    @FXML private Button deny;
    @FXML private Button exit;


    public DemoUserInventory() {
    }

    public DemoUserInventory(DemoUserManager demoUserManager) {
        this.demoUserManager = demoUserManager;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set up exit button
        this.exit.setText(this.demoUserPresenter.menuPromptExit());
        this.exit.setOnAction(this::exit);
        this.next.setText(this.demoUserPresenter.menuPromptNext());
        this.previous.setText(this.demoUserPresenter.menuPromptPrevious());
        this.remove.setText(this.demoUserPresenter.menuPromptRemove());

        this.previous.setOnAction(e -> previous());
        this.next.setOnAction(e -> next());
        this.remove.setOnAction(e -> remove());
    }

    public void previous (){
        if(this.index == 0) {
            this.systemMessage.setText(demoUserPresenter.endOfUserInventory());
        }
        else{
            this.index--;
        }
        itemToString();
    }

    public void next(){
        if(this.index == demoUserManager.getUserInventory().size() - 1) {
            this.systemMessage.setText(demoUserPresenter.endOfUserInventory());
        }
        else{
            this.index++;
        }
        itemToString();
    }
    public void remove(){
        demoUserManager.removeFromInventory(demoUserManager.getUserInventory().get(index));
        viewItem.setText(demoUserPresenter.itemRemoved());
    }

    public void itemToString() {
        if(demoUserManager.getUserInventory().size() != 0) {
            this.viewItem.setText(demoUserManager.getUserInventory().get(index).toString());
        }
        else {
            this.viewItem.setText(demoUserPresenter.emptyinventory());
        }
    }

    public void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}

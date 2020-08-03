package frontend.UserGUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import presenters.DemoUserPresenter;
import use_cases.DemoUserManager;
import use_cases.GlobalInventoryManager;

import java.net.URL;
import java.util.ResourceBundle;

public class DemoUserMenuGUI  implements Initializable {
    private DemoUserManager demoUserManager;
    private DemoUserPresenter demoUserPresenter;
    private GlobalInventoryManager globalInventoryManager;

    @FXML private Label systemMessage;
    @FXML private Button accountInfo;
    @FXML private Button globalInventory;
    @FXML private Button loanItem;
    @FXML private Button messageInbox;
    @FXML private Button newItem;
    @FXML private Button unfreezeRequest;
    @FXML private Button privateMessage;
    @FXML private Button logout;

    //TODO: use presenter to set the text on screen

    public DemoUserMenuGUI(DemoUserManager demoUserManager, DemoUserPresenter demoUserPresenter,
                           GlobalInventoryManager globalInventoryManager) {
        this.demoUserManager = demoUserManager;
        this.demoUserPresenter = demoUserPresenter;
        this.globalInventoryManager = globalInventoryManager;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountInfo.setText(demoUserPresenter.accInfo());
        globalInventory.setText(demoUserPresenter.browse());
        loanItem.setText(demoUserPresenter.loan());
        messageInbox.setText(demoUserPresenter.inbox());
        newItem.setText(demoUserPresenter.addItem());
        unfreezeRequest.setText(demoUserPresenter.unfreeze());
        privateMessage.setText(demoUserPresenter.pm());
        logout.setText(demoUserPresenter.exit());
    }

    // switch to the Account info menu scene
    @FXML
    public void getAccountInfo() { }

    // switch to the GlobalInventory scene
    @FXML
    public void getGlobalInventory() {
        if(!this.globalInventoryManager.hasNoItem()) {

        }
        else {
            this.systemMessage.setText(this.demoUserPresenter.emptyglobalinventory());
            // globalinventory no item
        }
    }

    // switch to the loan menu scene
    @FXML
    public void getLoanMenu() {
        systemMessage.setText(demoUserPresenter.noAccess());
    }

    // switch to the user message system scene
    @FXML
    public void getInbox() {
        systemMessage.setText(demoUserPresenter.noAccess());
    }


    // switch to the new item scene
    @FXML
    public void getNewItemMenu() {

    }

    @FXML
    public void getUnfreezeRequest() {
        systemMessage.setText(demoUserPresenter.noAccess());
    }

    // switch to the private message sending scene
    @FXML
    public void getPrivateMessageMenu() {
        systemMessage.setText(demoUserPresenter.noAccess());
    }

    @FXML
    public void exit() {
        // exit the menu
    }
}


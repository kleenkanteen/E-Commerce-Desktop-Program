package frontend.DemoUserGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import use_cases.DemoUserManager;
import use_cases.GlobalInventoryManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DemoUserMenuGUI  implements Initializable {
    private DemoUserManager demoUserManager;
    private DemoUserfxPresenter demoUserPresenter = new DemoUserfxPresenter();
    private GlobalInventoryManager globalInventoryManager;
    private Type type;

    private final String accountFXML = "DemoAccountMenu.fxml";
    private final String newItemFXML = "DemoAddItemMenu.fxml";
    private final String globalInventoryFXML = "DemoGlobalInventoryMenu.fxml";

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

    public DemoUserMenuGUI(GlobalInventoryManager globalInventoryManager) {
        this.demoUserManager = new DemoUserManager("demo", "demo");
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

        accountInfo.setOnAction(e -> getAccountInfo(e));
        globalInventory.setOnAction(e-> getGlobalInventory(e));
        loanItem.setOnAction(e -> getLoanMenu(e));
        messageInbox.setOnAction(e -> getInbox(e));
        newItem.setOnAction(e -> getNewItemMenu(e));
        unfreezeRequest.setOnAction(e -> getUnfreezeRequest(e));
        privateMessage.setOnAction(e -> getPrivateMessageMenu(e));
        logout.setOnAction(e -> exit(e));

    }

    // switch to the Account info menu scene
    @FXML
    public void getAccountInfo(ActionEvent event) {
        try {
            this.type = Type.ACCOUNT_INFO;
            switchScene(this.accountFXML);
        }
        catch(IOException ex) {
            // some kind of error message
        }
    }

    // switch to the GlobalInventory scene
    @FXML
    public void getGlobalInventory(ActionEvent event) {
        //test
        try {
            this.type = Type.GLOBAL_INVENTORY;
            switchScene(this.globalInventoryFXML);
        }
        catch(IOException ex) {
            // some kind of error message
        }

//        if(!this.globalInventoryManager.hasNoItem()) {
//            try {
//                this.type = Type.GLOBAL_INVENTORY;
//                switchScene(this.globalInventoryFXML);
//            }
//            catch(IOException ex) {
//                // some kind of error message
//            }
//        }
//        else {
//            this.systemMessage.setText(this.demoUserPresenter.emptyglobalinventory());
//        }
    }

    // switch to the loan menu scene
    @FXML
    public void getLoanMenu(ActionEvent event) {
        systemMessage.setText(demoUserPresenter.noAccess());
    }

    // switch to the user message system scene
    @FXML
    public void getInbox(ActionEvent event) {
        systemMessage.setText(demoUserPresenter.noAccess());
    }


    // switch to the new item scene
    @FXML
    public void getNewItemMenu(ActionEvent event) {
        try {
            this.type = Type.NEW_ITEM;
            switchScene(this.newItemFXML);
        }
        catch(IOException ex) {
            // some kind of error message
        }
    }

    @FXML
    public void getUnfreezeRequest(ActionEvent event) {
        systemMessage.setText(demoUserPresenter.noAccess());
    }

    // switch to the private message sending scene
    @FXML
    public void getPrivateMessageMenu(ActionEvent event) {
        systemMessage.setText(demoUserPresenter.noAccess());
    }

    @FXML
    public void exit(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    enum Type {
        ACCOUNT_INFO, GLOBAL_INVENTORY, USER_MESSAGES, LOAN_MENU, NEW_ITEM, PRIVATE_MESSAGES
    }

    /**
     * Switches the scene being viewed
     * @param filename the filename of the .fxml file to be loaded
     * @throws IOException for a funky input
     */
    public void switchScene(String filename) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        // access account info
        switch (this.type) {
            case ACCOUNT_INFO:
                loader.setController(new DemoUserInfoMenu(demoUserManager));
                break;
            // access global inventory
            case GLOBAL_INVENTORY:
                loader.setController(new DemoUserGlobalInventoryMenu(demoUserManager, globalInventoryManager));
                break;

            // access new item menu
            case NEW_ITEM:
                loader.setController(new DemoUserAddItemMenu(demoUserManager));
                break;

        }
        Parent root = loader.load();
        Scene newScene= new Scene(root);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(newScene);
        window.show();
    }
//
//    public void goToOtherScene(ActionEvent actionEvent, String otherScene, SelectedOption selectedOption) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(otherScene));
//
//        loader.setController(new LoginController(selectedOption.name(), userManager, tradeManager, adminManager,
//                globalInventoryManager, globalWishlistManager));
//
//        Parent parent = loader.load();
//        Scene scene = new Scene(parent);
//
//        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
//
//        window.setScene(scene);
//        window.show();
//    }
}


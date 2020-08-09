package frontend.DemoUserGUI;

import frontend.PopUp.PopUp;
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
    private final String globalInventoryFXML = "/frontend/GlobalInventoryGUI/GlobalInventoryMenu.fxml";

    @FXML private Label systemMessage;
    @FXML private Button accountInfo;
    @FXML private Button globalInventory;
    @FXML private Button loanItem;
    @FXML private Button messageInbox;
    @FXML private Button newItem;
    @FXML private Button unfreezeRequest;
    @FXML private Button privateMessage;
    @FXML private Button logout;

    /**
     * constructor for DemoUserMenuGUI
     * @param globalInventoryManager a globalInventoryManager
     */
    public DemoUserMenuGUI(GlobalInventoryManager globalInventoryManager) {
        this.demoUserManager = new DemoUserManager("demo", "demo");
        this.globalInventoryManager = globalInventoryManager;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
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

        accountInfo.setOnAction(this::getAccountInfo);
        globalInventory.setOnAction(this::getGlobalInventory);
        loanItem.setOnAction(this::getLoanMenu);
        messageInbox.setOnAction(this::getInbox);
        newItem.setOnAction(this::getNewItemMenu);
        unfreezeRequest.setOnAction(this::getUnfreezeRequest);
        privateMessage.setOnAction(this::getPrivateMessageMenu);
        logout.setOnAction(this::exit);

    }

    // switch to the Account info menu scene

    /**
     * switch to accountInfo menu
     * @param event mouse click
     */
    @FXML
    private void getAccountInfo(ActionEvent event) {
        try {
            this.type = Type.ACCOUNT_INFO;
            switchScene(this.accountFXML);
        }
        catch(IOException ex) {
            new PopUp(demoUserPresenter.error());
        }
    }

    /**
     * switch to globalInventoryMenu
     * @param event mouse click
     */
    @FXML
    private void getGlobalInventory(ActionEvent event) {
        //test
        try {
            this.type = Type.GLOBAL_INVENTORY;
            switchScene(this.globalInventoryFXML);
        }
        catch(IOException ex) {
            new PopUp(demoUserPresenter.error());
        }

    }

    // switch to the loan menu scene

    /**
     * switch to loanMenu, but for demo user it prints no access on screen
     * @param event mouse click
     */
    @FXML
    private void getLoanMenu(ActionEvent event) {
        systemMessage.setText(demoUserPresenter.noAccess());
    }

    /**
     * switch to user message inbox, but for demo user it prints no access on screen
     * @param event mouse click
     */
    @FXML
    private void getInbox(ActionEvent event) {
        systemMessage.setText(demoUserPresenter.noAccess());
    }


    // switch to the new item scene
    @FXML
    private void getNewItemMenu(ActionEvent event) {
        try {
            this.type = Type.NEW_ITEM;
            switchScene(this.newItemFXML);
        }
        catch(IOException ex) {
            new PopUp(demoUserPresenter.error());
        }
    }

    /**
     * switch to UnfreezeRequest menu, but for demo user it prints no access on screen
     * @param event mouse click
     */
    @FXML
    private void getUnfreezeRequest(ActionEvent event) {
        systemMessage.setText(demoUserPresenter.noAccess());
    }

    /**
     * switch to PrivateMessageMenu, but for demo user it prints no access on screen
     * @param event mouse click
     */
    @FXML
    private void getPrivateMessageMenu(ActionEvent event) {
        systemMessage.setText(demoUserPresenter.noAccess());
    }

    /**
     * exit user from DemoUserMenu
     * @param event mouse click
     */
    @FXML
    private void exit(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    /**
     * The ENUM values for demoUser
     */
    enum Type {
        ACCOUNT_INFO, GLOBAL_INVENTORY, NEW_ITEM
    }

    /**
     * Switches the scene being viewed
     * @param filename the filename of the .fxml file to be loaded
     * @throws IOException for a funky input
     */
    private void switchScene(String filename) throws IOException {
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

}


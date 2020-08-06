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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DemoUserInfoMenu implements Initializable {
    private Type type;
    private DemoUserManager demoUserManager;
    private DemoUserfxPresenter demoUserPresenter = new DemoUserfxPresenter();
    private String DemoUserInventoryFXML = "DemoUserInventory.fxml";
    private String DemoUserWishListFXML = "DemoUserWishlist.fxml";

    @FXML private Button tradeHistory;
    @FXML private Button newPassword;
    @FXML private Button tradePartners;
    @FXML private Button recentTrades;
    @FXML private Button inventory;
    @FXML private Button wishlist;
    @FXML private Button exit;
    @FXML private Label message;


    public DemoUserInfoMenu(DemoUserManager demoUserManager) {
        this.demoUserManager = demoUserManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.tradeHistory.setText(demoUserPresenter.accountInfoPromptTradeHistory());
        this.newPassword.setText(demoUserPresenter.accountInfoPromptPassword());
        this.tradePartners.setText(demoUserPresenter.accountInfoPromptTradingPartners());
        this.recentTrades.setText(demoUserPresenter.accountInfoPromptRecentTrades());
        this.inventory.setText(demoUserPresenter.accountInfoPromptInventory());
        this.wishlist.setText(demoUserPresenter.accountInfoPromptWishlist());
        this.exit.setText(demoUserPresenter.menuPromptExit());

        tradeHistory.setOnAction(e -> noAccess(e));
        newPassword.setOnAction(e -> noAccess(e));
        tradePartners.setOnAction(e -> noAccess(e));
        recentTrades.setOnAction(e -> noAccess(e));
        this.inventory.setOnAction(e -> viewInventory());
        this.wishlist.setOnAction(e -> viewWishlist());
        exit.setOnAction(e -> exit(e));
    }

    enum Type {
        INVENTORY, WISHLIST
    }

    /**
     * Switches the scene being viewed
     * @param filename the filename of the .fxml file to be loaded
     * @throws IOException for a funky input
     */
    public void switchScene(String filename) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        switch(this.type) {
            case INVENTORY:
                loader.setController(new DemoUserInventory(demoUserManager));
                break;
            // view user wishlist
            case WISHLIST:
                loader.setController(new DemoUserWishlist(demoUserManager));
                break;
        }
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void viewInventory() {
        try{
            type = Type.INVENTORY;
            switchScene(this.DemoUserInventoryFXML);
        } catch (IOException e){
            System.out.println("oh no");
            System.out.println(demoUserManager.getUserInventory().size());
        }
    }

    @FXML
    public void viewWishlist() {
        try{
            type = Type.WISHLIST;
            switchScene(this.DemoUserWishListFXML);
        } catch (IOException e){
            System.out.println("wishlist");
            System.out.println(demoUserManager.getUserWishlist().size());
        }
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    public void noAccess(ActionEvent event){
        message.setText(demoUserPresenter.noAccess());

    }
}


package frontend.MainMenuGUI;

import controllers.*;
import frontend.MainMenuGUI.LoginController;
import gateways.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.control.*;
import javafx.stage.StageStyle;
import presenters.*;
import use_cases.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML private Button userLoginButton;
    @FXML private Button userSignUpButton;
    @FXML private Button adminLoginButton;
    @FXML private Button demoLoginButton;
    @FXML private Button exitButton;
    @FXML private Label errorMessage;

    private AdminManager adminManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;

    private final String loginFXMLFile = "Login.fxml";

    private final String adminFilepath = "data/serializedAdmins.ser";
    private final String userFilepath = "data/serializedUsers.ser";
    private final String globalInventoryFilepath = "data/serializedGlobalInventory.ser";
    private final String adminMessagesFilepath = "data/serializedAdminMessages.ser";
    private final String globalWishlistFilepath = "data/serializedGlobalWishlist.ser";
    private final String tradeFilepath = "data/serializedUserTrades.ser";

    private AdminAccountGateways adminAccountGateways;
    private UserGateway userGateway;
    private GlobalInventoryGateways globalInventoryGateways;
    private UserTradesGateway userTradesGateway;
    private GlobalWishlistGateway globalWishlistGateway;
    private AdminMessageGateway adminMessageGateway;

    private MainMenuPresenter mainMenuPresenter = new MainMenuPresenter();

    // code for method goToOtherScene is similar to: https://www.youtube.com/watch?v=XCgcQTQCfJQ
    public void goToOtherScene(String otherScene, SelectedOption selectedOption) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(otherScene));

        loader.setController(new LoginController(selectedOption, userManager, tradeManager, adminManager,
                globalInventoryManager, globalWishlistManager));

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void userLoginButtonPressed () throws IOException {
        goToOtherScene(loginFXMLFile, SelectedOption.USER_LOGIN); //change to enum
    }

    public void userSignUpButtonPressed() throws IOException {
        goToOtherScene(loginFXMLFile, SelectedOption.USER_SIGNUP);
    }

    public void adminLoginButtonPressed() throws IOException {
        goToOtherScene(loginFXMLFile, SelectedOption.ADMIN_LOGIN);
    }

    public void programDemoButtonPressed() throws IOException {
        goToOtherScene(loginFXMLFile,SelectedOption.DEMO_LOGIN);
    }

    // code for method closeButtonIsPushed is similar to: https://www.youtube.com/watch?v=i4Fk10U7Sks
    public void closeButtonIsPushed(ActionEvent actionEvent) {
        Stage s = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        s.close();
        serialize();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userLoginButton.setText(mainMenuPresenter.userLoginOption());
        userSignUpButton.setText(mainMenuPresenter.userSignUpOption());
        adminLoginButton.setText(mainMenuPresenter.adminLoginOption());
        demoLoginButton.setText(mainMenuPresenter.demoLoginOption());
        exitButton.setText(mainMenuPresenter.exitOption());

        deserialize();
        serialize();
    }

    public void deserialize(){

        try {

            GatewayBuilder gatewayBuilder = new GatewayBuilder();
            //deserialize admins
            adminAccountGateways = gatewayBuilder.getAdminAccountGateways(adminFilepath);

            if(adminAccountGateways.getAdminMap().isEmpty()){
                adminAccountGateways.beginAdminMap();
            }
            //deserialize users
            userGateway = gatewayBuilder.getUserGateway(userFilepath);

            //deserialize global inventory
            globalInventoryGateways = gatewayBuilder.getGlobalInventoryGateways(globalInventoryFilepath);

            //deserialize all user trades
            userTradesGateway = gatewayBuilder.getUserTradesGateway(tradeFilepath);

            //deserialize GlobalWishlistGateway
            globalWishlistGateway = gatewayBuilder.getGlobalWishlistGateway(globalWishlistFilepath);

            //deserialize AdminMessageGateway
            adminMessageGateway = gatewayBuilder.getAdminMessageGateways(adminMessagesFilepath);
        }
        catch(IOException | ClassNotFoundException ex) {
            errorMessage.setText("Could not read file, exiting you from the program");
            // ? exitButton.fire();
        }
        // create managers to pass in serialized data
        UseCaseBuilder useCaseBuilder = new UseCaseBuilder();
        adminManager = useCaseBuilder.getAdminManager(adminAccountGateways.getAdminMap(),
                adminMessageGateway.getMessages());
        userManager = useCaseBuilder.getUserManager(userGateway.getMapOfUsers());
        tradeManager =
                useCaseBuilder.getTradeManager(userTradesGateway.getUserTrades());
        globalInventoryManager =
                useCaseBuilder.getGlobalInventoryManager(globalInventoryGateways.getGlobalInventory());
        globalWishlistManager =
                useCaseBuilder.getGlobalWishlistManager(globalWishlistGateway.getWishlistItems());
    }

    public void serialize() {
        try {
            userGateway.writeToFile(userFilepath, userManager.getUserData());
            globalInventoryGateways.writeToFile(globalInventoryManager.getGlobalInventoryData());
            userTradesGateway.writeToFile(tradeFilepath, tradeManager.getTradeData());
            globalWishlistGateway.writeToFile(globalWishlistFilepath, globalWishlistManager.getGlobalWishlistData());
            adminAccountGateways.saveToFile(adminManager.getAdminData());
            adminMessageGateway.writeToFile(adminMessagesFilepath, adminManager.getAdminMessages());
        }
        catch (IOException e) {
            //TODO: print error pop up or something
            errorMessage.setText("An error has occurred with saving.");
        }
    }


}

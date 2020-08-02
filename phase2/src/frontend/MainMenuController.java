package frontend;

import controllers.GatewayBuilder;
import controllers.UseCaseBuilder;
import gateways.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import javafx.scene.control.*;
import presenters.MainMenuPresenter;
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

    private AdminManager adminManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;

    //private final String userLoginFXMLFile = "UserLogin.fxml";
    //private final String userSignUpFXMLFile = "UserSignUp.fxml";
    //private final String adminLoginFXMLFile = "AdminLogin.fxml";
    //private final String demoLoginFXMLFile = "DemoLogin.fxml";
    private final String loginFXMLFile = "Login.fxml";

    private MainMenuPresenter mainMenuPresenter = new MainMenuPresenter();

    // code for method goToOtherScene is similar to: https://www.youtube.com/watch?v=XCgcQTQCfJQ
    public void goToOtherScene(ActionEvent actionEvent, String otherScene, String type) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(otherScene));

        loader.setController(new LoginController(type, userManager)); //pass in all use case

        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public void userLoginButtonPressed (ActionEvent actionEvent) throws IOException {
        goToOtherScene(actionEvent, loginFXMLFile, "USER_LOGIN"); //change to enum
    }

    public void userSignUpButtonPressed(ActionEvent actionEvent) throws IOException {
        goToOtherScene(actionEvent, loginFXMLFile, "USER_SIGNUP");
    }

    public void adminLoginButtonPressed(ActionEvent actionEvent) throws IOException {
        goToOtherScene(actionEvent, loginFXMLFile, "ADMIN_LOGIN");
    }

    public void programDemoButtonPressed(ActionEvent actionEvent) throws IOException {
        goToOtherScene(actionEvent, loginFXMLFile,"DEMO_LOGIN");
    }

    // code for method closeButtonIsPushed is similar to: https://www.youtube.com/watch?v=i4Fk10U7Sks
    public void closeButtonIsPushed(ActionEvent actionEvent) {
        Stage s = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        s.close();
        //call the serializing here
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deserialize();
        userLoginButton.setText(mainMenuPresenter.userLoginOption());
        userSignUpButton.setText(mainMenuPresenter.userSignUpOption());
        adminLoginButton.setText(mainMenuPresenter.adminLoginOption());
        demoLoginButton.setText(mainMenuPresenter.demoLoginOption());
        exitButton.setText(mainMenuPresenter.exitOption());
    }

    public void deserialize(){
        final String adminFilepath = "data/serializedAdmins.ser";
        final String userFilepath = "data/serializedUsers.ser";
        final String globalInventoryFilepath = "data/serializedGlobalInventory.ser";
        final String adminMessagesFilepath = "data/serializedAdminMessages.ser";
        final String globalWishlistFilepath = "data/serializedGlobalWishlist.ser";
        final String tradeFilepath = "data/serializedUserTrades.ser";

        AdminAccountGateways adminAccountGateways;
        UserGateway userGateway;
        GlobalInventoryGateways globalInventoryGateways;
        UserTradesGateway userTradesGateway;
        GlobalWishlistGateway globalWishlistGateway;
        AdminMessageGateway adminMessageGateway;
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
            mainMenuPresenter.readError();
            mainMenuPresenter.printExit();
            return;
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
    //do serializing
}

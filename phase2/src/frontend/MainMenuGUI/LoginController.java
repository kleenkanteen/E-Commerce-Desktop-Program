package frontend.MainMenuGUI;

import controllers.AdminSystem;
import controllers.BannedUserController;
import controllers.DemoUserController;
import controllers.UserMenu;
import exceptions.InvalidUsernameException;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presenters.MainMenuPresenter;
import use_cases.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML TextField username;
    @FXML TextField password;
    @FXML Button loginButton;
    @FXML Button exitButton;
    @FXML Label errorMessage;

    private String type; //change to ENUM
    private MainMenuPresenter mainMenuPresenter = new MainMenuPresenter();
    private UserManager userManager;
    private TradeManager tradeManager;
    private AdminManager adminManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;

    public LoginController(String type, UserManager userManager, TradeManager tradeManager, AdminManager adminManager,
                           GlobalInventoryManager globalInventoryManager, GlobalWishlistManager globalWishlistManager){
        this.type = type;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.adminManager = adminManager;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
    }

    // code for method changeScreenButtonPushed is similar to: https://www.youtube.com/watch?v=XCgcQTQCfJQ
    public void changeScreenButtonPushed(ActionEvent actionEvent)  {
        try {
            Parent mainMenuParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            Scene mainMenuScene = new Scene(mainMenuParent);

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(mainMenuScene);
            window.show();
        }catch (IOException e){
            // TODO: print error pop up or something
            errorMessage.setText("File you are returning to was not found");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
        exitButton.setText("Return to Main Menu");
        exitButton.setOnAction(e -> changeScreenButtonPushed(e));

        if(type.equals("USER_LOGIN")){
            loginButton.setText("Log In");
            loginButton.setOnAction(e -> userLogin());
        }
        else if(type.equals("USER_SIGNUP")){
            loginButton.setText("Sign Up");
            loginButton.setOnAction(e -> userSignUp());
        }
        else if(type.equals("ADMIN_LOGIN")){
            loginButton.setText("Log In");
            loginButton.setOnAction(e -> adminLogin());
        }
        else {
            loginButton.setText("Log In");
            loginButton.setOnAction(e -> programDemo());
        }
    }

    private void userLogin(){
        String username = this.username.getText();
        String password = this.password.getText();
        if(userManager.login(username, password)) {
            if (!userManager.getUserIsBanned(username)) {
                UserMenu userMenu = new UserMenu(username, userManager, tradeManager, globalInventoryManager,
                        globalWishlistManager, adminManager);
                userMenu.run();
            }
            else {
                BannedUserController bannedUserController = new BannedUserController(username, adminManager);
                bannedUserController.run();
            }
        }
        else errorMessage.setText("Wrong login, try again.");
    }

    private void userSignUp(){
        try {
            String username = this.username.getText();
            String password = this.password.getText();

            boolean d = userManager.createNewUser(username, password);
            if (!d){
                mainMenuPresenter.usernameTooShort();
            }
            else {
                if (!adminManager.userExist(username)) {
                    userManager.createNewUser(username, password);
                    mainMenuPresenter.successfulAccountCreation();
                }
                else errorMessage.setText("Your username is taken or invalid, try again.");
            }
        }
        catch (InvalidUsernameException f){
            errorMessage.setText("Your username is taken or invalid, try again.");
        }
    }

    private void adminLogin(){
        String username = this.username.getText();
        String password = this.password.getText();

        if ((adminManager.login(username, password))) {
            AdminSystem successfulLogin = new AdminSystem(adminManager.getAdmin(username), adminManager, userManager,
                    globalInventoryManager, tradeManager);
            successfulLogin.run();
        }
        else errorMessage.setText("Wrong login, try again.");
    }


    private void programDemo(){
        String username = this.username.getText();
        String password = this.password.getText();

        DemoUserController demo = new DemoUserController(username, password, globalInventoryManager);
        demo.run();
    }
}


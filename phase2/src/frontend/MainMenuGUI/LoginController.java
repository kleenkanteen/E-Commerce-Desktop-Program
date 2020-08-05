package frontend.MainMenuGUI;

import controllers.AdminSystem;
import controllers.BannedUserController;
import controllers.DemoUserController;
import controllers.UserMenu;
import entities.GlobalInventory;
import exceptions.InvalidUsernameException;
import frontend.BannedUser.BannedUserMenu;
import frontend.DemoUserGUI.DemoUserMenuGUI;
import frontend.UserGUI.UserMenuGUI;
import frontend.AdminGUI.AdminController;
import frontend.DemoUserGUI.DemoUserMenuGUI;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import presenters.MainMenuPresenter;
import use_cases.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Button loginButton;
    @FXML private Button exitButton;
    @FXML private Label errorMessage;

    private SelectedOption type; //change to ENUM
    private MainMenuPresenter mainMenuPresenter = new MainMenuPresenter();
    private UserManager userManager;
    private TradeManager tradeManager;
    private AdminManager adminManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;

    private final String userMenuGUIFile = "/frontend/UserGUI/UserMenuGUI.fxml";
    private final String adminMenuGUIFile = "/frontend/AdminGUI/AdminMenu.fxml";
    private final String demoMenuGUIFile = "/frontend/DemoUserGUI/DemoUserMenu.fxml";
    private final String bannedUserMenuGUIFile = "/frontend/BannedUser/BannedUserMenuGUI.fxml";

    private enum OpenMenu {
        USER_MENU, ADMIN_MENU, BANNED_USER_MENU, DEMO_MENU
    }
    public LoginController(SelectedOption type, UserManager userManager, TradeManager tradeManager, AdminManager adminManager,
                           GlobalInventoryManager globalInventoryManager, GlobalWishlistManager globalWishlistManager){
        this.type = type;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.adminManager = adminManager;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
    }

    // code for method changeScreenButtonPushed is similar to: https://www.youtube.com/watch?v=

    private void changeScreenButtonPushed(ActionEvent actionEvent)  {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.close();
    }

    // code for method goToOtherScene is similar to: https://www.youtube.com/watch?v=XCgcQTQCfJQ

    private void goToOtherScene(String otherScene, String MenuToOpen, String username, String password) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(otherScene));

        if (MenuToOpen.equals("USER_MENU")) {
            loader.setController(new UserMenuGUI(username, userManager, tradeManager,
                    globalInventoryManager, globalWishlistManager, adminManager));
        }

        if (MenuToOpen.equals("ADMIN_MENU")) {
            loader.setController(new AdminController(adminManager.getAdmin(username), adminManager,
                    userManager, globalInventoryManager, tradeManager));
        }

        if (MenuToOpen.equals("BANNED_USER_MENU")) {
            loader.setController(new BannedUserMenu(username, adminManager));
        }

        /*
        if (MenuToOpen.equals("DEMO_MENU")) {
            loader.setController(new DemoUserMenuGUI(String username, String password,
                GlobalInventory globalInventoryManager);
        }
         */


        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
        exitButton.setText("Return to Main Menu");
        exitButton.setOnAction(e -> changeScreenButtonPushed(e));

        if(type.equals(SelectedOption.USER_LOGIN)){
            loginButton.setText("Log In");
            loginButton.setOnAction(e -> {
                try {
                    userLogin();
                } catch (IOException ioException) {
                    errorMessage.setText("Failed to log in.");
                }
            });
        }
        else if(type.equals(SelectedOption.USER_SIGNUP)){
            loginButton.setText("Sign Up");
            loginButton.setOnAction(e -> userSignUp());
        }
        else if(type.equals(SelectedOption.ADMIN_LOGIN)){
            loginButton.setText("Log In");
            loginButton.setOnAction(e -> {
                try {
                    adminLogin();
                } catch (IOException ioException) {
                    errorMessage.setText("Failed to log in.");
                }
            });
        }
        // if user selects Program Demo option
        else {
            loginButton.setText("Log In");
            loginButton.setOnAction(e -> {
                try {
                    programDemo();
                } catch (IOException ioException) {
                    errorMessage.setText("Failed to log in.");
                }
            });
        }
    }

    private void userLogin() throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        if(userManager.login(username, password)) {
            if (!userManager.getUserIsBanned(username)) {
                goToOtherScene(userMenuGUIFile, OpenMenu.USER_MENU.name(), username, password);
            }
            else {
                goToOtherScene(bannedUserMenuGUIFile, OpenMenu.BANNED_USER_MENU.name(), username, password);
            }
        }
        else errorMessage.setText("Wrong login, try again.");
    }

    private void userSignUp(){
        try {
            String username = this.username.getText();
            String password = this.password.getText();
                if (userManager.createNewUser(username, password) && !adminManager.userExist(username)) {
                    errorMessage.setText("New account successfully created");
                }
                else errorMessage.setText("Your username is taken or invalid, try again.");
        }
        catch (InvalidUsernameException f){
            errorMessage.setText("Your username is taken or invalid, try again.");
        }
    }

    private void adminLogin() throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();

        if ((adminManager.login(username, password))) {
            goToOtherScene(adminMenuGUIFile, OpenMenu.ADMIN_MENU.name(), username, password);
        }
        else errorMessage.setText("Wrong login, try again.");
    }


    private void programDemo() throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();

        goToOtherScene(demoMenuGUIFile, OpenMenu.DEMO_MENU.name(), username, password);

    }
}


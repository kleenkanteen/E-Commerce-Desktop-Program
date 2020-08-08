package frontend.MainMenuGUI;

import exceptions.InvalidUsernameException;
import frontend.AdminGUI.AdminController;
import frontend.BannedUser.BannedUserMenu;
import frontend.DemoUserGUI.DemoUserMenuGUI;
import frontend.UserGUI.UserMenuGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

    /**
     * Constructor for LoginController, if a new LoginController is created, sets values of objects to be used.
     * @param selectedOption stores user's selected sign-in or login option stored as an ENUM
     * @param userManager use case that allows users to modify their user accounts and manage their messages.
     * @param tradeManager use case with all users' trade options given their trades
     * @param adminManager use case that allows admins to modify user accounts, manage their messages
     * @param globalInventoryManager use case storing all methods by which a user can modify the items stored by
     *                               themselves and other users
     * @param globalWishlistManager use case storing all items that user and other users want in their inventory
     */

    public LoginController(SelectedOption selectedOption, UserManager userManager, TradeManager tradeManager,
                           AdminManager adminManager,
                           GlobalInventoryManager globalInventoryManager, GlobalWishlistManager globalWishlistManager){
        this.type = selectedOption;
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

    private void goToOtherScene(String otherScene, String MenuToOpen, String username)
            throws IOException {
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


        if (MenuToOpen.equals("DEMO_MENU")) {
            loader.setController(new DemoUserMenuGUI(globalInventoryManager));
        }

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();

    }

    /**
     * Initialize the scene's labels and buttons with text based on user's desired menu selection
     * @param location stores location of path storing buttons and labels.
     * @param resources ResourceBundle to localize root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        errorMessage.setText("");
        exitButton.setText("Return to Main Menu");
        exitButton.setOnAction(this::changeScreenButtonPushed);

        if (type.equals(SelectedOption.USER_LOGIN)) {
            loginButton.setText("Log In");
            loginButton.setOnAction(e -> {
                try {
                    userLogin();
                } catch (IOException ioException) {
                    errorMessage.setText("Failed to log in.");
                }
            });
        }

        else if (type.equals(SelectedOption.USER_SIGNUP)) {
            loginButton.setText("Sign Up");
            loginButton.setOnAction(e -> userSignUp());
        }
        else if (type.equals(SelectedOption.ADMIN_LOGIN)) {
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

        if (userManager.login(username, password)) {
            if (!userManager.getUserIsBanned(username)) {
                goToOtherScene(userMenuGUIFile, OpenMenu.USER_MENU.name(), username);
            }
            else {
                goToOtherScene(bannedUserMenuGUIFile, OpenMenu.BANNED_USER_MENU.name(), username);
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
            goToOtherScene(adminMenuGUIFile, OpenMenu.ADMIN_MENU.name(), username);
        }
        else errorMessage.setText("Wrong login, try again.");
    }


    private void programDemo() throws IOException {
        String username = this.username.getText();

        goToOtherScene(demoMenuGUIFile, OpenMenu.DEMO_MENU.name(), username);

    }
}


package frontend.MainMenuGUI;

import controllers.AdminSystem;
import controllers.BannedUserController;
import controllers.DemoUserController;
import controllers.UserMenu;
import exceptions.InvalidUsernameException;
import frontend.UserGUI.UserMenuGUI;
import frontend.AdminGUI.AdminController;
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
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Button loginButton;
    @FXML private Button exitButton;
    @FXML private Label errorMessage;

    private String type; //change to ENUM
    private MainMenuPresenter mainMenuPresenter = new MainMenuPresenter();
    private UserManager userManager;
    private TradeManager tradeManager;
    private AdminManager adminManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;

    private final String userMenuGUIFile = "/frontend/UserGUI/UserMenuGUI.fxml";
    private final String adminMenuGUIFile = "/frontend/AdminGUI/AdminMenu.fxml";

    private enum OpenMenu {
        USER_MENU, ADMIN_MENU, DEMO_MENU
    }
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
    private void changeScreenButtonPushed(ActionEvent actionEvent)  {
       /* try {
           Parent mainMenuParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            Scene mainMenuScene = new Scene(mainMenuParent);

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(mainMenuScene);
            window.show();
        }catch (IOException e){
            // TODO: print error pop up or something
            errorMessage.setText("File you are returning to was not found");
        }

          */
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.close();
    }

    // code for method goToOtherScene is similar to: https://www.youtube.com/watch?v=XCgcQTQCfJQ

    private void goToOtherScene(String otherScene, String MenuToOpen, String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(otherScene));

        if (MenuToOpen.equals("USER_MENU")) {
            loader.setController(new UserMenuGUI(username, userManager, tradeManager,
                    globalInventoryManager, globalWishlistManager, adminManager));
        }

       if (MenuToOpen.equals("ADMIN_MENU")) {
            loader.setController(new AdminController(adminManager.getAdmin(username), adminManager,
                    userManager, globalInventoryManager, tradeManager));
        }

       /* if (MenuToOpen.equals("DEMO_MENU")) {
            loader.setController(new UserMenuGUI(selectedOption.name(), userManager, tradeManager, adminManager,
                    globalInventoryManager, globalWishlistManager));
        */

        Stage window = new Stage();
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

        if(type.equals("USER_LOGIN")){
            loginButton.setText("Log In");
            loginButton.setOnAction(e -> {
                try {
                    userLogin();
                } catch (IOException ioException) {
                    errorMessage.setText("Failed to log in.");
                }
            });
        }
        else if(type.equals("USER_SIGNUP")){
            loginButton.setText("Sign Up");
            loginButton.setOnAction(e -> userSignUp());
        }
        else if(type.equals("ADMIN_LOGIN")){
            loginButton.setText("Log In");
            loginButton.setOnAction(e -> {
                try {
                    adminLogin();
                } catch (IOException ioException) {
                    errorMessage.setText("Failed to log in.");
                }
            });
        }
        else {
            loginButton.setText("Log In");
            loginButton.setOnAction(e -> programDemo());
        }
    }

    private void userLogin() throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        if(userManager.login(username, password)) {
            if (!userManager.getUserIsBanned(username)) {
                goToOtherScene(userMenuGUIFile, OpenMenu.USER_MENU.name(), username);
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


    private void programDemo(){
        String username = this.username.getText();
        String password = this.password.getText();

        DemoUserController demo = new DemoUserController(username, password, globalInventoryManager);
        demo.run();
    }
}


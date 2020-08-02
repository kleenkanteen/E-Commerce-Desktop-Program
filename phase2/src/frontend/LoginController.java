package frontend;

import controllers.BannedUserController;
import controllers.UserMenu;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presenters.MainMenuPresenter;
import use_cases.UserManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    //Set up labels
    @FXML TextField username;
    @FXML Button loginButton;
    @FXML Button exitButton;
    private String type; //change to ENUM
    private MainMenuPresenter mainMenuPresenter = new MainMenuPresenter();
    private UserManager userManager;

    public LoginController(String type, UserManager userManager){
        this.type = type;
        this.userManager = userManager;
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
            //print error pop up or something
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Set up names of labels from presenter
        //Set up name of exit + login button
        exitButton.setOnAction(e -> changeScreenButtonPushed(e));
        if(type.equals("USER_LOGIN")){
            loginButton.setOnAction(e -> userLogin());
        }
        else if(type.equals("USER_SIGNUP")){
            loginButton.setOnAction(e -> userSignUP());
        }
        else{
            loginButton.setOnAction(e -> adminLogin());
        }
        //Add demo user

    }

    private void userLogin(){
        String username = this.username.getText();
        String password = "";
        System.out.println("user");
        if(userManager.login(username, password)) {
            if (!userManager.getUserIsBanned(username)) {


                //UserMenu userMenu = new UserMenu(username, userManager, tradeManager, globalInventoryManager,
                //        globalWishlistManager, adminManager);
                //userMenu.run();
            }
            else{
                //BannedUserController bannedUserController = new BannedUserController(username, adminManager);
                //bannedUserController.run();
            }
        }
        //.....
    }

    private void userSignUP(){
        System.out.println("sign up");
        //.....
    }

    private void adminLogin(){
        System.out.println("admin");
        //.....
    }
}

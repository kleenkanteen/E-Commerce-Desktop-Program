package frontend.AdminGUI;
import entities.Admin;
import frontend.MainMenuGUI.LoginController;
import frontend.MessageReplySystem.AdminMessageReplyGUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import presenters.AdminMenu;
import use_cases.AdminManager;
import use_cases.GlobalInventoryManager;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController extends Application  implements Initializable{

    @FXML private Button messageInboxButton;
    @FXML private Button manageAdminAccountButton;
    @FXML private Button UserBrowsingButton;
    @FXML private Button TradeUndoButton;
    @FXML private Button exitButton;
    private Admin admin;
    private AdminMenu adminMenu;


    private AdminManager adminManager;
    private TradeManager tradeManager;


    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;

    /**
     * Class constructor.
     * Create a new AdminAccountSystem that controls and allows the admin to reply to system messages
     * @param admin the admin of the currently logged in.
     * @param adminManager the AdminManager will be used to change account information
     * @param userManager the UserManager will be used to change user account information
     * @param globalInventoryManager the GlobalInventory will be used to change item in GlobalInventory
     */

    private String AdminAccountFXML = "AdminAccount.fxml";
    private String TradeUndoFXML = "TradeUndoMenu.fxml";
    private String AdminMessageGUI = "/frontend/MessageReplySystem/MessageGUI.fxml";



    public AdminController(Admin admin, AdminManager adminManager,
                       UserManager userManager, GlobalInventoryManager globalInventoryManager, TradeManager tradeManager) {
        this.admin = admin;
        adminMenu = new AdminMenu(admin);
        this.adminManager = adminManager;
        this.userManager = userManager;
        this.globalInventoryManager = globalInventoryManager;
        this.tradeManager = tradeManager;



    }

    public void switchScene(ActionEvent actionEvent, String fileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));

        loader.setController(new AdminController(admin, adminManager,userManager, globalInventoryManager, tradeManager));

        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public void messageInboxButtonPushed(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Admin Message Inbox");
        window.setMinWidth(800);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(AdminMessageGUI));

        loader.setController(new AdminMessageReplyGUI(adminManager,globalInventoryManager,
                userManager, admin.getUsername()));

        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(scene);

        window.show();


    }

    public void manageAdminAccountButtonPushed(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Admin Account Management");
        window.setMinWidth(800);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(AdminAccountFXML));
        loader.setController(new AdminAccountController(admin, adminManager, userManager));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }

    public void userBrowsingButtonPushed(ActionEvent actionEvent){

    }

    public void tradeUndoButtonPushed(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Admin Message Inbox");
        window.setMinWidth(800);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(TradeUndoFXML));

        loader.setController(new TradeUndoController(tradeManager, userManager));

        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(scene);

        window.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
        Scene scene = new Scene(root,600,500);
        primaryStage.setTitle("AdminMenu");
        primaryStage.setScene(scene );
        primaryStage.show();



    }
    public void close(ActionEvent actionEvent){
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageInboxButton.setText("Check your message inbox");
        manageAdminAccountButton.setText("Manage Admin account" );
        UserBrowsingButton.setText("Access the information of Users");
        TradeUndoButton.setText("Undo the trade of Users");
        messageInboxButton.setOnAction(e -> {
            try {
                messageInboxButtonPushed(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        exitButton.setText("Press to go back");
        exitButton.setOnAction(this::close);
        manageAdminAccountButton.setOnAction(e -> {
            try {
                manageAdminAccountButtonPushed(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        TradeUndoButton.setOnAction(e -> {
            try {
                tradeUndoButtonPushed(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });




    }
}

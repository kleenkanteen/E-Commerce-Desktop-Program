package frontend.AdminGUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController extends Application  implements Initializable{

    @FXML private Button messageInboxButton;
    @FXML private Button manageAdminAccountButton;
    @FXML private Button UserBrowsingButton;
    @FXML private Button TradeUndoButton;

    public void switchScene(ActionEvent actionEvent, String fileName) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fileName));
        Scene newScene= new Scene(root);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    public void messageInboxButtonPushed(ActionEvent actionEvent){


    }

    public void manageAdminAccountButtonPushed(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "AdminAccount.fxml");

    }

    public void userBrowsingButtonPushed(ActionEvent actionEvent){

    }

    public void tradeUndoButtonPushed(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "TradeUndoMenu.fxml");

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageInboxButton.setText("Check your message inbox");
        manageAdminAccountButton.setText("Manage Admin account" );
        UserBrowsingButton.setText("Access the information of Users");
        TradeUndoButton.setText("Undo the trade of Users");

    }
}

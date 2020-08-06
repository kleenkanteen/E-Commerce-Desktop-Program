package frontend.AdminGUI;

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
import use_cases.TradeManager;
import use_cases.UserManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TradeUndoController implements Initializable {
    @FXML private Button searchUserButton;
    @FXML private Button goBackButton;
    @FXML private TextField userNameField;
    @FXML private Label invalidUserLabel;

    private String UndoUnstartedTradeMenuFXML = "UndoUnstartedTradeMenu.fxml";
    private UserManager usermanager;
    private TradeManager tradeManager;
    private AdminGUIPresenter adminGUIPresenter;
    TradeUndoController(TradeManager tradeManager, UserManager userManager){
        this.usermanager = userManager;
        this.tradeManager = tradeManager;
        adminGUIPresenter = new AdminGUIPresenter();
    }


    public void switchScene(ActionEvent actionEvent, String fileName) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fileName));
        Scene newScene= new Scene(root);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }
    private void openTradeWindow(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(adminGUIPresenter.undoUserTradeWindow());
        window.setMinWidth(800);
        window.setMinHeight(400);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UndoUnstartedTradeMenuFXML));

        loader.setController(new UndoUnstartedTradeMenuController(userNameField.getText(), tradeManager, usermanager));

        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(scene);


        window.showAndWait();
    }









    public void searchUserButtonPushed(ActionEvent actionEvent) throws IOException {
        if(usermanager.isValidUser(userNameField.getText())){
            openTradeWindow(actionEvent);
        }
        else {
            invalidUserLabel.setText(adminGUIPresenter.InvalidUserNameLabel());
        }


    }
    public void close(ActionEvent actionEvent){
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invalidUserLabel.setText(" ");
        searchUserButton.setText(adminGUIPresenter.searchButtonText());
        searchUserButton.setOnAction(e -> {
            try {
                searchUserButtonPushed(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        goBackButton.setText(adminGUIPresenter.exitButton());
        goBackButton.setOnAction(this::close);


    }
}

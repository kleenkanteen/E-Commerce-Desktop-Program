package frontend.DemoUserGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import use_cases.DemoUserManager;

import java.net.URL;
import java.util.ResourceBundle;

public class DemoUserAddItemMenu implements Initializable {
    private DemoUserManager demoUserManager;
    private DemoUserfxPresenter demoUserPresenter = new DemoUserfxPresenter();


    @FXML private Button confirm;
    @FXML private Button cancel;
    @FXML private Label namePrompt;
    @FXML private Label descriptionPrompt;
    @FXML private TextField nameInput;
    @FXML private TextArea descriptionInput;
    @FXML private Label errorMessage;

    public DemoUserAddItemMenu() {
    }

    public DemoUserAddItemMenu(DemoUserManager demoUserManager) {
        this.demoUserManager = demoUserManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        namePrompt.setText(demoUserPresenter.enterItemName());
        descriptionPrompt.setText(demoUserPresenter.enterItemDescription());
        this.confirm.setText(demoUserPresenter.Confirm());
        this.cancel.setText(demoUserPresenter.Cancel());

        // set button function
        this.confirm.setOnAction(e -> demoAddItem());
        this.cancel.setOnAction(this::returnToMainMenu);
    }

    public void demoAddItem(){
        errorMessage.setText(demoUserPresenter.demoaddingitem());
    }

    @FXML
    public void returnToMainMenu(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }






}

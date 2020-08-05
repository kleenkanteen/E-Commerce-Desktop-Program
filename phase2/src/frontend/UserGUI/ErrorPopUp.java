package frontend.UserGUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorPopUp implements Initializable {

    @FXML private Label error;
    @FXML private Button exit;

    private String errorMessage;

    public ErrorPopUp(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.error.setText(this.errorMessage);
        this.exit.setText("Close.");
        this.exit.setOnAction(this::exit);
    }

    public void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}

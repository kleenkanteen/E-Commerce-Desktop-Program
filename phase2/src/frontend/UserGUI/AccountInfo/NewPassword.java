package frontend.UserGUI.AccountInfo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import use_cases.UserManager;
import presenters.UserPresenter;

import java.net.URL;
import java.util.ResourceBundle;

public class NewPassword implements Initializable {

    @FXML private Button confirm;
    @FXML private Button cancel;
    @FXML private Label prompt1;
    @FXML private Label prompt2;
    @FXML private Label error;
    @FXML private Label confirmation;
    @FXML private PasswordField password1;
    @FXML private PasswordField password2;

    private UserPresenter userPresenter;
    private UserManager userManager;
    private String currUser;

    public NewPassword(String currUser, UserManager userManager) {
        this.currUser = currUser;
        this.userManager = userManager;
        this.userPresenter = new UserPresenter();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set text
        this.confirm.setText(this.userPresenter.userLoanPromptConfirm());
        this.cancel.setText(this.userPresenter.menuPromptExit());
        this.prompt1.setText(this.userPresenter.setNewPasswordPrompt());
        this.prompt2.setText(this.userPresenter.setNewPasswordPromptSecundus());

        // set button functionality
        this.confirm.setOnAction(e -> setNewPassword());
        this.cancel.setOnAction(this::exit);
    }

    public void setNewPassword() {
        this.confirmation.setVisible(false);
        if(this.password1.getLength() == 0 || this.password2.getLength() == 0) {
            this.error.setText(this.userPresenter.invalidMessageInput());
        }
        else if(!this.password1.getText().equals(this.password2.getText())) {
            this.error.setText(this.userPresenter.invalidPasswordInputs());
        }
        else {
            this.userManager.changePassword(this.currUser, this.password1.getText());
            this.error.setVisible(false);
            this.confirmation.setVisible(true);
            this.confirmation.setText(this.userPresenter.passwordConfirmation());
        }
    }

    public void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}

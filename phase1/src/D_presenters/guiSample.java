package D_presenters;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class guiSample {

    private Stage prevStage;

    public void setPrevStage(Stage primaryStage) {
        this.prevStage = primaryStage;
    }

    /**
     * Redirects the user to User Menu.
     * @param actionEvent takes in an action when the "User Login" button is clicked.
     */
    public void goToUserMenu(ActionEvent actionEvent) throws IOException {
        UserMenuGUI userLogin = new UserMenuGUI();
        Stage userLoginStage = new Stage();
        prevStage.close();
        userLogin.start(userLoginStage);
    }

    public void goToUserAccountCreation(ActionEvent actionEvent) {
        System.out.println("Coming Soon!");
    }

    public void goToAdminLogin(ActionEvent actionEvent) {
        System.out.println("Coming Soon!");
    }
}

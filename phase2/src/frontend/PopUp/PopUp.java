package frontend.PopUp;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;

public class PopUp {
    public PopUp(String message){
        Stage window = new Stage();
        Label errorMessage = new Label(message);
        errorMessage.setWrapText(true);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(new Scene(errorMessage, 200, 100));
        window.show();

    }
}

package frontend;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TradeMenuMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TradeMenu.fxml"));
        loader.setController(new TradeMenuMainController());
        Parent root = loader.load();
        primaryStage.setTitle("Trade");
        Scene tradeScene = new Scene(root, 400, 300);
        primaryStage.setScene(tradeScene);
        primaryStage.show();
    }
}

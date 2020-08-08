package frontend.TradeGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class TradeMenuMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loadTradeMenu = new FXMLLoader(getClass().getResource("TradeMenu.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        loadTradeMenu.setController(new TradeMenuMainController());
        Parent root = loadTradeMenu.load();
        primaryStage.setTitle("Trade");
        Scene tradeScene = new Scene(root, 600, 400);
        primaryStage.setScene(tradeScene);
        primaryStage.show();
    }
}

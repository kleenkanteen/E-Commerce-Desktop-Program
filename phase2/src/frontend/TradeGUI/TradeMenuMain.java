package frontend.TradeGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class TradeMenuMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loadTradeMenu = new FXMLLoader(getClass().getResource("TradeMenu.fxml"));
        //primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        loadTradeMenu.setController(new TradeMenuMainController());
        Parent root = loadTradeMenu.load();
        primaryStage.setTitle("Trade");
        Scene tradeScene = new Scene(root, 400, 300);
        primaryStage.setScene(tradeScene);
        primaryStage.show();
    }
}

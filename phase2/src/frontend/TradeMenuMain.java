package frontend;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class TradeMenuMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ScreenBuilder tradeMenuScreen = new ScreenBuilder();
        tradeMenuScreen.createScreen(primaryStage, "TradeMenu.fxml", "Trade");
    }
}

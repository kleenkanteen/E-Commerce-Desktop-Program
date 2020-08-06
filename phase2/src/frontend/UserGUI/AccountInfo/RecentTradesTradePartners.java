package frontend.UserGUI.AccountInfo;

import entities.Trade;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import presenters.UserPresenter;

import java.net.URL;
import java.util.ResourceBundle;

public class RecentTradesTradePartners implements Initializable {

    private Trade[] recentTrades;
    private String[] tradePartners;
    private UserPresenter userPresenter;
    private Type type;

    @FXML private Button exit;
    @FXML private Label entry1;
    @FXML private Label entry2;
    @FXML private Label entry3;
    @FXML private Label title;

    enum Type {
        RECENT_TRADES, TRADE_PARTNERS
    }

    /**
     * Constructor for listener that handles recent trade viewing
     * @param recentTrades array of 3 trade objects
     */
    public RecentTradesTradePartners(Trade[] recentTrades) {
        this.recentTrades = recentTrades;
        this.userPresenter = new UserPresenter();
        this.type = Type.RECENT_TRADES;
    }

    /**
     * Constructor for listener that handles frequent trade partner viewing
     * @param tradePartners array of frequent trade partner usernames
     */
    public RecentTradesTradePartners(String[] tradePartners) {
        this.tradePartners = tradePartners;
        this.userPresenter = new UserPresenter();
        this.type = Type.TRADE_PARTNERS;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set text
        this.exit.setText(this.userPresenter.menuPromptExit());
        switch(this.type) {
            // recent trades
            case RECENT_TRADES:
                this.title.setText(this.userPresenter.recentTradesMenu());
                this.entry1.setText(this.recentTrades[0].toString());
                this.entry2.setText(this.recentTrades[1].toString());
                this.entry3.setText(this.recentTrades[2].toString());
                break;
            // trade partners
            case TRADE_PARTNERS:
                this.title.setText(this.userPresenter.tradePartnersMenu());
                this.entry1.setText(this.tradePartners[0]);
                this.entry2.setText(this.tradePartners[1]);
                this.entry3.setText(this.tradePartners[2]);
                break;
        }

        // set button functionality
        this.exit.setOnAction(this::exit);
    }

    public void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}

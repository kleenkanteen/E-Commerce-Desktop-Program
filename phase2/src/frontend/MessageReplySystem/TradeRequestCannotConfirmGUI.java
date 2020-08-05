package frontend.MessageReplySystem;

import entities.Message;
import frontend.PopUp.PopUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import presenters.MessageReplyPresenter;
import use_cases.MessageBuilder;
import use_cases.TradeRequestManager;
import use_cases.UserManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TradeRequestCannotConfirmGUI implements Initializable {
    @FXML
    private Label title;
    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private Label messageContent;

    private TradeRequestManager tradeRequestManager;
    private List<Message> messages;
    private UserManager userManager;

    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();

    public  TradeRequestCannotConfirmGUI(TradeRequestManager tradeRequestManager, UserManager userManager,
                                         List<Message> messages){
        this.tradeRequestManager = tradeRequestManager;
        this.userManager = userManager;
        this.messages = messages;
    }

    private void exit(ActionEvent e){
        new PopUp(messageReplyPresenter.success());

        Stage s = (Stage)(((Node) e.getSource()).getScene().getWindow());
        s.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText(messageReplyPresenter.titleTradeRequestCannotConfirm());
        messageContent.setText(messageReplyPresenter.tradeRequestCannotConfirmPrompt(tradeRequestManager.getTradeRequest()));
        messageContent.setWrapText(true);
        button1.setText(messageReplyPresenter.keepAndSkip());
        button1.setOnAction(this::exit);
        button2.setText(messageReplyPresenter.delete());
        button2.setOnAction(e -> {
            MessageBuilder messageBuilder = new MessageBuilder();
            Message message = tradeRequestManager.getTradeRequest();
            messages.remove(message);
            userManager.addUserMessage(message.getSender(),
                    messageBuilder.getSystemMessage("You or the other trader cannot create a " +
                            "new trade at this time or the items involved or not for trade at this time. " +
                            "The other trader has chosen to delete this trade request.\n"+
                            "Trade Request: "+message.toString()));
            exit(e);
        });
    }
}

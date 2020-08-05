package frontend.MessageReplySystem;

import entities.Message;
import frontend.PopUp.PopUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presenters.MessageReplyPresenter;
import use_cases.TradeRequestManager;
import use_cases.UserManager;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;

public class TradeRequestEditGUI implements Initializable {
    @FXML private Label title;
    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private Label dateLabel;
    @FXML private Label placeLabel;
    @FXML private TextField dateTextField;
    @FXML private TextField placeTextField;

    private TradeRequestManager tradeRequestManager;
    private String accountUsername;
    private UserManager userManager;
    private List<Message> messages;
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();

    public TradeRequestEditGUI (TradeRequestManager tradeRequestManager, UserManager userManager,
                                List<Message> messages,
                                String accountUsername){
        this.userManager = userManager;
        this.tradeRequestManager = tradeRequestManager;
        this.accountUsername = accountUsername;
        this.messages =messages;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText(messageReplyPresenter.titleTradeRequestEdit());
        button1.setText(messageReplyPresenter.exit());
        button1.setOnAction(this::exit);
        button2.setText(messageReplyPresenter.edit());
        button2.setOnAction(e -> editTradeRequest());
        dateLabel.setText(messageReplyPresenter.datePrompt(tradeRequestManager.getTradeRequest().getDate().toString()));
        placeLabel.setText(messageReplyPresenter.placePrompt(tradeRequestManager.getTradeRequest().getPlace()));
        dateTextField.setText(messageReplyPresenter.emptyString());
        placeTextField.setText(messageReplyPresenter.emptyString());
    }

    private void exit(ActionEvent e){
        Stage s = (Stage)(((Node) e.getSource()).getScene().getWindow());
        s.close();
    }

    private void editTradeRequest(){
        String place = placeLabel.getText();
        String date = dateLabel.getText();
        LocalDateTime newDate = tradeRequestManager.getTradeRequest().getDate();
        String newPlace = tradeRequestManager.getTradeRequest().getPlace();
        String receiver = tradeRequestManager.getTradeRequest().getSender();

        if(date.length() != 0) {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                newDate = LocalDateTime.parse(date, dtf);
            } catch (DateTimeParseException e) {
                new PopUp(messageReplyPresenter.wrongFormat());
                return;
            }

            if (newDate.isBefore(LocalDateTime.now())) {
                new PopUp(messageReplyPresenter.enterDateInPast());
                return;
            }
        }

        else if(place.length() != 0){
            newPlace = place;
        }
        else{
            new PopUp(messageReplyPresenter.noEdit());
            return;
        }
        messages.remove(tradeRequestManager.getTradeRequest());
        tradeRequestManager.setDateAndPlace(accountUsername, newDate, newPlace);
        userManager.addUserMessage(receiver, tradeRequestManager.getTradeRequest());
    }

}

package frontend.MessageReplySystem;

import entities.Message;
import entities.TradeRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import use_cases.TradeRequestManager;
import use_cases.UserManager;

import java.io.IOException;
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
        //TODO use presenter
        title.setText("Make changes to the old trade request: ");
        button1.setText("Exit");
        button1.setOnAction(this::exit);
        button2.setText("Edit");
        button2.setOnAction(e -> editTradeRequest());
        dateLabel.setText("Old date .... \nEnter the new date in the format yyyy-mm-dd hh:mm (or leave it empty):");
        placeLabel.setText("Old place ...\nEnter the new place (or leave it empty):");
        dateTextField.setText("");
        placeTextField.setText("");
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
                title.setText("Wrong Format");
                return;
            }

            if (newDate.isBefore(LocalDateTime.now())) {
                title.setText("Enter a time in the future");
                return;
            }
        }

        else if(place.length() != 0){
            newPlace = place;
        }
        else{
            title.setText("Make at least one Edit or Exit");
            return;
        }
        messages.remove(tradeRequestManager.getTradeRequest());
        tradeRequestManager.setDateAndPlace(accountUsername, newDate, newPlace);
        userManager.addUserMessage(receiver, tradeRequestManager.getTradeRequest());
    }

}

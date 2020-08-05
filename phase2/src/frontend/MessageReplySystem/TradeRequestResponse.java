package frontend.MessageReplySystem;

import entities.*;
import exceptions.UserFrozenException;
import frontend.PopUp.PopUp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import presenters.MessageReplyPresenter;
import use_cases.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TradeRequestResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private TradeRequestManager tradeRequestManager;
    private List<Message> messageList;
    private UserManager userManager;
    private TradeManager tradeManager;
    private GlobalInventoryManager globalInventoryManager;
    private String accountUsername;

    TradeRequestResponse(TradeRequest message, List<Message> messageList, UserManager userManager,
                         GlobalInventoryManager globalInventoryManager, TradeManager tradeManager, String accountName){
        this.messageList = messageList;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalInventoryManager = globalInventoryManager;
        accountUsername = accountName;

        tradeRequestManager = new TradeRequestManager(message);

        if(!tradeRequestManager.canEdit(accountName)&&!tradeRequestManager.canEdit(message.getSender())){
            new PopUp(messageReplyPresenter.tradeRequestWarning());
        }
    }
    @Override
    public String[] getActions() {
        return messageReplyPresenter.requestAction(tradeRequestManager.getTradeRequest());
    }

    @Override
    public void doAction(String action) {
        String[]validActions = getActions();
        TradeRequest message = tradeRequestManager.getTradeRequest();
        if(action.equals(validActions[0])){
            confirmTrade();
        }
        else if(action.equals(validActions[1])){
            messageList.remove(tradeRequestManager.getTradeRequest());
            MessageBuilder messageBuilder = new MessageBuilder();

            userManager.addUserMessage(message.getSender(),
                    messageBuilder.getSystemMessage("Your trade request:"+message.toString()+
                            "\n is rejected by "+ accountUsername));
        }
        else if(action.equals(validActions[2])){
            if(!tradeRequestManager.canEdit(accountUsername)&&!tradeRequestManager.canEdit(message.getSender())){
                new PopUp(messageReplyPresenter.tradeRequestCancel());
                messageList.remove(message);
                return;
            }
            tradeRequestEdit();
        }
    }



    //----------------Helpers----------------//
    private void confirmTrade(){
        TradeRequest message = tradeRequestManager.getTradeRequest();
        if(cannotTrade(message.getUserA(),message.getItemA())||cannotTrade(message.getUserB(), message.getItemB())){
            tradeRequestCannotConfirm();
        }
        //Confirming the trade
        messageList.remove(message);
        Trade trade = tradeRequestManager.setConfirmation();
        //Add trade to both user's trade history
        tradeManager.addTrade(trade);

        //Removing the items from the GI and personal inventory
        List<Item> list = new ArrayList<>(trade.getTraderAItemsToTrade());
        for(Item i:list) {
            globalInventoryManager.removeItem(i.getItemID());
        }
        list = new ArrayList<>(trade.getTraderBItemsToTrade());
        for(Item i:list) {
            globalInventoryManager.removeItem(i.getItemID());
        }
        new PopUp(messageReplyPresenter.success());
    }

    private boolean cannotTrade(String username, List<Item> userItem){
        //Checking if the user can trade
        try{
            if(!userManager.getCanTrade(username, tradeManager.getBorrowedTimes(username),
                    tradeManager.getLendTimes(username),
                    tradeManager.getIncompleteTimes(username),
                    tradeManager.numberOfTradesCreatedThisWeek(username))) return true;
        }catch(UserFrozenException e){
            return true;
        }
        //Checking if the items in the trade is valid aka in the personal inventory and GI
        for(Item i: userItem){
            if(!globalInventoryManager.contains(i))return true;
            boolean contain = false;
            for(Item j: userItem){
                if(i.isEqual(j))contain = true;
            }
            if(!contain)return true;
        }
        return false;
    }

    private void tradeRequestEdit(){
        try {
            Stage window = new Stage();
            FXMLLoader reportLoader = new FXMLLoader(getClass().getResource("TradeRequestEdit.fxml"));
            reportLoader.setController(new TradeRequestEditGUI(tradeRequestManager, userManager, messageList, accountUsername));
            Parent root = reportLoader.load();

            window.initModality(Modality.APPLICATION_MODAL);
            window.setScene(new Scene(root));
            window.setTitle(messageReplyPresenter.reportTitle());
            window.show();
        }catch(IOException e){
            new PopUp(messageReplyPresenter.error());
        }
    }

    private void tradeRequestCannotConfirm(){
        try {
            Stage window = new Stage();
            FXMLLoader reportLoader = new FXMLLoader(getClass().getResource("TradeRequestCannotConfirm.fxml"));
            reportLoader.setController(new TradeRequestCannotConfirmGUI(tradeRequestManager, userManager, messageList));
            Parent root = reportLoader.load();

            window.initModality(Modality.APPLICATION_MODAL);
            window.setScene(new Scene(root));
            window.setTitle(messageReplyPresenter.reportTitle());
            window.show();
        }catch(IOException e){
            new PopUp(messageReplyPresenter.error());
        }
    }
}

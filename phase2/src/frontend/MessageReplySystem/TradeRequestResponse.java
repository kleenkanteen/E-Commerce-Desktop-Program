package frontend.MessageReplySystem;

import entities.*;
import exceptions.UserFrozenException;
import frontend.PopUp.PopUp;
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
    private TradeRequest message;
    private TradeRequestManager tradeRequestManager;
    private List<Message> messageList;
    private UserManager userManager;
    private TradeManager tradeManager;
    private GlobalInventoryManager globalInventoryManager;
    private String accountUsername;

    TradeRequestResponse(TradeRequest message, List<Message> messageList, UserManager userManager,
                         GlobalInventoryManager globalInventoryManager, TradeManager tradeManager, String accountName){
        this.message = message;
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
        return messageReplyPresenter.requestAction(message);
    }

    @Override
    public void doAction(String action) {
        String[]validActions = getActions();
        if(action.equals(validActions[0])){
            confirmTrade();
        }
        else if(action.equals(validActions[1])){
            messageList.remove(message);
            MessageBuilder messageBuilder = new MessageBuilder();

            userManager.addUserMessage(message.getSender(),
                    messageBuilder.getSystemMessage("Your trade request:"+message.toString()+
                            "\n is rejected by "+ accountUsername));
        }
        else if(action.equals(validActions[2])){
            if(!tradeRequestManager.canEdit(accountUsername)&&!tradeRequestManager.canEdit(message.getSender())){
                new PopUp(messageReplyPresenter.tradeRequestCancel());
            }
        }
    }



    //----------------Helpers----------------//
    private void confirmTrade(){
        if(cannotTrade(message.getUserA(),message.getItemA())||cannotTrade(message.getUserB(), message.getItemB())){
            //Tell the user that their trade cannot be created at this time
            //messageReplyMenu.printCannotTradePrompt();
//            do {
//                input = br.readLine();
//                if(input.equals("2"))done = true;
//                else if(input.equals("1")) {
//                    messages.remove(m);
//                    //Tell the other trader that the trade could not be created at this time and
//                    //the trade request is deleted
//                    userManager.addUserMessage(username,
//                            messageBuilder.getSystemMessage("You or the other trader cannot create a " +
//                                    "new trade at this time or the items involved or not for trade at this time. " +
//                                    "The other trader has chosen to delete this trade request.\n"+
//                                    "Trade Request: "+m.toString()));
//                    done = true;
//                }
//                else messageReplyMenu.printInvalidInput();
//            }while(!done);
//            return true;
            //TODO
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
        new PopUp("Success");
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

    private void tradeRequestEdit(TradeRequest m, BufferedReader br){

    }
}

package frontend.MessageReplySystem;

import entities.GlobalInventory;
import entities.Item;
import entities.TradeRequest;
import exceptions.UserFrozenException;
import presenters.MessageReplyPresenter;
import entities.Message;
import use_cases.GlobalInventoryManager;
import use_cases.MessageBuilder;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.util.List;

public class TradeRequestResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private TradeRequest message;
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
    }
    @Override
    public String[] getActions() {
        return messageReplyPresenter.requestAction(message);
    }

    @Override
    public void doAction(String action) {
        String[]validActions = getActions();
        if(action.equals(validActions[0])){
            //TODO
        }
        else if(action.equals(validActions[1])){
            messageList.remove(message);
            MessageBuilder messageBuilder = new MessageBuilder();

            userManager.addUserMessage(message.getSender(),
                    messageBuilder.getSystemMessage("Your trade request:"+message.toString()+
                            "\n is rejected by "+ accountUsername));
        }
    }



    //----------------Helpers----------------//

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
}

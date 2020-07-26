package use_cases;
import entities.*;

public class MessageBuilder {
    public Message getContentMessage(String content, String sender){
        return new ContentMessage(content, sender);
    }
    public Message getContentMessage(String content){
        return new ContentMessage(content);
    }
    public Message getNewItemRequest(String content, Item item){
        return new NewItemRequest(content, item);
    }
    public Message getFreezeRequest(String content, String username){
        return new FreezeRequest(content, username);
    }
    public Message getTradeRequest(String content, String sender){
        return new TradeRequest(content, sender);
    }
    public Message getUnfreezeRequest(String content, String username){
        return new UnfreezeRequest(content, username);
    }

}


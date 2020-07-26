package use_cases;
import entities.*;

public class MessageBuilder {

    /**
     * Return a ContentMessage with a specified user.
     * @param content the String content
     * @param sender the String username
     * @return the ContentMessage
     */
    public Message getContentMessage(String content, String sender){
        return new ContentMessage(content, sender);
    }

    /**
     * Return a ContentMessage sent by the system.
     * @param content the String content
     * @return the ContentMessage message
     */
    public Message getContentMessage(String content){
        return new ContentMessage(content);
    }

    /**
     * Return a new ItemRequestObject
     * @param content the String content
     * @param item the Item object to be approved/denied
     * @return the ItemRequest message
     */
    public Message getNewItemRequest(String content, Item item){
        return new NewItemRequest(content, item);
    }

    /**
     * Return a new FreezeRequest object
     * @param content the String content
     * @param username the String username of the user to be frozen
     * @return the FreezeRequest message
     */
    public Message getFreezeRequest(String content, String username){
        return new FreezeRequest(content, username);
    }

    /**
     * Return a new TradeRequest object
     * @param content the String content
     * @param sender the String username of the sender
     * @return a new TradeRequest message
     */
    public Message getTradeRequest(String content, String sender){
        return new TradeRequest(content, sender);
    }

    /**
     * Return a new UnfreezeRequest object
     * @param content the String content
     * @param username the String username of the user who wants to be unfrozen
     * @return the UnfreezeRequest message
     */
    public Message getUnfreezeRequest(String content, String username){
        return new UnfreezeRequest(content, username);
    }

    /**
     * Return a new UnbanRequest object
     * @param content the String content
     * @param username the String username of the banned user who wants to be unbanned
     * @return the UnbanRequest message
     */
    public Message getUnbanRequest(String content, String username) {
        return new UnbanRequest(content, username);
    }

}


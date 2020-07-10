package entities;

import java.io.Serializable;

public class TradeRequestMessage extends DecisionMessage implements Serializable {
    private TradeRequest tradeContent;

    /**
     * Class constructor.
     * A message to a User from another user that asks them to make a decision on a trade request
     * @param content is the content of the message
     * @param tradeRequest is the trade request sent
     * @param sender is the sender's username
     */
    public TradeRequestMessage(String content, TradeRequest tradeRequest, String sender) {
        super(content, new String[]{"confirm", "deny", "edit"}, sender);
        this.tradeContent = tradeRequest;
    }

    /**
     * Getter for the trade request being sent
     * @return the trade request being sent
     */
    public TradeRequest getTradeContent(){
        return tradeContent;
    }

    /**
     * Returns a string representation of the message
     * @return the content, decisions, and trade request of the message in a string representation
     */
    @Override
    public String toString() {
        return super.toString() + "\nThe trade request: "+tradeContent;
    }
}

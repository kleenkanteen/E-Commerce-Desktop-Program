package entities;

import java.io.Serializable;

public class TradeRequestMessage extends DecisionMessage implements Serializable {
    private TradeRequest tradeContent;

    /**
     * A message to a User from another user that asks them to make a decision on a trade request
     * @param content is the content of the message
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

    @Override
    public String toString() {
        return super.toString() + "\nThe trade request: "+tradeContent;
    }
}

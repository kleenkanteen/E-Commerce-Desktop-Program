package entities;

public class TradeRequestMessage extends DecisionMessage {
    private TradeRequest tradeContent;

    /**
     * A message to a User from another user that asks them to make a decision on a trade request
     * @param content is the content of the message
     */
    public TradeRequestMessage(String content, TradeRequest tradeRequest) {
        //TODO add a sender
        super(content, new String[]{"confirm", "deny", "edit"});
        this.tradeContent = tradeRequest;
    }

    /**
     * Getter for the trade request being sent
     * @return the trade request being sent
     */
    public TradeRequest getTradeContent(){
        return tradeContent;
    }
}

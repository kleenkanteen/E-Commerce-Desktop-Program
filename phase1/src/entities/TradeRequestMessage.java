package entities;

import uses_cases.TradeRequest;

public class TradeRequestMessage extends DecisionMessage {
    private TradeRequest tradeContent;

    /**
     * A message sent to a User that
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

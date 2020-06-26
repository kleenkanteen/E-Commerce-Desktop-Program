public class TradeRequestMessage extends DecisionMessage {
    private static final String[] options = new String[]{"confirm", "deny", "edit"};
    private TradeRequest tradeContent;

    /**
     * A message sent to a User that request in making a change to
     * @param content is the content of the message
     */
    protected TradeRequestMessage(String content) {
        super(content, options);
    }

    /**
     * Getter for the trade request being sent
     * @return the trade request being sent
     */
    protected TradeRequest getTradeContent(){
        return tradeContent;
    }
}

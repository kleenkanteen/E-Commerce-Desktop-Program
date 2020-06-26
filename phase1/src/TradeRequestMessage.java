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

    @Override
    public void MakingDecision(String option) {
        //TODO
    }
}

public class FreezeRequestMessage extends DecisionMessage{
    private static final String[] options = new String[]{"freeze", "not freeze"};
    private User user;

    /**
     * A message sent to a User that request in making a change to
     * @param content is the content of the message
     */
    protected FreezeRequestMessage(String content) {
        super(content, options);
    }

    @Override
    public void MakingDecision(String option) {
        //TODO
    }
}

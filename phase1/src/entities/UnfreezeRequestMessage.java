package entities;

public class UnfreezeRequestMessage extends DecisionMessage {
    /**
     * A message sent from user to admins asking to have account unfrozen.
     *
     * @param content is the content of the message
     * @param options the options that can be made in the decision
     */
    public UnfreezeRequestMessage(String content, String[] options) {
        super(content, options);
    }

}

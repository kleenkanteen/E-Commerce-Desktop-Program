package entities;

public class UnfreezeRequestMessage extends DecisionMessage {
    private User user;

    /**
     * A message sent to a Admin from a User that request to be unfreezed
     * @param content is the content of the message
     * @param user is the user wanted to be unfreezed
     */
    public UnfreezeRequestMessage(String content, User user) {
        super(content, new String[]{"Unfreeze", "Ignore"}, user.getUsername());
        this.user = user;
    }

    /**
     * Getter for the user that might need to be frozen
     * @return the user
     */
    public User getUser(){ return user; }

}

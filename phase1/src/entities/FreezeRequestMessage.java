package entities;

public class FreezeRequestMessage extends DecisionMessage {
    private User user;

    /**
     * A message sent to a Admin from the system that request to freeze a User
     * @param content is the content of the message
     * @param user the user that might be frozen
     */
    public FreezeRequestMessage(String content, User user) {
        super(content, new String[]{"Freeze", "Ignore"});
        this.user = user;
    }

    /**
     * Getter for the user that might need to be frozen
     * @return the user
     */
    public User getUser(){ return user; }
}

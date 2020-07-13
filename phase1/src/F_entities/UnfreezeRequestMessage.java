package F_entities;

import java.io.Serializable;

public class UnfreezeRequestMessage extends DecisionMessage implements Serializable {
    private String username;

    /**
     * Class constructor.
     * A message sent to a Admin from a User that request to unfreeze themselves
     * @param content is the content of the message
     * @param username is the user wanted to be unfrozen's username
     */
    public UnfreezeRequestMessage(String content, String username) {
        super(content, new String[]{"Unfreeze", "Ignore"}, username);
        this.username = username;
    }

    /**
     * Getter for the user that wanted to be unfrozen's usename
     * @return the user's username
     */
    public String getUser(){ return username; }

    /**
     * Returns a string representation of the message
     * @return the content, decisions, and user of the message in a string representation
     */
    @Override
    public String toString() {
        return "From " + getSender()+": "+getContent() + "\nThe User's username: \n"+username+"\n"+super.toString();
    }
}

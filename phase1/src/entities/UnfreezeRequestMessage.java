package entities;

import java.io.Serializable;

public class UnfreezeRequestMessage extends DecisionMessage implements Serializable {
    private String username;

    /**
     * A message sent to a Admin from a User that request to be unfreezed
     * @param content is the content of the message
     * @param username is the user wanted to be unfreezed
     */
    public UnfreezeRequestMessage(String content, String username) {
        super(content, new String[]{"Unfreeze", "Ignore"}, username);
        this.username = username;
    }

    /**
     * Getter for the user that might need to be frozen
     * @return the user
     */
    public String getUser(){ return username; }

    @Override
    public String toString() {
        return super.toString() + "\nThe User's username: "+username;
    }
}

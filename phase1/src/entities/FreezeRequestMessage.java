package entities;

import java.io.Serializable;

public class FreezeRequestMessage extends DecisionMessage implements Serializable {
    private String username;

    /**
     * A message sent to a Admin from the system that request to freeze a User
     * @param content is the content of the message
     * @param username the user that might be frozen
     */
    public FreezeRequestMessage(String content, String username) {
        super(content, new String[]{"Freeze", "Ignore"});
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

package entities;

import entities.User;
import uses_cases.DecisionMessage;

public class FreezeRequestMessage extends DecisionMessage {
    private static final String[] options = new String[]{"freeze", "not freeze"};
    private User user;

    /**
     * A message sent to a entities.User that request in making a change to
     * @param content is the content of the message
     */
    protected FreezeRequestMessage(String content) {
        super(content, options);
    }

    /**
     * Getter for the user that might need to be frozen
     * @return the user
     */
    protected User getUser(){
        return user;
    }
}

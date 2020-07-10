package entities;

import java.io.Serializable;

public class Message implements Serializable {
    private String content;
    private String senderUsername;
    /**
     * Class constructor
     * A message sent by the system with some content
     * @param content is the content of the message
     */
    public Message(String content){
        this.content = content;
        senderUsername = "System Messages";
    }

    /**
     * Class constructor
     * A message sent by an account with some content
     * @param content is the content of the message
     * @param username is the sender of this message's username
     */
    public Message(String content, String username){
        this.content = content;
        senderUsername = username;
    }

    /**
     * Getter of the content of the message
     * @return the content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * Getter of the sender of the message
     * @return the sender of the message
     */
    public String getSender() {
        return senderUsername;
    }

    /**
     * Returns a string representation of the message
     * @return the content of the message
     */
    @Override
    public String toString() {
        return "From " + senderUsername+": "+content;
    }
}

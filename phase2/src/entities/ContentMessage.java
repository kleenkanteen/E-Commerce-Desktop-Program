package entities;

public class ContentMessage extends Message {
    /**
     * Class constructor
     * A message sent by the system with only information/a content
     * @param content is the content of the message
     */
    public ContentMessage(String content){
        super(content);
    }

    /**
     * Class constructor
     * A message sent by an account with only information/a content
     * @param content is the content of the message
     * @param username is the sender of this message's username
     */
    public ContentMessage(String content, String username){
        super(content, username);
    }

}

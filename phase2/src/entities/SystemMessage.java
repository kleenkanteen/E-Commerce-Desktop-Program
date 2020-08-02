package entities;

public class SystemMessage extends Message {
    /**
     * Class constructor
     * A message sent by the system with only information/a content
     * @param content is the content of the message
     */
    public SystemMessage(String content){
        super(content);
    }

}

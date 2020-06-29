package entities;

public class Message {
    private String content;
    /**
     * A message sent to a entities.User with a content
     * @param content is the content of the message
     */
    protected Message(String content){
        this.content = content;
    }

    /**
     * Getter of the content of the message
     * @return the content of the message
     */
    protected String getContent() {
        return content;
    }

    /**
     * Returns a string representation of the message
     * @return the content of the message
     */
    @Override
    public String toString() {
        return content;
    }
}

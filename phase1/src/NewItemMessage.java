public class NewItemMessage extends DecisionMessage {
    private static final String[] options = new String[]{"confirm", "deny"};
    private Item newItem;

    /**
     * A message sent to a User that request in making a change to
     * @param content is the content of the message
     */
    protected NewItemMessage(String content) {
        super(content, options);
    }

    /**
     * Getter for the new item that request looking at
     * @return the new item
     */
    protected Item getNewItem(){
        return newItem;
    }
}
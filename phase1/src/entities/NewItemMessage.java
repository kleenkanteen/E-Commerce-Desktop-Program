package entities;

public class NewItemMessage extends DecisionMessage {
    private Item newItem;

    /**
     * A message sent to a entities.User that request in making a change to
     * @param content is the content of the message
     */
    public NewItemMessage(String content, Item item) {
        super(content, new String[]{"confirm", "deny"});
        this.newItem = item;
    }

    /**
     * Getter for the new item that request looking at
     * @return the new item
     */
    public Item getNewItem(){
        return newItem;
    }
}

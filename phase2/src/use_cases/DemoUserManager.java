package use_cases;

import java.util.List;
import entities.DemoUser;
import entities.*;

public class DemoUserManager {

    private DemoUser demoUser;
    private String demoName;
    private int itemID;

    /**
     * Constructs a new DemoUserManager and stores an instance of DemoUser as an instance variable
     * @param username the string username
     * @param password the string password
     */
    public DemoUserManager(String username, String password) {
        this.demoUser = new DemoUser(username, password);
        this.demoName = username;
        this.itemID = 0;
    }

    /**
     * Get this demo user's inventory
     * @return return this user's inventory
     */
    public List<Item> getUserInventory() { return this.demoUser.getPersonalInventory(); }

    /**
     * Return this user's wishlist
     * @return return this user's wishlist
     */
    public List<Item> getUserWishlist() { return this.demoUser.getWishlist(); }

    /**
     * Return the inbox, composed of a predefined TradeRequest entity and a private message
     * @return the list of predefined messages
     */
    public List<Message> getUserMessage() {
        // TODO add a trade request
        // TODO add a private whatever personal message
        return this.demoUser.getMessages();
    }

    /**
     * Add to the wishlist
     * @param item the item to be added
     */
    public void addDemoWishlist(Item item) {
        this.demoUser.addWishlist(item);
    }

    /**
     * Create a new item and add it directly to the the demoUser's/Global inventory
     * @param name the name of the item
     * @param description the description of the item
     */
    public void createNewItem(String name, String description) {
        //TODO add to DemoGlobalInventory
        Item demoItem = new Item(name, this.demoName, description);
        demoItem.setItemID(Integer.toString(this.itemID));
        this.itemID++;
        this.demoUser.addPersonalInventory(demoItem);
    }

    /**
     * Add an item to the user's personal inventory
     * @param item the item to be added
     */
    public void addToInventory(Item item) {
        this.demoUser.addPersonalInventory(item);
    }

    /**
     * Remove an item from the personal inventory
     * @param item the item to be removed
     */
    public void removeFromInventory(Item item) {
        this.demoUser.removePersonalInventory(item);
    }

    /**
     * Remove from the wishlist
     * @param item the item to be removed
     */
    public void removeFromWishlist(Item item) {
        this.demoUser.removeWishlist(item);
    }

    /**
     * Remove a message from this user's inbox
     * @param message the message to be removed
     */
    public void removeMessage(Message message) {
        this.demoUser.removeMessage(message);
    }
}

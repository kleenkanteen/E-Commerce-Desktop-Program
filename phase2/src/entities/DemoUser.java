package entities;

import java.util.ArrayList;
import java.util.List;

public class DemoUser extends Account {
    private List<Item> personalInventory = new ArrayList<>();
    private List<Item> personalWishlist = new ArrayList<>();
    private List<Message> demoMessages = new ArrayList<>();

    /**
     * Constructs a new DemoUser account.
     * @param username string username
     * @param password string password
     */
    public DemoUser(String username, String password){
        super(username, password);
    }

    /**
     * Return the personal inventory
     * @return the List of this DemoUser's personal inventory
     */
    public List<Item> getPersonalInventory() { return this.personalInventory; }

    /**
     * Get the wishlist
     * @return the list of this user's personal wishlist
     */
    public List<Item> getWishlist() { return this.personalWishlist; }

    /**
     * Get this user's list of messages
     * @return the List of this user's messages
     */
    public List<Message> getMessages() {return this.demoMessages; }

    /**
     * Add an item to this demo user's inventory
     * @param item the item to be added
     */
    public void addPersonalInventory(Item item) { this.personalInventory.add(item); }

    /**
     * Add an item to this user's wishlist
     * @param item Item to be added
     */
    public void addWishlist(Item item) { this.personalWishlist.add(item); }

    /**
     * Add a message to this user's inbox
     * @param message the message to be added
     */
    public void addMessage(Message message) { this.demoMessages.add(message); }

    /**
     * Remove an item from inventory
     * @param item the item to be removed
     */
    public void removePersonalInventory(Item item) { this.personalInventory.remove(item); }

    /**
     * Remove an item from the wishlist
     * @param item the item to be removed
     */
    public void removeWishlist(Item item) { this.personalWishlist.remove(item); }

    /**
     * Remove an item from inbox
     * @param message the message to be removed
     */
    public void removeMessage(Message message) { this.demoMessages.remove(message); }
}

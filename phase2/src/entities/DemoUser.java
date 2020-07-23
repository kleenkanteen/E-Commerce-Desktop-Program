package entities;

import java.util.ArrayList;
import java.util.List;

public class DemoUser extends Account {
    private List<Item> personalInventory = new ArrayList<>();
    private List<Item> personalWishlist = new ArrayList<>();
    //private final List<Message> demoMessages;

    public DemoUser(String username, String password){
        super(username, password);
    }

    public List<Item> getPersonalInventory() {
        return personalInventory;
    }

    public List<Item> getWishlist() {
        return personalWishlist;
    }

    public void addPersonalInventory(Item item) {
        personalInventory.add(item);
    }

    public void addWishlist(Item item) {
        personalWishlist.add(item);
    }

    public void removePersonalInventory(Item item) {
        personalInventory.remove(item);
    }

    public void removeWishlist(Item item) {
        personalWishlist.remove(item);
    }
}

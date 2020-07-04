package entities;

import java.io.Serializable;

public class Item implements Serializable {
    /**
     * Creates a trade with an item that both the seller wants to sell
     * and the buyer wants to buy.
     * The buyer creates the trade with the item that they want to sell.
     * @param itemID is a unique String assigned by AdminUser to the item
     * @param description is the String that the user sets to describe the Item condition
     * @param name is the name of this Item
     * @param ownerName refers who this Item belongs to.
     */

    private String itemID = null;
    private final String description;
    private final String name;
    private String ownerName;


    /**
     * Create a HashMap to store the information of item within the GlobalInventory
     * The constructor will be called with no parameter and automatically construct an empty HashMap
     * @param description is the String that the user sets to describe the Item condition
     * @param name is the name of this Item
     * @param ownerName refers who this Item belongs to.
     */

    public Item(String name, String ownerName, String description){
        this.name = name;
        this.ownerName = ownerName;
        this.description = description;

    }

    @Override
    public String toString() {
        return "entities.Item{" +
                "itemID='" + itemID + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }

    /**
     * Set the ownerName of the item that can be called when the trade is made
     * @param ownerName is the new owner Name of this Item
     */

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * Set the ItemID to the Item when the admin approve the Item.
     * @param i is the unique itemID can be generated for the item
     */

    public void setItemID(String i) {
        itemID = i ;
    }

    /**
     * getter to get the itemID from the Item
     * @return itemID of the Item
     */

    public String getItemID(){
        return itemID;
    }

    /**
     * Getter to get the description of the Item
     * @return the description of the Item
     */

    public String getDescription() {
        return description;
    }

    /**
     * Getter to get the name of the Item
     * @return the name of the Item
     */

    public String getName() {
        return name;
    }

    /**
     * Getter to get the owner's name of the Item
     * @return the ownerName of the Item
     */

    public String getOwnerName() {
        return ownerName;
    }

    public boolean isEqual(Item item){
        return item.getDescription().equals(description) &&
                item.getName().equals(name) && item.getOwnerName().equals(ownerName)
                && (item.getItemID().equals(itemID));
    }

}




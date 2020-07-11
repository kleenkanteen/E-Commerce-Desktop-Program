package F_entities;

import java.io.Serializable;

public class Item implements Serializable {
    /**
     * Creates an Item that available for trade if the admin approve the Item and assign an unique ID to it.
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
     * constructor to create a new Item
     * @param description is the String that the user sets to describe the Item condition
     * @param name is the name of this Item
     * @param ownerName refers who this Item belongs to.
     */

    public Item(String name, String ownerName, String description){
        this.name = name;
        this.ownerName = ownerName;
        this.description = description;

    }

    /**
     * change the String representation of Item.
     * @return String of item's content.
     */

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

    /**
     * To determine if two items are identical
     * @param item is the Item we want to compare with
     * @return true iff two items have same itemID.
     */

    public boolean isEqual(Item item){
        return (item.getItemID().equals(itemID));
    }

}




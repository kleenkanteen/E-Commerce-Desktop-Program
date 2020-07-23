package entities;

import java.io.Serializable;

public class Item implements Serializable {

    private String itemID = null;
    private final String description;
    private final String name;
    private String ownerName;
    private String place = null;



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
     * setter to set the location that the owner of this item belongs to.
     * @param place the location the owner of this Item in.
     */

    public void setPlace(String place){
        this.place = place;
    }



    /**
     * getter to the place of the Item
     * @return return the place of the Item.
     */

    public String getPlace() {
        return place;
    }

    /**
     * set the new ownerName to the items that have been traded
     * @param newOwnerName the new ownerName that will be assigned to the Item.
     */

    public void setOwnerName(String newOwnerName){
        ownerName = newOwnerName;
    }

    /**
     * change the String representation of Item.
     * @return String of item's content.
     */

    @Override
    public String toString() {
        return "Item Name: " + getName() + "\n"
                + "Owner Name: " + getOwnerName() + "\n"
                + "Description: " + getDescription();
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




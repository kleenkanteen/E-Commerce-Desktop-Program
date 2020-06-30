package entities;

public class Item {
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

    Item(String name, String ownerName, String description){
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

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setItemID(String i){
        itemID = i ;
    }

    public String getItemID(){
        return itemID;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getOwnerName() {
        return ownerName;
    }
    public boolean isEqual(Item item){
        return item.getDescription().equals(description) &&
                item.getName().equals(name) && item.getOwnerName().equals(ownerName);
    }

}




package F_entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

public class GlobalInventory implements Serializable {

    private HashMap<String, Item> itemMap;
    private ArrayList<String> itemIdCollection;
    /**
     * Create a HashMap to store the information of item within the GlobalInventory
     * Crease an ArrayList to store all the ID that has been assigned to Item.
     * The constructor will be called with no parameter and automatically construct an empty HashMap
     * and an empty ArrayList.
     */

    public GlobalInventory(){
        itemMap = new HashMap<>();
        itemIdCollection = new ArrayList<>();
    }

//    /**
//     * return the hashmap that stores the Items in global inventory
//     * @return hashmap of items stored in global inventory
//     */
//
//    public HashMap<String, Item> getItemMap() {
//        return itemMap;
//    }

//    /**
//     * modify the hashmap that stores the Items in global inventory with hashmap passed in.
//     */
//    public void setItemMap(HashMap<String, Item> itemMap) {
//        this.itemMap = itemMap;
//    }


    /**
     * Add ItemID to ItemIdCollection
     * @param itemID is the unique id each item has.

     */

    public void addItemIdToCollection(String itemID){
        itemIdCollection.add(itemID);
    }


    /**
     * getter for the Arraylist of itemID that have ever existed in the program.
     * @return the ArrayList of all ID that has ever been assigned to Item
     */


    public ArrayList<String> getItemIdCollection() {
        return itemIdCollection;
    }

//    /**
//     * Getter of Item in GlobalInventory with ItemID been called
//     * @param itemID is the unique ID of each Item.
//
//     * @return Item with itemID been called
//     * and return null if the itemID is not in the GlobalInventory
//     */
//
//    public Object getItem(String itemID) {
//        if (itemMap.containsKey(itemID)){
//            return itemMap.get(itemID);
//        }
//        else {
//            System.out.println("itemID not found");
//            return null;
//        }
//
//    }

    /**
     * Getter of the Item by the index with the order of key stored in itemMap
     * @param index is the index follows the order of keys stored in itemMap
     * @return the Item referred by index been called
     */

    public Item getItemByIndex(int index){
        Set<String> keys = itemMap.keySet();
        String i = new ArrayList<>(keys).get(index);
        return itemMap.get(i);
    }

    /**
     * Setter of Item in GlobalInventory with itemID as key and Item as what key refers to
     * @param itemID is the key for itemMap
     * @param item is what itemID refers to in itemMap
     */

    public void addItem(String itemID, Item item){
        itemMap.put(itemID, item);
    }

    /**
     * Remove item with specific itemID
     * @param itemID is the key for itemMap.

     */

    public void removeItem(String itemID){
        itemMap.remove(itemID);

    }

    /**
     * getter of the number of Item that in the global inventory.
     * @return the number of Item that exists in itemMap
     */

    public int getNumOfItem(){
        return itemMap.size();
    }

    /**
     * @param itemID is the itemID that needed to be checked about its existence in itemMap
     * @return true if the itemID exists in the itemMap.
     */

    public boolean containsKey(String itemID){
        return itemMap.containsKey(itemID);
    }


//    /**
//     * method for the situation if the user wants to search a specific type of item.
//     * @param itemName is the item name the user wants to search in GlobalInventory
//     * @return an arraylist of Item with the Name the User searches
//     */
//
//    public ArrayList<Item> searchByItemName(String itemName){
//        ArrayList<Item> banana = new ArrayList<Item>();
//        for (int i = 0; i < itemMap.size(); i++){
//            Set<String> keys = itemMap.keySet();
//            String f = new ArrayList<>(keys).get(i);
//            if (itemMap.get(f).getName().equals(itemName)){
//                banana.add(itemMap.get(f));
//            }
//        }
//        return banana;
//    }

//    /**
//     * method for search engine that if the user want ot search the items that belongs to a specific person.
//     * @param ownerName is the owner name the user wants to search in GlobalInventory
//     * @return an arraylist of Item belongs to the User with specific ownerName within GlobalInventory
//     */
//
//    public ArrayList<Item> searchByOwnerName(String ownerName){
//        ArrayList<Item> banana = new ArrayList<Item>();
//        for (int i = 0; i < itemMap.size(); i++){
//            Set<String> keys = itemMap.keySet();
//            String f = new ArrayList<>(keys).get(i);
//            if (itemMap.get(f).getOwnerName().equals(ownerName)){
//                banana.add(itemMap.get(f));
//            }
//        }
//        return banana;
//    }


    public String toString() {
        StringBuilder i = new StringBuilder();
        for (String key : itemMap.keySet()) {
            i.append(key).append(itemMap.get(key)).append("\n");
        }
        // from https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
        return i.toString();
    }



}

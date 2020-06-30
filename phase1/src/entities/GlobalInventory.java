package entities;

import entities.Item;
import exceptions.InvalidItemException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

public class GlobalInventory {
    HashMap<String, Item> itemMap = new HashMap<String, Item>();
    /**
     * Create a HashMap to store the information of item within the GlobalInventory
     * The constructor will be called with no parameter and automatically construct an empty HashMap
     */

    GlobalInventory(){}


    /**
     * Getter of Item in GlobalInventory with ItemID been called
     * @param itemID is the unique ID of each Item.
     * @throws  InvalidItemException if itemID is not existed in 'itemMap'
     * @return Item with itemID been called
     */

    public Object getItem(String itemID) throws InvalidItemException {
        if (itemMap.containsKey(itemID)){
            return itemMap.get(itemID);
        }
        else {
            throw new InvalidItemException();
        }

    }

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
     * @throws InvalidItemException if the key doesn't not exist in itemMap
     */

    public void removeItem(String itemID) throws InvalidItemException{
        if (! itemMap.containsKey(itemID)){
            throw new InvalidItemException();
        }
        itemMap.remove(itemID);
    }

    /**
     * return the number of Item that exists in itemMap
     */

    public int getNumOfItem(){
        return itemMap.size();
    }

    /**
     * @param itemID is the itemID that needed to be checked about its existence in itemMap
     * return true if the itemID exists in the itemMap.
     */

    public boolean containsKey(String itemID){
        return itemMap.containsKey(itemID);
    }


    /**
     * @param itemName is the item name the user wants to search in GlobalInventory
     * return an arraylist of Item with the Name the User searches
     */

    public ArrayList<Item> searchByItemName(String itemName){
        ArrayList<Item> banana = new ArrayList<Item>();
        for (int i = 0; i < itemMap.size(); i++){
            Set<String> keys = itemMap.keySet();
            String f = new ArrayList<>(keys).get(i);
            if (itemMap.get(f).getName().equals(itemName)){
                banana.add(itemMap.get(f));
            }
        }
        return banana;
    }

    /**
     * @param ownerName is the owner name the user wants to search in GlobalInventory
     * return an arraylist of Item belongs to the User with specific ownerName within GlobalInventory
     */

    public ArrayList<Item> searchByOwnerName(String ownerName){
        ArrayList<Item> banana = new ArrayList<Item>();
        for (int i = 0; i < itemMap.size(); i++){
            Set<String> keys = itemMap.keySet();
            String f = new ArrayList<>(keys).get(i);
            if (itemMap.get(f).getOwnerName().equals(ownerName)){
                banana.add(itemMap.get(f));
            }
        }
        return banana;
    }






}

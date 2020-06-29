package entities;

import entities.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

public class GlobalInventory {
    HashMap<String, Item> itemMap = new HashMap<String, Item>();


    GlobalInventory(){

    }

    public Object getItem(String itemID){
        if (itemMap.containsKey(itemID)){
            return itemMap.get(itemID);
        }
        else {
            return "There is no record matched with your itemID";
        }

    }

    public Item getItemByIndex(int index){
        Set<String> keys = itemMap.keySet();
        String i = new ArrayList<>(keys).get(index);
        return itemMap.get(i);
    }

    public void addItem(String itemID, Item item){
        itemMap.put(itemID, item);
    }

    public void removeItem(String itemID){
        itemMap.remove(itemID);
    }

    public boolean containsKey(String itemID){
        return itemMap.containsKey(itemID);
    }

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

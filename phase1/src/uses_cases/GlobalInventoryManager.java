package uses_cases;

import entities.Item;
import entities.GlobalInventory;
import exceptions.InvalidItemException;

import java.util.ArrayList;

public class GlobalInventoryManager {

    /**
     * @param gI is the GlobalInventory we want to modify.
     */
    GlobalInventory gI;

    /**
     * construct the Use Case class to do some changes on gI.
     * @param gI is the GlobalInventory we are about to modify.
     */
    GlobalInventoryManager(GlobalInventory gI){
        this.gI = gI;
    }


    /**
     * add the item to globalInventory
     * @param itemID set the key in globalInventory
     * @param item set what the key refers to in globalInventory
     */

    public void addItem(String itemID, Item item){
        gI.addItem(itemID, item);
    }

    /**
     * remove the item with specific itemID
     * @param itemID is the itemID of the item we want to remove
     * @throws InvalidItemException if itemID does not exist in gI
     */

    public void removeItem(String itemID) throws InvalidItemException {
        if (gI.containsKey(itemID)) {
            gI.removeItem(itemID);
        }
        else {
            throw new InvalidItemException();
        }
    }

    /**
     * Generate an arraylist with at most 10 items sorted by key order
     * @param pageNumber is what page the user want to see
     * @return an arraylist with at most 10 items sorted by key order
     */



    public ArrayList<Item> generatePage(int pageNumber){
        ArrayList<Item> itemList = new ArrayList<Item>();
        for (int i = (pageNumber - 1) * 10; i < pageNumber * 10 && i < gI.getNumOfItem(); i++){
            itemList.add(gI.getItemByIndex(i));

        }
        return itemList;

    }

    /**
     * generate an arraylist of Item which has itemName
     * @param itemName is the name of item the user want to search
     * @return an arraylist of Item which the user want to search
     */

    public ArrayList<Item> searchWithItemName(String itemName){
        return gI.searchByItemName(itemName);
    }

    /**
     * generate an arraylist of Item belongs to the specific owner
     * @param ownerName is the name of item the user want to search
     * @return an arraylist of Item belongs to the specific owner
     */
    public ArrayList<Item> searchWithItemOwner(String ownerName){
        return gI.searchByOwnerName(ownerName);
    }
}



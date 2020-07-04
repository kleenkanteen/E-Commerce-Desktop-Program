package uses_cases;

import entities.Admin;
import entities.Item;
import entities.GlobalInventory;
import exceptions.InvalidItemException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;

public class GlobalInventoryManager implements Serializable {

    /**
     * @param gI is the GlobalInventory we want to modify.
     */
    GlobalInventory gI;
    HashMap<String, Item> mapItem = new HashMap<>();


    /**
     * construct the Use Case class to do some changes on gI.
     *

     */
    public GlobalInventoryManager(GlobalInventory gI) {
        this.gI = gI;
    }



    private String IdGenerator() {
        Random rand = new Random();
        int id = rand.nextInt(900000000) + 100000000;
        String ID = Integer.toString(id);
        while (gI.getItemIdCollection().contains(ID)) {
            id = rand.nextInt(900000000) + 100000000;
            ID = Integer.toString(id);
        }
        return ID;
    }


    /**
     * add the item to globalInventory with an unique Id generated automatically
     * The ID generated will be assigned to the Item
     * and then the that ItemID will be sent to IdCollection to record
     *
     * @param item set what the key refers to in globalInventory
     */

    public void addItemToHashMap(Item item) {
        if (item.getItemID() != null) {

            gI.addItem(item.getItemID(), item);


        } else {

            String itemID = IdGenerator();

            item.setItemID(itemID);
            gI.addItemIdToCollection(itemID);

            gI.addItem(itemID, item);


        }}

        /**
         * remove the item with specific itemID
         * @param itemID is the itemID of the item we want to remove
         * @throws InvalidItemException if itemID does not exist in gI
         */

        public void removeItem (String itemID) throws InvalidItemException {
            if (gI.containsKey(itemID)) {
                HashMap<String, Item> gi = gI.getItemMap();
                gi.remove(itemID);
                gI.setItemMap(gi);

            } else {
                throw new InvalidItemException();
            }
        }




        /**
         * Generate an arraylist with at most 10 items sorted by key order
         * @param pageNumber is what page the user want to see
         * @return an arraylist with at most 10 items sorted by key order
         */


        public ArrayList<Item> generatePage ( int pageNumber){
            ArrayList<Item> itemList = new ArrayList<Item>();
            for (int i = (pageNumber - 1) * 10; i < pageNumber * 10 && i < gI.getNumOfItem(); i++) {
                itemList.add(gI.getItemByIndex(i));

            }
            return itemList;

        }

        /**
         * generate an arraylist of Item which has itemName
         * @param itemName is the name of item the user want to search
         * @return an arraylist of Item which the user want to search
         */

        public ArrayList<Item> searchWithItemName (String itemName){
            return gI.searchByItemName(itemName);
        }

        /**
         * generate an arraylist of Item belongs to the specific owner
         * @param ownerName is the name of item the user want to search
         * @return an arraylist of Item belongs to the specific owner
         */
        public ArrayList<Item> searchWithItemOwner (String ownerName){
            return gI.searchByOwnerName(ownerName);
        }
    }




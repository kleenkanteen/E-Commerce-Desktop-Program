package uses_cases;

import entities.Item;
import entities.GlobalInventory;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class GlobalInventoryManager implements Serializable {

    // gI is the GlobalInventory we want to modify.
    protected GlobalInventory gI;

    /**
     * construct the Use Case class to do some changes on gI.
     * @param gI - the globalinventory it takes in
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

         */

        public boolean removeItem (String itemID){
            return gI.removeItem(itemID);
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

        public int generatePageNumber(){
           int num =  gI.getNumOfItem();
           return (int) Math.ceil((double)num/10);
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




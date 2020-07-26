package use_cases;

import entities.Category;
import entities.Item;
import entities.GlobalInventory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GlobalInventoryManager implements Serializable {

    // gI is the GlobalInventory we want to modify.
    private GlobalInventory globalInventory;




    /**
     * construct the Use Case class to do some changes on globalinventory.
     *
     * @param gI - the globalInventory it takes in
     */
    public GlobalInventoryManager(GlobalInventory gI) {
        this.globalInventory = gI;
    }

    /**
     * getter to get the Item from GlobalInventory with itemID.
     * @param itemID is the unique id that each item has.
     * @return the Item with itemID and return nothing if ID doesn't exist in GlobalInventory
     */


    public Item getItemFromGI(String itemID){
        return (Item) globalInventory.getItem(itemID);
    }


    public ArrayList<Item> getItemFromGI(ArrayList<String> itemIDList){
        ArrayList<Item> newList = new ArrayList<>();
        if (itemIDList.size() == 0){
            return newList;
        }
        for (String s : itemIDList) {
            newList.add((Item) globalInventory.getItem(s));
        }
        return newList;
    }




    private String IdGenerator() {
        Random rand = new Random();
        int id = rand.nextInt(900000000) + 100000000;
        String ID = Integer.toString(id);
        while (globalInventory.getItemIdCollection().contains(ID)) {
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
        if (!item.getItemID().equals("")) {

            globalInventory.addItem(item.getItemID(), item);


        } else {

            String itemID = IdGenerator();

            item.setItemID(itemID);
            globalInventory.addItemIdToCollection(itemID);

            globalInventory.addItem(itemID, item);


        }
    }


    /**
     * remove the item with specific itemID
     *
     * @param itemID is the itemID of the item we want to remove
     */

    public void removeItem(String itemID) {
        globalInventory.removeItem(itemID);
    }





    /**
     * Generate an arraylist with at most 10 items sorted by key order
     *
     * @param pageNumber is what page the user want to see
     * @return an arraylist with at most 10 items sorted by key order
     */


    public List<Item> generatePage(int pageNumber) {
        ArrayList<Item> itemList = new ArrayList<>();
        for (int i = (pageNumber - 1) * 10; i < pageNumber * 10 && i < globalInventory.getNumOfItem(); i++) {
            itemList.add(globalInventory.getItemByIndex(i));

        }
        return itemList;

    }

    /**
     * generate the page number of the last page in globalInventory
     *
     * @return an int to represent the last page in globalInventory.
     */

    public int generatePageNumber() {
        int num = globalInventory.getNumOfItem();
        return (int) Math.ceil((double) num / 10);
    }

        /**
         * generate an arraylist of Item which has itemName
         * @param itemName is the name of item the user want to search
         * @return an arraylist of Item which the user want to search
         */

        public List<Item> searchWithItemName (String itemName){
            return globalInventory.searchByItemName(itemName);
        }

    /**
     * generate an arraylist of Item belongs to the specific owner
     * @param ownerName is the name of item the user want to search
     * @return an arraylist of Item belongs to the specific owner
     */
    public List<Item> getPersonInventory (String ownerName){ return globalInventory.searchByOwnerName(ownerName); }

    /**
     * returns whether the global inventory contains an item
     *
     * @param item is the name of item the user want to search
     * @return whether the item is in the global inventory
     */
    public boolean contains(Item item) {
        return globalInventory.containsKey(item.getItemID());
    }

    /**
     * return a list with all Item in a specific category
     * @param category the category of Item that the user wants to search
     * @return the list with all Item in a specific category
     */

    public List<Item> searchByCategory(Category category){
        return globalInventory.filterWithCategory(category);
    }



    /**
     * return if gI has no Item in it.
     *
     * @return true if gI has no Item in it.
     */


    public boolean hasNoItem() {
        return globalInventory.isEmpty();

    }

//    public ArrayList<Item> generatePage(int pageNumber, String userName) {
//        ArrayList<Item> lst = new ArrayList<>();
//
//        for (int i = 0; i < gI.getNumOfItem(); i++) {
//            if (!gI.getItemByIndex(i).getOwnerName().equals(userName)) {
//                lst.add(gI.getItemByIndex(i));
//            }
//        }
//        ArrayList<Item> itemList = new ArrayList<>();
//        for (int i = (pageNumber - 1) * 10; i < pageNumber * 10 && i < lst.size(); i++) {
//            itemList.add(lst.get(i));
//
//        }
//        return itemList;
//
//    }
//
//    /**
//     * generate the page number of the last page in globalInventory
//     *
//     * @return an int to represent the last page in globalInventory.
//     */
//
//    public int generatePageNumber(String userName) {
//        ArrayList<Item> lst = new ArrayList<>();
//        for (int i = 0; i < gI.getNumOfItem(); i++) {
//            if (!gI.getItemByIndex(i).getOwnerName().equals(userName)) {
//                lst.add(gI.getItemByIndex(i));
//            }
//        }
//        int num = lst.size();
//        return (int) Math.ceil((double) num / 10);
//    }

    public GlobalInventory getGlobalInventoryData(){
        return globalInventory;
    }
}



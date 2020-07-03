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
    String filePath;

    /**
     * construct the Use Case class to do some changes on gI.
     * @param filePath is the GlobalInventory we are about to modify.
     */
    public GlobalInventoryManager(String filePath){
        this.filePath = filePath;
        gI = new GlobalInventory();
        try {
            File file = new File(filePath);
            if (file.exists()) {
                readFromFile();
            } else {
                file.createNewFile();
            }
        }
        catch(IOException ex) {
            System.out.println("Failed to read");
        }
    }

    public GlobalInventory getGI() {
        return gI;
    }

    private void readFromFile(){
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            gI = (GlobalInventory) input.readObject();
            input.close();
        } catch (IOException ex) {
            System.out.println("Failed to read");
        }
        catch (ClassNotFoundException ex){
            System.out.println("failed to find the class to read");
        }
    }

    private void writeToFile(GlobalInventory gi){
        try{
            OutputStream file = new FileOutputStream(filePath);

        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        output.writeObject(gi);
        output.close();}
        catch (FileNotFoundException ex){
            System.out.println("No file is found");

        }
        catch (IOException ex){
            System.out.println("Filed to write the Object");
        }


    }

    private String IdGenerator(){
        Random rand = new Random();
        int id =  rand.nextInt(900000000) + 100000000;
        String ID = Integer.toString(id);
        while (gI.getItemIdCollection().contains(ID)){
            id =  rand.nextInt(900000000) + 100000000;
            ID = Integer.toString(id);
        }
        return ID;
    }


    /**
     * add the item to globalInventory with an unique Id generated automatically
     * The ID generated will be assigned to the Item
     * and then the that ItemID will be sent to IdCollection to record
     * @param item set what the key refers to in globalInventory
     */

    public void addItemToHashMap(Item item)  {

            String itemID = IdGenerator();

        item.setItemID(itemID);
        addItemIdToCollection(itemID);
        HashMap<String, Item> gi = gI.getItemMap();
        gi.put(itemID, item);
        gI.setItemMap(gi);
        writeToFile(gI);

    }

    /**
     * remove the item with specific itemID
     * @param itemID is the itemID of the item we want to remove
     * @throws InvalidItemException if itemID does not exist in gI
     */

    public void removeItem(String itemID) throws InvalidItemException {
        if (gI.containsKey(itemID)) {
            HashMap<String, Item> gi = gI.getItemMap();
            gi.remove(itemID);
            gI.setItemMap(gi);
            writeToFile(gI);
        }
        else {
            throw new InvalidItemException();
        }}


    private void addItemIdToCollection(String itemID) {
        ArrayList<String> idList = gI.getItemIdCollection();
        idList.add(itemID);
        gI.setItemIdCollection(idList);
        writeToFile(gI);
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



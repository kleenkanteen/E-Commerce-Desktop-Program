package controller_presenter_gateway;

import entities.GlobalInventory;
import entities.Item;
import exceptions.InvalidItemException;
import uses_cases.GlobalInventoryManager;

import java.io.*;

public class GlobalInventoryGateways implements Serializable{
    String filePath;
    GlobalInventoryManager gIManager;
    GlobalInventory gI;
    GlobalInventoryGateways(String filePath) throws IOException {
        this.filePath = filePath;

    try {
        File file = new File(filePath);
        if (file.exists()) {
            readFromFile();
        } else {
            file.createNewFile();
        }
    }
        catch(
    IOException ex) {
        System.out.println("Failed to read");
    }
    gIManager = new GlobalInventoryManager(gI);
    }
    private void readFromFile() {
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            gI = (GlobalInventory) input.readObject();
            input.close();
        } catch (IOException ex) {
            System.out.println("Failed to read");
        } catch (ClassNotFoundException ex) {
            System.out.println("failed to find the class to read");
        }

    }

    private void writeToFile(GlobalInventory gi) {
        try {
            OutputStream file = new FileOutputStream(filePath);

            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(gi);
            output.close();
        } catch (FileNotFoundException ex) {
            System.out.println("No file is found");

        } catch (IOException ex) {
            System.out.println("Filed to write the Object");
        }


    }

    /**
     * Add Item to GlobalInventory and record it to .ser file.
     * No need to worry about the ID, the ID will be automatically generated to Item
     * and sent to ID collection in globalInventory.
     * @param item the item will become available in GlobalInventory.
     */

    public void addItemToGlobalInventory(Item item){
        gIManager.addItemToHashMap(item);
        writeToFile(gI);
    }

    /**
     * Remove Item from GI and record it
     * @param itemID remove the Item with that itemID
     * @throws InvalidItemException if itemID is not in the GlobalInventory
     */

    public void removeItemFromGlobalInventory(String itemID) throws InvalidItemException {
        try{gIManager.removeItem(itemID);
        writeToFile(gI);}
        catch (InvalidItemException ex){
            System.out.println("itemID is not available");
        }
    }

}


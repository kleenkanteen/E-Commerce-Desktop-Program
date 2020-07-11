package B_gateways;

import F_entities.GlobalInventory;
import E_use_cases.GlobalInventoryManager;

import java.io.*;

public class GlobalInventoryGateways implements Serializable{
    String filePath;
    GlobalInventoryManager gIManager;
    GlobalInventory gI;

    /**
     * Creates a new gateway that loads GlobalInventory in a .ser file
     */
    public GlobalInventoryGateways(String filePath){
        this.filePath = filePath;

    try {
        File file = new File(filePath);
        if (file.exists()) {
            readFromFile();
            if(gI == null) {
                gI = new GlobalInventory();
            };
        } else {
            file.createNewFile();
            gI = new GlobalInventory();

        }
    }
        catch(
    IOException ex) {
        System.out.println("Failed to read");
    }
    gIManager = new GlobalInventoryManager(gI);
    }

    /**
     * Deserializes the GlobalInventory object into the program.
     * will assign gI to the GlobalInventory that stored in the program.
     */
    public void readFromFile() {
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

    /**
     * Serialize the GlobalInventory into .ser file
     * @param gi the GlobalInventory Object that we want to store in the .ser file
     *
     */

    public void writeToFile(GlobalInventory gi) {
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
     * getter for GlobalInventory that stored in .ser file.
     * @return the globalInventory that stored in .ser file.
     */

    public GlobalInventory getgI() {
        return gI;
    }
}


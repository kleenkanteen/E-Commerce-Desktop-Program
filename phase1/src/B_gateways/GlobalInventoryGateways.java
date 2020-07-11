package B_gateways;

import F_entities.GlobalInventory;
import E_use_cases.GlobalInventoryManager;

import java.io.*;

public class GlobalInventoryGateways implements Serializable{
    String filePath;
    GlobalInventoryManager gIManager;
    GlobalInventory gI;
    public GlobalInventoryGateways(String filePath){
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

    public GlobalInventory getgI() {
        return gI;
    }
}

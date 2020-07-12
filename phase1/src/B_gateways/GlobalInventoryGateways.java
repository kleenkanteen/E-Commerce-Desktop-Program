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
     * @throws IOException If something is wrong with the filepath or file
     * @throws ClassNotFoundException If the class cannot be found
     */
    public GlobalInventoryGateways(String filePath) throws IOException, ClassNotFoundException{
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
    catch (IOException | ClassNotFoundException ex) {
        System.out.println("Failed to read");
        throw ex;
    }
    gIManager = new GlobalInventoryManager(gI);
    }

    /**
     * Deserializes the GlobalInventory object into the program.
     * will assign gI to the GlobalInventory that stored in the program.
     * @throws IOException If the file cannot be read
     * @throws ClassNotFoundException If the class cannot be found
     */
    public void readFromFile() throws IOException, ClassNotFoundException{
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            gI = (GlobalInventory) input.readObject();
            input.close();
        }
        catch(IOException ex) {
            System.out.println("Input error during deserialization");
            throw ex;
        }
        catch(ClassNotFoundException ex) {
            System.out.println("Class not found exception");
            throw ex;
        }

    }

    /**
     * Serialize the GlobalInventory into .ser file
     * @param gi the GlobalInventory Object that we want to store in the .ser file
     * @throws IOException when an error occur when serializing
     */

    public void writeToFile(GlobalInventory gi) throws IOException{
        try {
            OutputStream file = new FileOutputStream(filePath);

            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(gi);
            output.close();
        }
        catch (IOException ex) {
            System.out.println("Input error during serialization!");
            throw ex;
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


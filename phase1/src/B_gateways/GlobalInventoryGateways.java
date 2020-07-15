package B_gateways;

import F_entities.GlobalInventory;
import E_use_cases.GlobalInventoryManager;

import java.io.*;

public class GlobalInventoryGateways implements Serializable{
    String filePath;
    GlobalInventoryManager gIManager;
    GlobalInventory gI;

    /**
     * Creates a new gateway that loads GlobalInventory in a .ser file\
     * @param filePath filePath of the ser file containing the serialized GlobalInventory
     * @throws IOException If something is wrong with the filepath or file
     * @throws ClassNotFoundException If the class cannot be found
     */
    public GlobalInventoryGateways(String filePath) throws IOException, ClassNotFoundException{
        this.filePath = filePath;

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
        gIManager = new GlobalInventoryManager(gI);
    }

    /**
     * Deserializes the GlobalInventory object into the program.
     * will assign gI to the GlobalInventory that stored in the program.
     * @throws IOException If the file cannot be read
     * @throws ClassNotFoundException If the class cannot be found
     */
    public void readFromFile() throws IOException, ClassNotFoundException{
        InputStream file = new FileInputStream(filePath);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);

        // deserialize the Map
        gI = (GlobalInventory) input.readObject();
        input.close();
    }

    /**
     * Serialize the GlobalInventory into .ser file
     * @param gi the GlobalInventory Object that we want to store in the .ser file
     * @throws IOException when an error occur when serializing
     */

    public void writeToFile(GlobalInventory gi) throws IOException{
        OutputStream file = new FileOutputStream(filePath);

        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        gI = gi;
        output.writeObject(gi);
        output.close();
    }

    /**
     * Getter for GlobalInventory that stored in .ser file.
     * @return the globalInventory that stored in .ser file.
     */
    public GlobalInventory getgI() {
        return gI;
    }
}


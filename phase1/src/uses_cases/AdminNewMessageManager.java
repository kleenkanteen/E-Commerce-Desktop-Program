package uses_cases;

import entities.Item;
import entities.NewItemMessage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the saving and loading of Students.
 */
public class AdminNewMessageManager implements Serializable {

    private static final Logger logger = Logger.getLogger(UniqueIDGeneration.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    /** An ArrayList of all previously issued IDs. */
    private ArrayList<NewItemMessage> newItemData;

    /** A hashmap to store all Item objects and their associated itemIDs. **/
    private HashMap<String, Item> itemMap = new HashMap<String, Item>();

    /**
     * Checks and reads file content.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public AdminNewMessageManager(String newItemMessagesFilePath, String globalInventoryFilePath, String serializedUniqueIDsPath) throws ClassNotFoundException, IOException {
        newItemData = new ArrayList<NewItemMessage>();
        itemMap = new HashMap<String, Item>();

        // Associate the handler with the logger.
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        File file = new File(newItemMessagesFilePath);
        if (file.exists()) {
            readFromFile(newItemMessagesFilePath);
        } else {
            file.createNewFile();
        }

        File file2 = new File(globalInventoryFilePath);
        if (file2.exists()) {
            readFromFile2(globalInventoryFilePath);
        } else {
            file2.createNewFile();
        }
    }

    public void manageNewItemMessages(String newItemMessagesFilePath, String globalInventoryFilePath, String serializedUniqueIDsPath) throws ClassNotFoundException, IOException {
        if (newItemData.size()==0) {
            System.out.println("There are currently no items to approve.");
            return;
        }
        decision(serializedUniqueIDsPath);
        saveToFile(newItemMessagesFilePath);
        saveToFile2(globalInventoryFilePath);
    }

    public void decision(String serializedUniqueIDsPath) throws IOException, ClassNotFoundException {
        String temp = newItemData.get(0).getNewItem().getName();

        System.out.println((newItemData.get(0)).getNewItem().toString());
        System.out.println(newItemData.get(0).getOptions());
        // Get admin input - Either 'confirm' or 'deny'
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Would you like to approve this item for the Global Inventory? Type: \n'confirm' to approve this item\n'deny' to deny this item");
        String input = br.readLine();
        if (input.equals("deny")){
            newItemData.remove(0);
            System.out.println("NewItemMessage deleted and item '" + temp + "' denied from being added to the GI.");
        }
        else if (input.equals("confirm")){
            UniqueIDGeneration temp2 = new UniqueIDGeneration(serializedUniqueIDsPath);
            String uniqueID = temp2.generateID(serializedUniqueIDsPath);
            // TODO: remove next line when finished
            System.out.println("Unique generated ID: " + uniqueID);
            // Set approved item's ItemID to uniqueID String.
            newItemData.get(0).getNewItem().setItemID(uniqueID);

            // TODO: add item to GI
            itemMap.put(uniqueID, newItemData.get(0).getNewItem());

            newItemData.remove(0);
            System.out.println("NewItemMessage deleted and item '" + temp + "' was added to the GI.");
        }
    }

    public void readFromFile(String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            newItemData = (ArrayList<NewItemMessage>) input.readObject();

            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }

    public void readFromFile2(String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            itemMap = (HashMap<String, Item>) input.readObject();
            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }

    // for debugging only
    public void addNewItemMessage(String filePath, NewItemMessage toAdd){
        newItemData.add(newItemData.size(), toAdd);
    }

    /**
     * Writes the ID to file at filePath.
     *
     * @param filePath the file to write the records to
     * @throws IOException
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(newItemData);
        output.close();
    }

    public void saveToFile2(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(itemMap);
        output.close();
    }

    @Override
    public String toString() {
        return newItemData.toString();
    }
}

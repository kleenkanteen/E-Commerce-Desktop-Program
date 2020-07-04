package uses_cases;

import entities.FreezeRequestMessage;
import entities.Item;
import entities.NewItemMessage;
import entities.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminMessageManager implements Serializable {
    private static final Logger logger = Logger.getLogger(AdminMessageManager.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    public AdminMessageManager() throws IOException, ClassNotFoundException {


        String serializedNewItemRequests = "src/ser_file_infos/serializedNewItemRequests.ser";
        String serializedGlobalInventory = "src/ser_file_infos/serializedGI.ser";
        String serializedUniqueIDs = "src/ser_file_infos/serializedAdminMessages.ser";
        String serializedFreezeRequests = "src/ser_file_infos/serializedFreezeRequests.ser";
        String userAccountPath = "src/ser_file_infos/userAccountPath.ser";

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals("exit")) {
            System.out.println("Type 1 to manage NewItemMessages\nType 2 to manually add a new NewItemMessages\nType 3 to view the Global Inventory and list of NewItemMessages to be approved or denied.\nType 'exit' to exit the program.");
            try {
                input = br.readLine();
                if (input.equals("1")) {
                    AdminNewMessageManager temp = new AdminNewMessageManager(serializedNewItemRequests, serializedGlobalInventory, serializedUniqueIDs);
                    temp.manageNewItemMessages(serializedNewItemRequests, serializedGlobalInventory, serializedUniqueIDs);
                    temp.saveToFile(serializedNewItemRequests);
                    readGlobalInv(serializedGlobalInventory);
                    readNewItemRequests(serializedNewItemRequests);
                    //AdminAccountManagement temp = new AdminAccountManagement();
                    //readFromFile(serializedAdminData);
                } else if (input.equals("2")) {
                    AdminNewMessageManager temp = new AdminNewMessageManager(serializedNewItemRequests, serializedGlobalInventory, serializedUniqueIDs);
                    System.out.println("Enter message content:");
                    String content = br.readLine();
                    System.out.println("Enter item name");
                    String item = br.readLine();
                    System.out.println("Enter owner name");
                    String owner = br.readLine();
                    System.out.println("Enter item description");
                    String description = br.readLine();
                    temp.addNewItemMessage(serializedNewItemRequests, new NewItemMessage(content, new Item(item, owner, description )));
                    System.out.println("Item " + item + " was added to the list of items to be approved or disapproved for the Global Inventory.");
                    readGlobalInv(serializedGlobalInventory);
                    temp.saveToFile(serializedNewItemRequests);
                    readNewItemRequests(serializedNewItemRequests);
                } else if (input.equals("3")) {
                    readGlobalInv(serializedGlobalInventory);
                    readNewItemRequests(serializedNewItemRequests);
                } else if (input.equals("4")) {
                    //TODO: this is nonfunctional right now
                    AdminFreezeRequestManager temp = new AdminFreezeRequestManager(serializedFreezeRequests, userAccountPath);
                    temp.addNewFreezeRequest(serializedFreezeRequests, new FreezeRequestMessage("help", new User("userman", "pass")));
                    AdminFreezeRequestManager temp2 = new AdminFreezeRequestManager(serializedFreezeRequests, userAccountPath);
                    System.out.println("now what");
                }
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
    }

    public void readNewItemRequests(String serializedNewItemRequests){
        try {
            InputStream file = new FileInputStream(serializedNewItemRequests);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            ArrayList<NewItemMessage> temp;

            // deserialize the Map
            temp = (ArrayList<NewItemMessage>) input.readObject();
            System.out.println("NewItemMessages (to be approved/ disapproved): " + temp);
            input.close();
        } catch (IOException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }
    public void readGlobalInv(String serializedGlobalInventory){
        try {
            InputStream file = new FileInputStream(serializedGlobalInventory);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            HashMap<String, Item> temp;

            // deserialize the Map
            temp = (HashMap<String, Item>) input.readObject();
            System.out.println("Global Inventory: " + temp);
            input.close();
        } catch (IOException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }
}


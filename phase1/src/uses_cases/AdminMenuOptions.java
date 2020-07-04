package uses_cases;

import entities.Admin;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AdminMenuOptions implements Serializable {
    private static final Logger logger = Logger.getLogger(AdminLogin.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    /** An arraylist to store all admin objects. **/
    private ArrayList<Admin> adminAccountInformation;

    String serializedAdminData = "src/ser_file_infos/serializeAdminData.ser";
    String serializedUniqueIDs = "src/ser_file_infos/serializedAdminMessages.ser";

    public AdminMenuOptions(String filePath) throws ClassNotFoundException, IOException {

        adminAccountInformation = new ArrayList<Admin>();

        // Associate the handler with the logger.
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        File file = new File(serializedAdminData);
        if (file.exists()) {
            readFromFile(serializedAdminData);
        } else {
            file.createNewFile();
        }

        System.out.println("Initial Status:\nAdmins: " + adminAccountInformation);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals("exit")) {
            System.out.println("Type 1 to add, remove or modify an admin account.\nType 2 to view and respond to admin messages.\nType 3 to increase ID value.\nType 4 to DEBUG.\nType 'exit' to exit the program.");
            try {
                input = br.readLine();
                if (input.equals("1")) {
                    AdminAccountManagement temp = new AdminAccountManagement();
                    readFromFile(serializedAdminData);
                } else if (input.equals("2")) {
                    AdminMessageManager x = new AdminMessageManager();
                } else if (input.equals("3")) {
                    System.out.println("nothing yet");
                } else if (input.equals("4")) {
                    System.out.println("also nothing yet");

                }
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
        System.out.println("Final Status:\nAdmins: " + adminAccountInformation);
    }

    public void readFromFile(String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            adminAccountInformation = (ArrayList<Admin>) input.readObject();
            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }
}
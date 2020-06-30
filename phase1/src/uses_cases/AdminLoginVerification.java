package uses_cases;

import entities.Admin;
import uses_cases.AdminAccountManager;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminLoginVerification {
    // TODO: explain logger
    private static final Logger logger = Logger.getLogger(AdminAccountManager.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    /** An arraylist of all admin objects. */
    private ArrayList<Admin> adminAccountInformation;

    public AdminLoginVerification(String serializedAdminManagerAccountInfo) throws ClassNotFoundException, IOException {
        adminAccountInformation = new ArrayList<>();

        // Associate the handler with the logger.
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        File file = new File(serializedAdminManagerAccountInfo);
        if (file.exists()) {
            readFromFile(serializedAdminManagerAccountInfo);
        } else {
            file.createNewFile();
        }
    }

    public Admin login(String usernameEntry, String passwordEntry) {
        for (Admin r : adminAccountInformation) {
            if ((r.getUsername().equals(usernameEntry)) && r.getPassword().equals(passwordEntry)) {
                System.out.println("successful login");
                return r;
            }
        }
        System.out.println("wrong login");
        Admin lol = null;
        return lol;
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

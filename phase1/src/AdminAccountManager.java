package com.jetbrains;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages saving and loading in admins
 */

public class AdminAccountManager implements Serializable {
    // TODO: explain logger
    private static final Logger logger = Logger.getLogger(AdminAccountManager.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    /** An arraylist of all admin objects. */
    private ArrayList<Admin> adminAccountInformation;

    /**
     *
     * @param serializedAdminManagerAccountInfo
     */

    public AdminAccountManager(String serializedAdminManagerAccountInfo) throws ClassNotFoundException, IOException {
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

    public void add(Admin newAdmin) {
        for (Admin r : adminAccountInformation) {
            if ((r.getUsername().equals(newAdmin.getUsername())) && (r.getPassword().equals(newAdmin.getPassword()))){
                System.out.println("This admin account already exists in the list of admins and could not be added.");
                return;
            }
        }
        adminAccountInformation.add(newAdmin);

        // Log the addition of an admin.
        logger.log(Level.INFO, "Added a new admin " + newAdmin.toString());
    }

    // the remove() method is for debugging only
    public void remove(Admin adminAccount) {
        List<Admin> toRemove = new ArrayList();
        for (Admin r : adminAccountInformation) {
            if (r.getUsername().equals(adminAccount.getUsername()) && (r.getPassword().equals(adminAccount.getPassword()))){
                toRemove.add(r);
            }
        }
        adminAccountInformation.removeAll(toRemove);

        // Log the removal of a message.
        logger.log(Level.INFO, "Removed an admin Account " + adminAccount.toString());
    }

    public Admin login(String usernameEntry, String passwordEntry) {
        for (Admin r : adminAccountInformation) {
            if ((r.getUsername() == usernameEntry) && r.getPassword() == passwordEntry) {
                System.out.println("successful login");
                return r;
            }
        }
        System.out.println("wrong login");
        Admin lol = null;
        return lol;
    }

    public void saveToFile(String serializedManagerInfo) throws IOException {

        OutputStream file = new FileOutputStream(serializedManagerInfo);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(adminAccountInformation);
        output.close();
    }

    @Override
    public String toString() {
        String result = "";
        for (Admin r : adminAccountInformation) {
            System.out.println(r.getUsername());
        }
        return result;
    }
}

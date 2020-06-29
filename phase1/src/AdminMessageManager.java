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
 * Manages saving and loading in of admin messages
 */
public class AdminMessageManager {
    // TODO: explain logger
    private static final Logger logger = Logger.getLogger(AdminAccountManager.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    /** An arraylist of all admin message obejcts. */
    private ArrayList<Message> adminMessages;

    public AdminMessageManager(String serializedAdminManagerMessageInfo) throws ClassNotFoundException, IOException {
        adminMessages = new ArrayList<>();

        // Associate the handler with the logger.
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        File file = new File(serializedAdminManagerMessageInfo);
        if (file.exists()) {
            readFromFile(serializedAdminManagerMessageInfo);
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
            adminMessages = (ArrayList<Message>) input.readObject();
            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }

    public void add(Message newMessage) {
        adminMessages.add(newMessage);

        // Log the addition of a message.
        logger.log(Level.INFO, "Added a new message " + newMessage.toString());
    }

    public void remove(Message newMessage) {
        List<Message> toRemove = new ArrayList();
        for (Message r : adminMessages) {
            if (r.getMessage().equals(newMessage.getMessage())){
                toRemove.add(r);
                break;
            }
        }
        adminMessages.removeAll(toRemove);

        // Log the removal of a message.
        logger.log(Level.INFO, "Removed a new message " + newMessage.toString());
    }

    public void saveToFile(String serializedMessageInfo) throws IOException {

        OutputStream file = new FileOutputStream(serializedMessageInfo);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(adminMessages);
        output.close();
    }

    @Override
    public String toString() {
        String result = "";
        for (Message r : adminMessages) {
            System.out.println(r.getMessage());
        }
        return result;
    }






}

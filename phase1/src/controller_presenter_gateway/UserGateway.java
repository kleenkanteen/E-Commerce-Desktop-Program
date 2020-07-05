package controller_presenter_gateway;

import entities.User;

import java.io.*;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.User;
import uses_cases.UserManager;

public class UserGateway {
    HashMap<String, User> mapOfUsers;
    private static final Logger logger = Logger.getLogger(UserManager.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    /**
     * Creates a new gateway that loads in the HashMap of user objects for an .ser file.
     * @param filepath the directory where the .ser file is stored
     */
    public UserGateway(String filepath) {
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        try {
            File file = new File(filepath);
            if (file.exists()) {
                this.mapOfUsers = readFromFile(filepath);
            } else {
                file.createNewFile();
            }
        }
        catch(IOException ex) {
            logger.log(Level.SEVERE, "Input error while creating UserManager!", ex);
            //System.out.println("Input error!");
        }
    }
    /**
     * Deserializes the arraylist of user objects into the program.
     * @param filepath Filepath to the .ser file storing the User objects.
     * @return the hashmap of user objects
     */
    public HashMap<String, User> readFromFile(String filepath) {
        HashMap<String, User> userObjects = new HashMap<>();
        try {
            // load in the objects
            InputStream file = new FileInputStream(filepath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the hashmap of user objects
            try {
                userObjects = (HashMap<String, User>) input.readObject();
                input.close();
                return userObjects;
            }
            catch(ClassCastException ex) {
                logger.log(Level.SEVERE, "Casting a weird object as the hashmap in UserManager.", ex);
                // System.out.println("Casting a weird object as the hashmap in UserManager.");
            }
        }
        catch(IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input during deserialization.", ex);
            // System.out.println("Input error during deserialization!");
        }
        catch(ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "Class not found exception.", ex);
            // System.out.println("Class not found exception!");
        }
        return userObjects;
    }

    /**
     * Serializes the arraylist of user objects.
     * @param filepath where this file will be stored
     */
    public void writeToFile(String filepath, HashMap<String, User> userObjects) {
        try {
            // load allUsers onto the file at designed path
            FileOutputStream file = new FileOutputStream(filepath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(buffer);

            // serialize objects
            output.writeObject(userObjects);
            output.close();
        }
        catch(IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input during serialization.", ex);
            // System.out.println("Input error during serialization!");
        }
    }

    public HashMap<String, User> getMapOfUsers() {return this.mapOfUsers; }
}

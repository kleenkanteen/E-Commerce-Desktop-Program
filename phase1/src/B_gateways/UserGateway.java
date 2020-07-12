package B_gateways;

import F_entities.User;

import java.io.*;
import java.util.HashMap;

public class UserGateway {
    HashMap<String, User> mapOfUsers;

    /**
     * Creates a new gateway that loads in the HashMap of user objects for an .ser file.
     * @param filepath the directory where the .ser file is stored
     * @throws IOException If the path is not valid or an error happened when reading or writing the file
     * @throws ClassNotFoundException If the class cannot be found
     */
    public UserGateway(String filepath) throws IOException, ClassNotFoundException{
        try {
            File file = new File(filepath);
            if (file.exists()) {
                this.mapOfUsers = readFromFile(filepath);
            } else {
                file.createNewFile();
            }
            if(this.mapOfUsers == null) {
                this.mapOfUsers = new HashMap<>();
            }
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println("Failed to read");
            throw ex;
        }
    }
    /**
     * Deserializes the arraylist of user objects into the program.
     * @param filepath Filepath to the .ser file storing the User objects.
     * @return the hashmap of user objects
     * @throws IOException If the file cannot be read
     * @throws ClassNotFoundException If the class cannot be found
     */
    public HashMap<String, User> readFromFile(String filepath) throws IOException, ClassNotFoundException{
        HashMap<String, User> userObjects = new HashMap<>();
        try {
            // load in the objects
            InputStream file = new FileInputStream(filepath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the hashmap of user objects
            userObjects = (HashMap<String, User>) input.readObject();
            input.close();
            return userObjects;
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
     * Serializes the arraylist of user objects.
     * @param filepath where this file will be stored
     * @throws IOException when an error occur when serializing
     */
    public void writeToFile(String filepath, HashMap<String, User> userObjects) throws IOException{
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
            System.out.println("Input error during serialization!");
            throw ex;
        }
    }

    /**
     * Returns the now deserialized map of user objects
     * @return the HashMap of user objects
     */
    public HashMap<String, User> getMapOfUsers() { return this.mapOfUsers; }
}

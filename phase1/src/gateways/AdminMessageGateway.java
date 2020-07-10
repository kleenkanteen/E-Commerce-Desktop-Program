package gateways;

import entities.Message;

import java.io.*;
import java.util.ArrayList;

public class AdminMessageGateway {
    ArrayList<Message> messages;

    /**
     * Creates a new gateway that loads in the Arraylist of Message objects in a .ser file
     * @param filepath the directory where the .ser file is stored
     */
    public AdminMessageGateway(String filepath) {
        try {
            File file = new File(filepath);
            if (file.exists()) {
                this.messages = readFromFile(filepath);
            } else {
                file.createNewFile();
            }
        }
        catch(IOException ex) {
            System.out.println("Input error!");
        }
    }
    /**
     * Deserializes the arraylist of user objects into the program.
     * @param filepath Filepath to the .ser file storing the User objects.
     * @return the arraylist of Messages an Admin can respond to.
     */
    public ArrayList<Message> readFromFile(String filepath) {
        ArrayList<Message> messages2 = new ArrayList<>();
        try {
            // load in the objects
            InputStream file = new FileInputStream(filepath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the hashmap of user objects
            try {
                messages2 = (ArrayList<Message>) input.readObject();
                input.close();
                return messages2;
            }
            catch(ClassCastException ex) {
                System.out.println("Casting to improper subclass in AdminMessageGateway.");
            }
        }
        catch(IOException ex) {
            System.out.println("Input error during deserialization.");
        }
        catch(ClassNotFoundException ex) {
            System.out.println("Class could not be found.");
        }
        return messages2;
    }

    /**
     * Serializes the arraylist of Message objects.
     * @param filepath where this file will be stored
     */
    public void writeToFile(String filepath, ArrayList <Message> adminMessages) {
        try {
            // load allUsers onto the file at designed path
            FileOutputStream file = new FileOutputStream(filepath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(buffer);

            // serialize objects
            output.writeObject(adminMessages);
            output.close();
        }
        catch(IOException ex) {
            System.out.println("Input error during serialization!");
        }
    }

    public ArrayList<Message> getMessages() {
        return this.messages;
    }
}

package controller_presenter_gateway;

import entities.Trade;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UserTradesGateway {

    HashMap<String, ArrayList<Trade>> userTrades;

    /**
     * Creates a new gateway that loads in the HashMap of users and their respective trades for an .ser file.
     * @param filepath the directory where the .ser file is stored
     */
    public UserTradesGateway(String filepath) {
        try {
            File file = new File(filepath);
            if (file.exists()) {
                this.userTrades = readFromFile(filepath);
            } else {
                file.createNewFile();
            }
        }
        catch(IOException ex) {
            System.out.println("Input error!");
        }
    }
    /**
     * Deserializes the HashMap of usernames and their trades
     * @param filepath Filepath to the .ser file storing the usernames and their trades
     * @return the HashMap of usernames and their trades
     */
    public HashMap<String, ArrayList<Trade>> readFromFile(String filepath) {
        HashMap<String, ArrayList<Trade>> userTrades2 = new HashMap<>();
        try {
            // load in the objects
            InputStream file = new FileInputStream(filepath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the hashmap of user objects
            try {
                userTrades2 = (HashMap<String, ArrayList<Trade>>) input.readObject();
                input.close();
                return userTrades2;
            }
            catch(ClassCastException ex) {
                System.out.println("Casting a weird object as the hashmap in UserTradesGateway.");
            }
        }
        catch(IOException ex) {
            System.out.println("Input error during deserialization!");
        }
        catch(ClassNotFoundException ex) {
            System.out.println("Class not found exception!");
        }
        return userTrades2;
    }

    /**
     * Serializes the HashMap of usernames and their trades
     * @param filepath where this file will be stored
     */
    public void writeToFile(String filepath, HashMap<String, ArrayList<Trade>> userTrades3) {
        try {
            // load allUsers onto the file at designed path
            FileOutputStream file = new FileOutputStream(filepath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(buffer);

            // serialize objects
            output.writeObject(userTrades3);
            output.close();
        }
        catch(IOException ex) {
            System.out.println("Input error during serialization!");
        }
    }

    public HashMap<String, ArrayList<Trade>> getUserTrades() {
        return userTrades;
    }
}

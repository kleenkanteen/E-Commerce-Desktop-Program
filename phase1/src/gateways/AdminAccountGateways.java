package gateways;

import entities.Admin;
import use_cases.AdminManager;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class AdminAccountGateways {
    String filePath;
    HashMap<String, Admin> adminMap = new HashMap<>();

    /**
     * create a gateways that loads a HashMap of Admin with UserName as key.
     * @param filePath the directory where the .ser file is stored
     */

    public AdminAccountGateways(String filePath) {
        this.filePath = filePath;

        try {
            File file = new File(filePath);
            if (file.exists()) {
                readFromFile();
            } else {
                file.createNewFile();
            }
        } catch (
                IOException | ClassNotFoundException ex) {
            System.out.println("Failed to read");
        }

    }
    public void readFromFile() throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            adminMap = (HashMap<String, Admin>) input.readObject();
            input.close();

        } catch (IOException ex) {
            System.out.println("Failed to read");
        } catch (ClassNotFoundException ex) {
            System.out.println("failed to find the class to read");
        }
    }

    public void saveToFile(HashMap<String, Admin> adminMap) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(adminMap);
        output.close();
    }

    public HashMap<String, Admin> getAdminMap() {
        return adminMap;
    }
}

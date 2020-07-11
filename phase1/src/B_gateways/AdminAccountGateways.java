package B_gateways;

import F_entities.Admin;

import java.io.*;
import java.util.HashMap;

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

    /**
     * Deserializes the hashmap of Admin into the program.
     * will assign adminMap to the hashmap which stores Admin with UserName as key
     */
    public void readFromFile() throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            adminMap = (HashMap<String, Admin>) input.readObject();
            input.close();

        } catch (IOException ex) {
            System.out.println("Failed to read");
        } catch (ClassNotFoundException ex) {
            System.out.println("failed to find the class to read");
        }
    }

    /**
     * Serialize the HashMap of admin into .ser file
     * @param adminMap the HashMap we want to use to store AdminAccountInformation
     * @throws IOException when failed to serialize into .ser file
     *
     */

    public void saveToFile(HashMap<String, Admin> adminMap) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(adminMap);
        output.close();
    }

    /**
     * getter for HashMap of Admin that stored in .ser file.
     * @return the HashMap of Admin that stored in .ser file.
     */

    public HashMap<String, Admin> getAdminMap() {
        return adminMap;
    }

    public void setAdminMap(HashMap<String, Admin> adminMap) {
        this.adminMap = adminMap;
    }
}

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
     * @throws IOException If something is wrong with the filepath or file
     * @throws ClassNotFoundException If the class cannot be found
     */

    public AdminAccountGateways(String filePath) throws IOException, ClassNotFoundException{
        this.filePath = filePath;

        try {
            File file = new File(filePath);
            if (file.exists()) {
                readFromFile();
                if(adminMap == null){
                    adminMap = new HashMap<>();
                }
            } else {
                file.createNewFile();
                adminMap = new HashMap<>();
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Failed to read");
            throw ex;
        }
    }

    /**
     * Deserializes the hashmap of Admin into the program.
     * will assign adminMap to the hashmap which stores Admin with UserName as key
     * @throws IOException If the file cannot be read
     * @throws ClassNotFoundException If the class cannot be found
     */
    public void readFromFile() throws IOException, ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            adminMap = (HashMap<String, Admin>) input.readObject();
            input.close();

        } catch (IOException ex) {
            System.out.println("Input error during deserialization");
            throw ex;
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception");
            throw ex;
        }
    }

    /**
     * Serialize the HashMap of admin into .ser file
     * @param adminMap the HashMap we want to use to store AdminAccountInformation
     * @throws IOException when an error occur when serializing
     */

    public void saveToFile(HashMap<String, Admin> adminMap) throws IOException {

        try {
            OutputStream file = new FileOutputStream(filePath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            // serialize the Map
            output.writeObject(adminMap);
            output.close();
        }
        catch(IOException ex) {
        System.out.println("Input error during serialization!");
        throw ex;
    }
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

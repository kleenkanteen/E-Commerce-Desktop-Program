package uses_cases;
import entities.Admin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminAccountManagement {
    private static final Logger logger = Logger.getLogger(AdminLogin.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    /** An arraylist to store all admin objects. **/
    private ArrayList<Admin> adminAccountInformation;

    String serializedAdminData = "src/ser_file_infos/serializeAdminData.ser";

    public AdminAccountManagement() throws IOException, ClassNotFoundException {

        adminAccountInformation = new ArrayList<Admin>();

        // Associate the handler with the logger.
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        File file = new File(serializedAdminData);
        if (file.exists()) {
            readFromFile(serializedAdminData);
        } else {
            file.createNewFile();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals("exit")) {
            System.out.println("Type 1 to add an account.\nType 2 to remove an account\nType 3 to change an account's password.\nType 'exit' to exit the program.");
            try {
                input = br.readLine();
                if (input.equals("1")) {
                    System.out.println("Enter username:");
                    String user = br.readLine();
                    System.out.println("Enter password");
                    String pass = br.readLine();
                    addAdmin(new Admin(user, pass));
                } else if (input.equals("2")) {
                    System.out.println("Enter username:");
                    String user = br.readLine();
                    removeAdmin(new Admin(user, "temp"));
                } else if (input.equals("3")) {
                    System.out.println("Enter username:");
                    String user = br.readLine();
                    System.out.println("Enter current password");
                    String currPass = br.readLine();
                    System.out.println("Enter new password");
                    String newPass = br.readLine();
                    changePassword(new Admin(user, currPass), newPass);
                } else if (input.equals("4")) {

                }
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
            saveToFile(serializedAdminData);
        }
        System.out.println("Admins: " + adminAccountInformation);
    }

    public void addAdmin(Admin newAdmin) {
        for (Admin r : adminAccountInformation) {
            if ((r.getUsername().equals(newAdmin.getUsername()))){
                System.out.println("This admin account already exists in the list of admins and could not be added.");
                System.out.println("Admins: " + adminAccountInformation);
                return;
            }
            Admin temp = new Admin("System Messages", "pw");
            if (newAdmin.getUsername().equals(temp.getUsername())) {
                System.out.println("This username is not permitted");
                System.out.println("Admins: " + adminAccountInformation);
                return;
            }
        }
        adminAccountInformation.add(newAdmin);

        // Log the addition of an admin.
        logger.log(Level.INFO, "Added a new admin " + newAdmin.toString());
        System.out.println("Current Admins: " + adminAccountInformation);
    }

    // the remove() method is for debugging only
    public void removeAdmin(Admin adminAccount) {
        List<Admin> toRemove = new ArrayList();
        int counter = 0;
        for (Admin r : adminAccountInformation) {
            if (r.getUsername().equals(adminAccount.getUsername())){
                toRemove.add(r);
            }
            counter++;
        }
        if (counter>1){
            adminAccountInformation.removeAll(toRemove);
            // Log the removal of a message.
            logger.log(Level.INFO, "Removed an admin Account " + adminAccount.toString());
            System.out.println("Admins: " + adminAccountInformation);
        }
        else {
            System.out.println("There must be at least 1 admin in the system.");
            System.out.println("Current Admins: " + adminAccountInformation);
        }
    }

    public void changePassword(Admin adminAccount, String newPass) {
        for (Admin r : adminAccountInformation) {
            if ((r.getUsername().equals(adminAccount.getUsername())) && r.getPassword().equals(adminAccount.getPassword())) {
                r.setPassword(newPass);
                // Log the changing of a password
                logger.log(Level.INFO, "Changed password for the admin account " + adminAccount.toString());
                return;
            }
        }
        logger.log(Level.INFO, "Username and password not found for the admin account " + adminAccount.toString() + ". Did not change the password");
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

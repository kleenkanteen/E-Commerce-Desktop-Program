package uses_cases;

import entities.Admin;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminLogin {
    private static final Logger logger = Logger.getLogger(AdminLogin.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    /** An arraylist to store all admin objects. **/
    private ArrayList<Admin> adminAccountInformation;


    String serializedAdminData = "src/ser_file_infos/serializeAdminData.ser";

    public AdminLogin() throws IOException, ClassNotFoundException {
        run();
    }
    public void run() throws IOException, ClassNotFoundException {
        adminAccountInformation = new ArrayList<>();

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
        loginPrompt();
    }

    public Admin loginPrompt() throws IOException, ClassNotFoundException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter username:");
        String user = br.readLine();
        System.out.println("Enter password");
        String pass = br.readLine();
        // see if valid username and password. if login(user, pass) returns null, invalid login)
        // if valid login, run demoAdminManager()

        Admin temp = login(user, pass);
        if (temp != null)
            new AdminMenuOptions(serializedAdminData);
        return temp;
    }

    public Admin login(String usernameEntry, String passwordEntry) {
        for (Admin r : adminAccountInformation) {
            if ((r.getUsername().equals(usernameEntry)) && r.getPassword().equals(passwordEntry)) {
                System.out.println("Successful login.");
                return r;
            }
        }
        System.out.println("Invalid login");
        Admin loginAttempt = null;
        return loginAttempt;
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
}
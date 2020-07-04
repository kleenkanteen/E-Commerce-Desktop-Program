package uses_cases;

import entities.FreezeRequestMessage;
import entities.User;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the saving and loading of Students.
 */
public class AdminFreezeRequestManager implements Serializable {

    private static final Logger logger = Logger.getLogger(UniqueIDGeneration.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    /** An ArrayList of all present FreezeRequestMessages. */
    private ArrayList<FreezeRequestMessage> freezeRequestData;

    /** An ArrayList of all users. */
    private ArrayList<User> users;


    /**
     * Checks and reads file content.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public AdminFreezeRequestManager(String freezeRequestsPath, String usersPath) throws ClassNotFoundException, IOException {
        freezeRequestData = new ArrayList<FreezeRequestMessage>();
        users = new ArrayList<User>();

        // Associate the handler with the logger.
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        File file = new File(freezeRequestsPath);
        if (file.exists()) {
            readFromFile(freezeRequestsPath);
        } else {
            file.createNewFile();
        }

        File file3 = new File(usersPath);
        if (file3.exists()) {
            readFromFile2(usersPath);
        } else {
            file3.createNewFile();
        }
        manageNewItemMessages(freezeRequestsPath, usersPath);
    }

    public void manageNewItemMessages(String newItemMessagesFilePath, String usersPath) throws ClassNotFoundException, IOException {
        if (freezeRequestData.size()==0) {
            System.out.println("There are currently no Freeze Requests to approve or disapprove.");
            return;
        }
        decision(newItemMessagesFilePath, usersPath);
        saveToFile(newItemMessagesFilePath);
        saveToFile2(usersPath);
    }

    //TODO: add userlist, comments

    public void decision(String newItemMessagesFilePath, String usersPath) throws IOException, ClassNotFoundException {
        String temp = freezeRequestData.get(0).getSender();

        System.out.println((freezeRequestData.get(0)).toString());
        System.out.println(freezeRequestData.get(0).getOptions());
        // Get admin input - Either 'confirm' or 'deny'
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Would you like to freeze this user account? Type: \n'freeze' to freeze this user\n'unfreeze' to unfreeze this user.");
        String input = br.readLine();
        if (input.equals("unfreeze")){
            User toUnfreeze = freezeRequestData.get(0).getUser();
            toUnfreeze.setFrozenStatus(false);
            freezeRequestData.remove(0);
            System.out.println("FreezeRequestMessage deleted and user '" + temp + "'s account has been unfrozen.");
        }
        else if (input.equals("freeze")){
            User toFreeze = freezeRequestData.get(0).getUser();
            toFreeze.setFrozenStatus(true);
            freezeRequestData.remove(0);
            System.out.println("FreezeRequestMessage deleted and user '" + temp + "'s account has been frozen.");
        }
    }

    public void readFromFile(String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            freezeRequestData = (ArrayList<FreezeRequestMessage>) input.readObject();

            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }

    public void readFromFile2(String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            users = (ArrayList<User>) input.readObject();

            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }

    // for debugging only
    public void addNewFreezeRequest(String filePath, FreezeRequestMessage toAdd){
        freezeRequestData.add(freezeRequestData.size(), toAdd);
    }

    /**
     * Writes the ID to file at filePath.
     *
     * @param filePath the file to write the records to
     * @throws IOException
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(freezeRequestData);
        output.close();
    }

    public void saveToFile2(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(users);
        output.close();
    }
    @Override
    public String toString() {
        return freezeRequestData.toString();
    }
}

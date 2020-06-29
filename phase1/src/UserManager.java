import java.util.ArrayList;
import java.lang.System;
import java.util.Scanner;
import java.util.HashMap;
import java.io.*;

public class UserManager implements Serializable{
    private Hashmap<String, User> allUsers;

    /**
     * Creates a UserManager object. Final implementation will vary later (how to serialize? deserialize?)
     */
    public UserManager() {
        this.allUsers = new Hashmap<String, User>();
    }

    /**
     * Deserializes the arraylist of user objects into the program.
     * @param filepath Filepath to the .ser file storing the User objects.
     */
    public void readFromFile(String filepath) {
        try {
            // load in the objects
            InputStream file = new FileInputStream(filepath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the arraylist of user objects
            allUsers = (Hashmap<String, User>) input.readObject();
            input.close();
        }
        catch(IOException ex) {
            System.out.println("Input error during deserialization!");
        }
    }

    /**
     * Serializes an the arraylist of user objects
     * @param filepath where this file will be stored
     */
    public void writeToFile(String filepath) {
    }
        try {
        FileOutputStream file = new FileOutputStream(filepath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutputStream output = new ObjectOutputStream(buffer);

        output.writeObject(allUsers);
        output.close();
    }
        catch(IOException ex) {
        System.out.println("Input error during serialization!");
    }
}

    /**
     * Takes in username, password, finds the right user name and returns it.
     * If the username does not match up with password, throw InvalidLoginException.
     * Put this method in a try-catch!!!
     * @param username String username
     * @param password String password
     * @return the relevant User object
     * @throws InvalidLoginException an invalid login
     */
    public User login(String username, String password) throws InvalidLoginException {
        // check username
        if(this.allUsers.containsKey(username)) {
            // check password
            if(password == this.allUsers.get(username).getPassword()) {
                return this.allUsers.get(username);
            }
        }
        throw new InvalidLoginException;
    }

    /**
     * Takes in a newly created User object and adds it to the list of users.
     * All usernames are unique.
     * Returns true if the user successfully created, false if there's another user with the same username.
     * @param userName the new user's username
     * @param password new user's chosen password
     * @throws InvalidUsernameException username is already taken
     */
    public void createNewUser(String userName, String password) throws InvalidUsernameException{
        /*
        for(User user : this.allUsers) {
            //check to see if this username already exists, if yes then:
            throw new InvalidUsernameException;
        }
        this.allUsers.add(newUser);
        */
    }

    /**
     * Admin functionality?
     * Removes the selected user.
     * Returns true if the user successfully removed, false if the user is not in the list of users.
     * @return True if user removed, False if user is not in list of users.
     * @param userName String of username
     */
    public boolean removeUser(String userName) {
        /*
        if(get index of the user object with the username) {
            this.allUsers.remove(this.allUsers.index(user));
            return true;
        }
        return false;
        */
    }

    /**
     * Return a user's personal inventory.
     * @param user String username
     * @return Arraylist of user's items
     */
    // public ArrayList<Item> getUserInventory (String user) { }

    /**
     * ARGUMENTS ARE NOT FINAL, JUST PLACEHOLDERS.
     * Removes an item from a user's personal inventory.
     * @param user String username
     * @param itemName String item name
     * @param itemID String item ID
     * @return True if item removed, False if item not in inventory or some other error comes up.
     */
    // public boolean removeItemFromUserInventory (String user, String itemName, String itemID) { }

    // Below functions are ADMIN ONLY, used by AdminManager.

    /**
     * ADMIN ONLY
     * Allows an admin to either freeze an unfrozen user account or unfreeze a frozen user account.
     * @param userName the User object in question.
     */
    public void freezeUserAccount(String userName) {
        User selectedUser; //get index of user object with the username
        if (selectedUser.isFrozen()) {
            selectedUser.setFrozen(false);
        }
        else {
            selecteduser.setFrozen(true);
        }
    }
}
package uses_cases;

import entities.User;
import entities.Item;
import exceptions.InvalidLoginException;
import exceptions.InvalidUsernameException;
import exceptions.UserFrozenException;
import entities.PermTrade;
import entities.TempTrade;
import entities.Trade;
import entities.Message;
import entities.NewItemMessage;
import java.util.ArrayList;
import java.lang.System;
import java.util.Scanner;
import java.util.HashMap;
import java.io.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserManager implements Serializable{
    private static final Logger logger = Logger.getLogger(UserManager.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();
    private HashMap<String, User> allUsers;
    // String username;
    // User currentUser;

    /*
    public UserManager(String username, HashMap<String, User> allUsers) {
        this.allUsers = allUsers;
        this.username = username;
    }
     */

    /**
     * Creates a UserManager object.
     * @param filepath Filepath to the .ser file storing the User objects
     */
    public UserManager(String filepath) {
        // create new file and check to see if file already exists
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        this.allUsers = new HashMap<>();
        try {
            File file = new File(filepath);
            if (file.exists()) {
                readFromFile(filepath);
            } else {
                file.createNewFile();
            }
        }
        catch(IOException ex) {
            logger.log(Level.SEVERE, "Input error while creating UserManager!", ex);
            //System.out.println("Input error!");
        }
    }

    // SERIALIZATION/DESERIALIZATION

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

            // deserialize the hashmap of user objects
            try {
                this.allUsers = (HashMap<String, User>) input.readObject();
                input.close();
            }
            catch(ClassCastException ex) {
                logger.log(Level.SEVERE, "Casting a weird object as the hashmap in UserManager.", ex);
                // System.out.println("Casting a weird object as the hashmap in UserManager.");
            }
        }
        catch(IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input during deserialization.", ex);
            // System.out.println("Input error during deserialization!");
        }
        catch(ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "Class not found exception.", ex);
            // System.out.println("Class not found exception!");
        }
    }

    /**
     * Serializes the arraylist of user objects.
     * @param filepath where this file will be stored
     */
    public void writeToFile(String filepath) {
        try {
            // load allUsers onto the file at designed path
            FileOutputStream file = new FileOutputStream(filepath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(buffer);

            // serialize objects
            output.writeObject(this.allUsers);
            output.close();
        }
        catch(IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input during serialization.", ex);
            // System.out.println("Input error during serialization!");
        }
    }

    // TODO
    // USER ACCOUNT METHODS

    /**
     * Takes in username, password, finds the right user name and returns it.
     * If the username does not match up with password, throw InvalidLoginException.
     * Put this method in a try-catch!!!
     * @param username String username
     * @param password String password
     * @return the string username
     * @throws InvalidLoginException an invalid login
     */
    public String login(String username, String password) throws InvalidLoginException {
        // check username
        if(this.allUsers.containsKey(username)) {
            // check password
            if(password.equals(this.allUsers.get(username).getPassword())) {
                logger.log(Level.INFO, "successful login, user object returned");
                // if successful, return String username
                return this.allUsers.get(username).getUsername();
            }
        }
        logger.log(Level.INFO, "Invalid login exception.");
        throw new InvalidLoginException();
    }

    /**
     * Takes in a newly created User object and adds it to the list of users.
     * All usernames are unique.
     * Returns true if the user successfully created, false if there's another user with the same username.
     * PUt this method in a try-catch!!!
     * @param userName the new user's username
     * @param password new user's chosen password
     * @throws InvalidUsernameException username is already taken
     */
    public void createNewUser(String userName, String password) throws InvalidUsernameException{
        if(!this.allUsers.containsKey(userName)) {
            logger.log(Level.INFO, "Successful account creation.");
            this.allUsers.put(userName, new User(userName, password));
        }
        logger.log(Level.INFO, "Invalid username exception");
        throw new InvalidUsernameException();
    }

    /**
     * Allows a user to change their username as long as
     * @param username String username (the old one)
     * @param newUsername The new username
     * @throws InvalidUsernameException if username already taken
     */
    public void changeUsername(String username, String newUsername) throws InvalidUsernameException {
        if(!this.allUsers.containsKey(newUsername)) {
            this.allUsers.get(username).setUsername(newUsername);
        }
        throw new InvalidUsernameException();
    }

    /**
     * Changes the password of a user
     * @param username String username
     * @param newPassword the new password
     */
    public void changePassword(String username, String newPassword) {
        this.allUsers.get(username).setPassword(newPassword);
    }

    /**
     * Return all usernames in this system.
     * @return String of all usernames
     */
    @Override
    public String toString() {
        String allUsers = "";
        for (String user: this.allUsers.keySet()) {
            allUsers += user + " ";
        }
        return allUsers;
    }

    // GETTERS/SETTERS

    /**
     * Return user ability to trade status.
     * @param user user to be checked
     * @return true if user can trade, false if not
     * @throws UserFrozenException if the User is frozen (make note of this for the user!!!)
     */
    public boolean getCanTrade(String user) throws UserFrozenException {
        User chosenUser = this.allUsers.get(user);
        if (chosenUser.getFrozenStatus()) {
            throw new UserFrozenException();
        }
        return chosenUser.getBorrowedTimes() <= chosenUser.getLendTimes();
    }

    /**
     * Return a user's personal inventory.
     * @param user String username
     * @return Arraylist of user's items
     */
    public ArrayList<Item> getUserInventory (String user) { return this.allUsers.get(user).getPersonalInventory(); }

    /**
     * Return a user's personal wishlist.
     * @param user String username
     * @return the user's wishlist
     */
    public ArrayList<Item> getUserWishlist (String user) { return this.allUsers.get(user).getWishlist(); }

    /**
     * Return a certain user's messages.
     * @param username the user you want
     * @return arraylist of this user's messages
     */
    public ArrayList<Message> getUserMessages(String username) {
        return this.allUsers.get(username).getMessages();
    }

    /**
     * Set a user's messages to a different arraylist
     * @param username String username
     * @param userMessages The arraylist of messages to set
     */
    public void setUserMessages(String username, ArrayList<Message> userMessages) {
        this.allUsers.get(username).setMessages(userMessages);
    }

    /*
    when the newly created trade object is first added to user's inventory
    public void confirmTrade(Trade trade) {
        call on some method in Trade that would return either a precompleted trade object or whatever
    }
    */

    public boolean checkIfTooManyIncompleteTrades(String username) {
        return this.allUsers.get(username).getIncompleteTradeHistory().size() <
                this.allUsers.get(username).getLimitOfImcompleteTrade();
    }

    // public ArrayList<Trade> tradesToConfirm(String username) {
    //    call all trades that have yet to be confirmed to be over (incomplete trades?)
    //    use some method in trade that would check to see if the trade meeting time has already passed
    //    prompt user to confirm each trade
    // }

    // USER INVENTORY AND WISHLIST

    /**
     * Removes an item from a user's personal inventory.
     * @param user String username
     * @param itemID String item ID
     * @return True if item removed, False if item not in inventory or some other error comes up.
     */
    public boolean removeItemFromUserInventory (String user, String itemID) {
        ArrayList<Item> userInventory = getUserInventory(user);
        for(Item item : userInventory) {
            // if you find the matching item
            if(item.getItemID().equals(itemID)) {
                userInventory.remove(item);
                this.allUsers.get(user).setPersonalInventory(userInventory);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove an item from a user's wishlist.
     * @param user String username
     * @param itemID String itemID
     * @return Return true if item successfully removed, false if item not in wishlist
     */
    public boolean removeItemFromUserWishlist(String user, String itemID) {
        ArrayList<Item> userWishlist = getUserWishlist(user);
        for(Item item : userWishlist) {
            // if you find the matching item
            if(item.getItemID().equals(itemID)) {
                userWishlist.remove(item);
                this.allUsers.get(user).setWishlist(userWishlist);
                return true;
            }
        }
        return false;
    }

    /**
     * Add an item to a user's inventory.
     * @param item item to be added to inventory
     * @param username String username
     */
    public void addItemToUserInventory(Item item, String username) {
        ArrayList<Item> userInventory = getUserInventory(username);
        userInventory.add(item);
        this.allUsers.get(username).setPersonalInventory(userInventory);
    }

    /**
     * Add an item to a user's wishlist
     * @param username String username
     * @param item Item object to be added
     */
    public void addItemToWishlist(String username, Item item) {
        ArrayList<Item> userWishlist = getUserWishlist(username);
        userWishlist.add(item);
        this.allUsers.get(username).setWishlist(userWishlist);
    }

    public NewItemMessage createNewItem(String username) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the name of your item");
        String name = input.nextLine();
        System.out.println("Enter a short description of your item");
        String description = input.nextLine();
        Item newItem = new Item(name, username, description);
        return new NewItemMessage("User " + username + " has created a new item, requires approval", newItem);
    }

    // TODO
    // Possible messages section?
    // public Object sendAdminSomeMessageOrWhatever() {}

    // ADMIN METHODS

    /**
     * ADMIN ONLY
     * Allows an admin to either freeze an unfrozen user account or unfreeze a frozen user account.
     * @param userName the User object in question.
     */
    public void freezeUserAccount(String userName, boolean freezeStatus) {
        this.allUsers.get(userName).setFrozenStatus(freezeStatus);
    }

    /**
     * ADMIN ONLY
     * Iterator code taken from:
     * https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
     * Allows an admin to set a new trades per week value for all users.
     * @param newTradesPerWeek the new trades per week
     */
    public void setWeeklyTrades(int newTradesPerWeek) {
        for (User user: this.allUsers.values()) {
            user.setTradePerWeek(newTradesPerWeek);
        }
    }

    /**
     * ADMIN ONLY
     * Allows an admin to set a new weekly trades limit for one user
     * @param username the user to be accessed
     * @param newTradesPerWeek the new trades per week limit
     */
    public void setWeeklyTradesForOneUser(String username, int newTradesPerWeek) {
        this.allUsers.get(username).setTradePerWeek(newTradesPerWeek);
    }

    /**
     * ADMIN ONLY
     * Removes the selected user.
     * Returns true if the user successfully removed, false if the user is not in the list of users.
     * @param userName String of username
     * @throws InvalidUsernameException if username not in list of users (checks for typos)
     */
    public void removeUser(String userName) throws InvalidUsernameException {
        if(!this.allUsers.containsKey(userName)) {
            throw new InvalidUsernameException();
        }
        this.allUsers.remove(userName);
    }

    /**
     * ADMIN ONLY
     * Allows an admin to set a new limit of how many incomplete trades a user can have for all users.
     * @param newLimit the new incomplete trades limit to set all users to
     */
    public void setLimitOfIncompleteTrades(int newLimit) {
        for (User user : this.allUsers.values()) {
            user.setLimitOfImcompleteTrade(newLimit);
        }
    }

    /**
     * ADMIN ONLY
     * @param username The user to be set
     * @param newLimit the new incomplete trades limit that one user will be set to
     */
    public void setLimitOfIncompleteTradesForOneUser(String username, int newLimit) {
        this.allUsers.get(username).setLimitOfImcompleteTrade(newLimit);
    }

    /**
     * ADMIN ONLY
     * Allows an admin to set a new borrow v. loan threshold for all users
     * @param newThreshold the new threshold to set
     */
    public void setNewThreshold(int newThreshold) {
        for(User user : this.allUsers.values()) {
            user.setTheshold(newThreshold);
        }
    }

    /**
     * ADMIN ONLY
     * Allows an admin to set a new borrow v. loan threshold for a single user
     * @param username the user to be accessed
     * @param newThreshold the new borrow v. loan threshold
     */
    public void setNewThresholdForOneUser(String username, int newThreshold) {
        this.allUsers.get(username).setTheshold(newThreshold);
    }
}
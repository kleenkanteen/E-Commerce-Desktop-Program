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

public class UserManager implements Serializable{
    private HashMap<String, User> allUsers;

    public UserManager(String username, HashMap<String, User> allUsers) {
        this.allUsers = allUsers;
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

    /**
     * Check if a user has too many incomplete trades.
     * @param username the username
     * @return True if the user has not exceeded max incompletes, false if there are too many incompletes
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

    /**
     * Add a trade to a user's trade history.
     * @param username the username of the user to be accessed
     * @param trade the trade to be added to this user's trade history
     */
    public void addToTradeHistory(String username, Trade trade) {
        this.allUsers.get(username).addTradeHistory(trade);
    }

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

    /**
     * Allows a user to request an admin approve of a new item.
     * @param username the name of the user requesting approval
     * @param itemName the name of the new object
     * @param description description of this object
     * @return the NewItemMessage to be approved by an admin
     */
    public NewItemMessage createNewItem(String username, String itemName, String description) {
        Item newItem = new Item(itemName, username, description);
        return new NewItemMessage("User " + username + " has created a new item, requires approval", newItem);
    }

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
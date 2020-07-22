package use_cases;

import entities.TradeRequestMessage;
import exceptions.InvalidUsernameException;

import java.util.ArrayList;
import java.util.HashMap;

public class UserManager {
    private HashMap<String, entities.User> allUsers;

    /**
     * Constructs a UserManager object
     * @param allUsers the hashmap of all user objects
     */
    public UserManager(HashMap<String, entities.User> allUsers) {
        this.allUsers = allUsers;
    }

    /**
     * Allows a user to login. Only use with user login option in main menu!
     * If the username does not match up with password
     * @param username the username input
     * @param password the password input
     * @return True if user logged in, false if invalid login
     */
    public boolean login(String username, String password) {
        // check username
        if(allUsers.containsKey(username)) {
            // check password
            // if successful, return String username
            return password.equals(allUsers.get(username).getPassword());
        }
        return false;
    }

    /**
     * Attempts to add a new user to the HashMap of all users
     * All usernames are unique.
     * Returns the HashMap if the user successfully created, Throw error if there's another user with the same username.
     * @param username the new username
     * @param password the string password
     * @param tradeHistory the hashmap of all user trades
     * @return true if user account successfully created, false if user already exists in system
     * @throws exceptions.InvalidUsernameException if the username already exists in the system
     */
    public boolean createNewUser(String username, String password,
                                 HashMap<String, ArrayList<entities.Trade>> tradeHistory) throws exceptions.InvalidUsernameException {
        if(allUsers.containsKey(username)) {
            throw new exceptions.InvalidUsernameException();
        }
        if(!(username.length() < 3)) {
            allUsers.put(username, new entities.User(username, password));
            tradeHistory.put(username, new ArrayList<>());
            return true;
        }
        return false;
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
     * Checks to see if this username exists in the system of users.
     * @param username The username to check
     * @return Returns true if the user does exist in the system, false if otherwise.
     */
    public boolean isValidUser(String username) {
        return this.allUsers.containsKey(username);
    }

    /**
     * Return all usernames in this system.
     * @return String of all usernames
     */
    @Override
    public String toString() {
        StringBuilder allUsers = new StringBuilder();
        for (String user: this.allUsers.keySet()) {
            allUsers.append(user).append(" ");
        }
        return allUsers.toString();
    }

    // GETTERS/SETTERS

    /**
     * Returns whether or not this user can trade.
     * @param user the user in question
     * @param borrowedTimes the num of times this user has borrowed
     * @param lendTimes the num of times this user has loaned
     * @param numIncomplete the num of incomplete trades this user has
     * @param numTradesMadeThisWeek the num of trade this user made this week
     * @return True if this user can trade, false if not
     * @throws exceptions.UserFrozenException Throws if the user is already frozen
     */
    public boolean getCanTrade(String user, int borrowedTimes, int lendTimes,
                               int numIncomplete, int numTradesMadeThisWeek) throws exceptions.UserFrozenException {
        if (this.allUsers.get(user).getFrozenStatus()) {
            throw new exceptions.UserFrozenException();
        }
        // check borrows, num of incomplete trades, num of trades made this week
        return (borrowedTimes - lendTimes) < this.allUsers.get(user).getThreshold() &&
                numIncomplete < this.allUsers.get(user).getLimitOfIncompleteTrade() &&
                numTradesMadeThisWeek < this.allUsers.get(user).getTradePerWeek();
    }

    /**
     * Variant of getCanTrade to check to see if the user can trade; will ignore borrows v. loans so that user can
     * can loan items
     * @param username user in question
     * @param numIncomplete the number of incomplete trades
     * @param numTradesMadeThisWeek the number of trade offers made this week
     * @return true if the user can trade, false if not
     * @throws exceptions.UserFrozenException if the user is already frozen
     */
    public boolean getCanTradeIgnoreBorrowsLoans(String username, int numIncomplete, int numTradesMadeThisWeek)
            throws exceptions.UserFrozenException {
        if (this.allUsers.get(username).getFrozenStatus()) {
            throw new exceptions.UserFrozenException();
        }
        return numIncomplete < this.allUsers.get(username).getLimitOfIncompleteTrade() &&
                numTradesMadeThisWeek < this.allUsers.get(username).getTradePerWeek();
    }

    /**
     * Return a user's personal inventory.
     * @param user String username
     * @return Arraylist of user's items
     */
    public ArrayList<entities.Item> getUserInventory (String user) { return this.allUsers.get(user).getPersonalInventory(); }

    /**
     * Return a user's personal wishlist.
     * @param user String username
     * @return the user's wishlist
     */
    public ArrayList<entities.Item> getUserWishlist (String user) { return this.allUsers.get(user).getWishlist(); }

    /**
     * Return a certain user's messages.
     * @param username the user you want
     * @return arraylist of this user's messages
     */
    public ArrayList<entities.Message> getUserMessages(String username) {
        return this.allUsers.get(username).getMessages();
    }

    /**
     * Allows for setting of a user's messages
     * @param username the user in question
     * @param message the arrayList of messages to set
     */
    public void setUserMessages(String username, ArrayList<entities.Message> message) {
        this.allUsers.get(username).setMessages(message);
    }

    /**
     * Return this user's limit of incomplete trades
     * @param username the user in question
     * @return the max num of incomplete trades for this user
     */
    public int getUserIncompleteTrades(String username) {
        return this.allUsers.get(username).getLimitOfIncompleteTrade();
    }

    /**
     * Returns the max trades per week that a specified user can make.
     * @param username the String username
     * @return the number of trades this user can make
     */

    public int getTradesPerWeekForUser(String username) { return this.allUsers.get(username).getTradePerWeek(); }

    /**
     * Returns the account information of a selected user.
     * @param username the user in question
     * @return The string representation of this user's account
     */
    public String getUserInfo(String username) {
        return this.allUsers.get(username).accountInfo();
    }

    /**
     * Returns a user's frozen status
     * @param username the user in question
     * @return True if frozen, false if unfrozen.
     */
    public boolean getUserFrozenStatus(String username) {
        return this.allUsers.get(username).getFrozenStatus();
    }

    /**
     * Return's a user's threshold
     * @param username the user in question
     * @return the threshold of borrows v. loans
     */
    public int getUserThreshold(String username) {
        return this.allUsers.get(username).getThreshold();
    }

    /**
     * Adds a message to a user's account
     * @param username the user to be accessed
     * @param message the message to add
     */
    public void addUserMessage(String username, entities.Message message) { this.allUsers.get(username).addMessages(message); }

    /**
     * Removes a message from a user's account
     * @param username the user in question
     * @param message the message to remove
     */
    public void removeUserMessage(String username, entities.Message message) {
        this.allUsers.get(username).removeMessages(message);
    }

    // USER INVENTORY AND WISHLIST

    /**
     * Removes an item from a user's personal inventory.
     * @param user String username
     * @param itemID String item ID
     */
    public void removeItemFromUserInventory (String user, String itemID) {
        ArrayList<entities.Item> userInventory = new ArrayList<>(getUserInventory(user));
        for(entities.Item item : userInventory) {
            if (item.getItemID().equals(itemID)) {
                userInventory.remove(item);
                this.allUsers.get(user).setPersonalInventory(userInventory);
                return;
            }
        }
    }

    /**
     * Remove an item from a user's wishlist.
     * @param user String username
     * @param itemID String itemID
     */
    public void removeItemFromUserWishlist(String user, String itemID) {
        ArrayList<entities.Item> userWishlist = new ArrayList<>(getUserWishlist(user));
        for(entities.Item item : userWishlist) {
            if(item.getItemID().equals(itemID)) {
                userWishlist.remove(item);
                this.allUsers.get(user).setWishlist(userWishlist);
                return;
            }
        }
    }

    /**
     * Removes an item from multiple user's wishlists.
     * @param users The users to be accessed
     * @param itemID the itemID of the item to be removed
     */
    public void removeFromMultipleUsersWishlists(ArrayList<String> users, String itemID) {
        if (users.size() != 0) {
            for (String user : users) {
                removeItemFromUserWishlist(user, itemID);
            }
        }
    }

    /**
     * Add an item to a user's inventory.
     * @param item item to be added to inventory
     * @param username String username
     */
    public void addItemToUserInventory(entities.Item item, String username) {
        ArrayList<entities.Item> userInventory = getUserInventory(username);
        userInventory.add(item);
        this.allUsers.get(username).setPersonalInventory(userInventory);
    }

    /**
     * Add an item to a user's wishlist
     * @param username String username
     * @param item Item object to be added
     */
    public void addItemToWishlist(String username, entities.Item item) {
        ArrayList<entities.Item> userWishlist = getUserWishlist(username);
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
    public entities.NewItemMessage createNewItem(String username, String itemName, String description) {
        entities.Item newItem = new entities.Item(itemName, username, description);
        return new entities.NewItemMessage("User " + username + " has created a new item, requires approval", newItem);
    }

    /**
     * Create and add a new message object to a user's list of messages
     * @param username the user in question
     * @param content the content of the message to be added to the user's inbox
     */
    public void createUserMessage(String username, String content) {
        addUserMessage(username, new entities.Message(content, username));
    }

    /**
     * Create a new TradeRequestMessage object and add it to another user's inbox
     * @param recipient the user account that will receive the TradeRequestMessage
     * @param messageContent the content of the message
     * @param tradeRequest the TradeRequest object
     * @param senderUsername the user sending the trade request offer
     */
    public void createAndAddNewTradeRequestMessage(String recipient, String messageContent,
                                                   entities.TradeRequest tradeRequest, String senderUsername) {
        addUserMessage(recipient, new TradeRequestMessage(messageContent, tradeRequest, senderUsername));
    }

    // ADMIN METHODS

    /**
     * ADMIN ONLY
     * Allows an admin to either freeze an unfrozen user account or unfreeze a frozen user account.
     * @param username the User object in question.
     * @param freezeStatus what to set the user's freeze status to
     */
    public void freezeUserAccount(String username, boolean freezeStatus) {
        this.allUsers.get(username).setFrozenStatus(freezeStatus);
    }

    /**
     * ADMIN ONLY
     * Overloaded FreezeUserAccount for AdminMenu use
     * @param username the user to freeze
     */
    public void freezeUserAccount(String username) {
        this.allUsers.get(username).setFrozenStatus(!this.allUsers.get(username).getFrozenStatus());
    }

    /**
     * ADMIN ONLY
     * Allows an admin to set a new trades per week value for all users.
     * @param newTradesPerWeek the new trades per week
     */
    public void setWeeklyTrades(int newTradesPerWeek) {
        for (entities.User user: this.allUsers.values()) {
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
     * @throws exceptions.InvalidUsernameException if username not in list of users (checks for typos)
     */
    public void removeUser(String userName) throws exceptions.InvalidUsernameException {
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
        for (entities.User user : this.allUsers.values()) {
            user.setLimitOfIncompleteTrade(newLimit);
        }
    }

    /**
     * ADMIN ONLY
     * @param username The user to be set
     * @param newLimit the new incomplete trades limit that one user will be set to
     */
    public void setLimitOfIncompleteTradesForOneUser(String username, int newLimit) {
        this.allUsers.get(username).setLimitOfIncompleteTrade(newLimit);
    }

    /**
     * ADMIN ONLY
     * Allows an admin to set a new borrow v. loan threshold for all users
     * @param newThreshold the new threshold to set
     */
    public void setNewThreshold(int newThreshold) {
        for(entities.User user : this.allUsers.values()) {
            user.setThreshold(newThreshold);
        }
    }

    /**
     * ADMIN ONLY
     * Allows an admin to set a new borrow v. loan threshold for a single user
     * @param username the user to be accessed
     * @param newThreshold the new borrow v. loan threshold
     */
    public void setNewThresholdForOneUser(String username, int newThreshold) {
        this.allUsers.get(username).setThreshold(newThreshold);
    }
}
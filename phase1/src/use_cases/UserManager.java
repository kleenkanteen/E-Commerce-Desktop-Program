package use_cases;

import exceptions.*;
import entities.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UserManager {
    private HashMap<String, User> allUsers;

    /**
     * Constructs a UserManager object
     * @param allUsers the hashmap of all user objects
     */
    public UserManager(HashMap<String, User> allUsers) {
        this.allUsers = allUsers;
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
     * @throws UserFrozenException if the User is frozen
     */
    public boolean getCanTrade(String user, int borrowedTimes, int lendTimes) throws UserFrozenException {
        User chosenUser = this.allUsers.get(user);
        if (chosenUser.getFrozenStatus()) {
            throw new UserFrozenException();
        }
        return (borrowedTimes - lendTimes) <= chosenUser.getThreshold();
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
     * Returns the max trades per week that a specified user can make.
     * @param username the String username
     * @return the number of trades this user can make
     */
    public int getTradesPerWeekForUser(String username) {
        return this.allUsers.get(username).getTradePerWeek();
    }

    /**
     * Find all temp trades that have passed their completion date and have not been marked confirmed yet.
     * @param username String username
     * @return list of unconfirmed/incomplete TempTrades
     */
    public ArrayList<TempTrade> tempTradesToConfirm(String username) {
        ArrayList<TempTrade> tempTrades = new ArrayList<>();
        ArrayList<TempTrade> allTempTrades = this.allUsers.get(username).getTempTradeHistory();
        for (TempTrade trade : allTempTrades) {
            if (trade.daysLeft() <= 0 && !trade.getCompleted()) {
                tempTrades.add(trade);
            }
        }
        return tempTrades;
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
     * Removes an item from multiple user's wishlists.
     * @param users The users to be accessed
     * @param itemID the itemID of the item to be removed
     */
    public void removeFromMultipleUsersWishlists(ArrayList<String> users, String itemID) {
        for (String user : users) {
            removeItemFromUserWishlist(user, itemID);
        }
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
        for(User user : this.allUsers.values()) {
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

    // TODO add isValidUser() to check if a userid represents a valid user

    /** Added by sabih so adminBrowsingUsers can get info of an user for printing.
     * Called by a controller
     * @param userid - the user to be accessed
     * @return - String of toString representation
     */
    public String representUser(String userid){
        return allUsers.get(userid).accountInfo();
    }
}
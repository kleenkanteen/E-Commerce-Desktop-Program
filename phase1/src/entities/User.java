package entities;

import java.util.ArrayList;

// Moved to entities folder, update getpersonalinventory() with a import so it works

public class User extends AccountInformation {
    private ArrayList<Item> personalInventory;
    private ArrayList<Item> wishlist;
    private ArrayList<Trade> tradeHistory;
    private ArrayList<Message> messages;
    private boolean frozen;
    private int tradePerWeek;
    private int theshold;


    /**
     * Creates an entities.User with the given username and password and the account of times they can trade in a week
     * @param username is the username of this entities.User
     * @param password is the password of this entities.User
     */
    public User(String username, String password) {
        super(username, password);
        frozen = true;
        tradeHistory = new ArrayList<Trade>();
        personalInventory = new ArrayList<Item>();
        wishlist = new ArrayList<Item>();
        tradePerWeek = 5;
        theshold = 1;
        messages = new ArrayList<Message>();
    }

    /**
     * Getter of the personal inventory of this account
     * @return the personal inventory of this account
     */
    public ArrayList<Item> getPersonalInventory(){
        return personalInventory;
    }

    /**
     * Getter of the wishlist of this account
     * @return the wishlist of this account
     */
    public ArrayList<Item> getWishlist(){
        return wishlist;
    }

    /**
     * Getter of the trade history of this account
     * @return the history of this account
     */
    public ArrayList<Trade> getTradeHistory() {
        return tradeHistory;
    }

    /**
     * Getter of all the temporary trade history of this account
     * @return all the temporary trade history of this account
     */
    public ArrayList<Trade> getTempTradeHistory() {
        ArrayList <Trade> tempTradeHistory = new ArrayList<Trade>();
        for (Trade t: tradeHistory){
            if(t instanceof TempTrade) tempTradeHistory.add(t);
        }
        return tempTradeHistory;
    }

    /**
     * Getter of how much times this user has borrowed
     * @return the amount of times this user has borrowed
     */
    public int getBorrowedTimes() {
        //TODO
        return 0;
    }

    /**
     * Getter of how much times this user has lend
     * @return the amount of times this user has lend
     */
    public int getLendTimes() {
        //TODO
        return 0;
    }

    /**
     * Getter of the 3 most frequent trading partners
     * @return the 3 most frequent trading partners
     */
    public User[] getFrequentTradingPartners() {
        //TODO
        User [] tradingPartners= new User[3];
        return tradingPartners;
    }

    /**
     * Getter of the theshold (how many more times must you lend items before you can borrow) value of this user
     * @return the theshold value
     */
    public int getTheshold() {
        return theshold;
    }

    /**
     * Getter of the limited of times the user can trade in a week of this account
     * @return the limited of times the user can trade of this account
     */
    public int getTradePerWeek(){
        return tradePerWeek;
    }

    /**
     * Getter of the frozen status of this account
     * @return the frozen status of this account
     */
    public boolean isFrozen(){
        return frozen;
    }

    /**
     * Getter of the messages of this account
     * @return the messages of this account
     */
    public ArrayList<Message> getMessages(){
        return messages;
    }

    /**
     * Setting a new personal inventory to this account
     * @param personalInventory the new personal inventory
     */
    public void setPersonalInventory(ArrayList<Item> personalInventory){
        this.personalInventory = personalInventory;
    }

    /**
     * Setting a new wishlist to this account
     * @param wishlist the new wishlist
     */
    public void setWishlist(ArrayList<Item> wishlist){
        this.wishlist = wishlist;
    }

    /**
     * Adding the most recent trade of this entities.User to the entities.User's trade history
     * @param trade the most recent trade of this entities.User
     */
    public void addTradeHistory(Trade trade){
        tradeHistory.add(trade);
    }

    /**
     * Setting the frozen status to this account
     * @param frozen the new frozen status to this account
     */
    public void setFrozen(boolean frozen){
        this.frozen = frozen;
    }


    /**
     * Setting a new list of messages to this account
     * @param messages the new messages of this account
     */
    public void setMessages(ArrayList<Message> messages){
        this.messages = messages;
    }

    /**
     * Changing the theshold (how many more times must you lend items before you can borrow) that this user has
     * @param newTheshold the new theshold value
     */
    public void setTheshold(int newTheshold){
        theshold = newTheshold;
    }

    /**
     * Changing the limited of times that this user can trade in a week
     * @param tradePerWeek the new trade per week value
     */
    public void setTradePerWeek(int tradePerWeek){
        this.tradePerWeek = tradePerWeek;
    }

}

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User extends AccountInformation {
    private ArrayList<Item> personalInventory;
    private ArrayList<Item> wishlist;
    private ArrayList<Trade> tradeHistory;
    private ArrayList<Message> messages;
    private boolean frozen;
    private final int tradePerWeek;
    private int theshold;


    /**
     * Creates an User with the given username and password and the account of times they can trade in a week
     * @param username is the username of this User
     * @param password is the password of this User
     * @param tradePerWeek is the limit amount of times this User can trade in a week
     */
    protected User(String username, String password, int tradePerWeek) {
        super(username, password);
        frozen = true;
        tradeHistory = new ArrayList<Trade>();
        personalInventory = new ArrayList<Item>();
        wishlist = new ArrayList<Item>();
        this.tradePerWeek = tradePerWeek;
        theshold = 1;
        messages = new ArrayList<Message>();
    }

    /**
     * Getter of the personal inventory of this account
     * @return the personal inventory of this account
     */
    protected ArrayList<Item> getPersonalInventory(){
        return personalInventory;
    }

    /**
     * Getter of the wishlist of this account
     * @return the wishlist of this account
     */
    protected ArrayList<Item> getWishlist(){
        return wishlist;
    }

    /**
     * Getter of the trade history of this account
     * @return the history of this account
     */
    protected ArrayList<Trade> getTradeHistory() {
        return tradeHistory;
    }

    /**
     * Getter of all the temporary trade history of this account
     * @return all the temporary trade history of this account
     */
    protected ArrayList<Trade> getTempTradeHistory() {
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
    protected int getBorrowedTimes() {
        //TODO
        return 0;
    }

    /**
     * Getter of how much times this user has lend
     * @return the amount of times this user has lend
     */
    protected int getLendTimes() {
        //TODO
        return 0;
    }

    /**
     * Getter of the 3 most frequent trading partners
     * @return the 3 most frequent trading partners
     */
    protected User[] getFrequentTradingPartners() {
        //TODO
        User [] tradingPartners= new User[3];
        return tradingPartners;
    }

    /**
     * Getter of the theshold (how many more times must you lend items before you can borrow) value of this user
     * @return the theshold value
     */
    protected int getTheshold() {
        return theshold;
    }

    /**
     * Getter of the limited of times the user can trade in a week of this account
     * @return the limited of times the user can trade of this account
     */
    protected int getTradePerWeek(){
        return tradePerWeek;
    }

    /**
     * Getter of the frozen status of this account
     * @return the frozen status of this account
     */
    protected boolean isFrozen(){
        return frozen;
    }

    /**
     * Getter of the messages of this account
     * @return the messages of this account
     */
    protected ArrayList<Message> getMessages(){
        return messages;
    }

    /**
     * Setting a new personal inventory to this account
     * @param personalInventory the new personal inventory
     */
    protected void setPersonalInventory(ArrayList<Item> personalInventory){
        this.personalInventory = personalInventory;
    }

    /**
     * Setting a new wishlist to this account
     * @param wishlist the new wishlist
     */
    protected void setWishlist(ArrayList<Item> wishlist){
        this.wishlist = wishlist;
    }

    /**
     * Adding the most recent trade of this User to the User's trade history
     * @param trade the most recent trade of this User
     */
    protected void addTradeHistory(Trade trade){
        tradeHistory.add(trade);
    }

    /**
     * Setting the frozen status to this account
     * @param frozen the new frozen status to this account
     */
    protected void setFrozen(boolean frozen){
        this.frozen = frozen;
    }


    /**
     * Setting a new list of messages to this account
     * @param messages the new messages of this account
     */
    protected void setMessages(ArrayList<Message> messages){
        this.messages = messages;
    }

    /**
     * Changing the theshold (how many more times must you lend items before you can borrow) that this user has
     * @param newTheshold the new theshold value
     */
    protected void setTheshold(int newTheshold){
        theshold = newTheshold;
    }

}

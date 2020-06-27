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
     * Add a new message to messages
     * @param newMessage the message being added to messages
     */
    protected void addMessage(Message newMessage){
        messages.add(newMessage);
    }

    /**
     * Deleting a message from messages, if the message is not in messages, nothing happens
     * @param message the message being deleted from messages
     */
    protected void removeMessage(Message message){
        messages.remove(message);
    }

}

import java.util.ArrayList;

public class User extends AccountInformation {
    ArrayList<Item> personalInventory;
    ArrayList<Item> wishlist;
    ArrayList<Trade> tradeHistory;
    boolean frozen;
    int tradePerWeek;


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

    }
}

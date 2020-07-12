package F_entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class TradeRequest implements Serializable {
    /**
     * Creates a one-way trade request that sends to the lander, or creates a two-way trade request to another user
     */
    private String userA; // username of user who initiate the trade request (borrower in one way trade)
    private String userB; // username of user who gets the trade request (lander in one way trade)
    private ArrayList<Item> itemA; // userA's items
    private ArrayList<Item> itemB;  // userB's items
    private boolean perm;
    private LocalDateTime date;
    private String place;

    private boolean confirmationA = true;
    private boolean confirmationB = false;
    private int numberOfEditA = 3;
    private int numberOfEditB = 3;

    // one way trade request

    /**
     * Generate a one-way trade request
     * @param userA the username of user who initiate the trade request (borrower in one-way trade)
     * @param userB the user who gets the trade request (lander in one-way trade)
     * @param itemB userB's item
     * @param perm true if this user is requesting a perm trade, false if requesting a temp trade
     * @param date the date of this trade request meeting date
     * @param place the place for the meeting
     */
    public TradeRequest(String userA, String userB, ArrayList<Item> itemB, boolean perm, LocalDateTime date, String place) {
        this.userA = userA;
        this.userB = userB;
        this.itemB = itemB;
        this.perm = perm;
        this.date = date;
        this.place = place;
    }


    // two way trade request

    /**
     * Generate two-way trade request
     * @param userA the username of user who initiate the trade request (borrower in one-way trade)
     * @param userB the user who gets the trade request (lander in one-way trade)
     * @param itemA userA's item
     * @param itemB userB's item
     * @param perm true if this user is requesting a perm trade, false if requesting a temp trade
     * @param date the date of this trade request meeting date
     * @param place the place for the meeting
     */


    public TradeRequest(String userA, String userB, ArrayList<Item> itemA, ArrayList<Item> itemB, boolean perm,
                        LocalDateTime date, String place) {
        this.userA = userA;
        this.userB = userB;
        this.itemA = itemA;
        this.itemB = itemB;
        this.perm = perm;
        this.date = date;
        this.place = place;
    }

    public String getUserA() {
        return userA;
    }

    public String getUserB() {
        return userB;
    }

    public ArrayList<Item> getItemA() {
        return itemA;
    }

    public ArrayList<Item> getItemB() {
        return itemB;
    }

    public boolean isPerm() {
        return perm;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public int getNumberOfEditA() {
        return numberOfEditA;
    }

    public int getNumberOfEditB() {
        return numberOfEditB;
    }

    public boolean isConfirmationA() {
        return confirmationA;
    }

    public boolean isConfirmationB() {
        return confirmationB;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setConfirmationA(boolean confirmationA) {
        this.confirmationA = confirmationA;
    }

    public void setConfirmationB(boolean confirmationB) {
        this.confirmationB = confirmationB;
    }

    public void setNumberOfEditA(int numberOfEditA) {
        this.numberOfEditA = numberOfEditA;
    }

    public void setNumberOfEditB(int numberOfEditB) {
        this.numberOfEditB = numberOfEditB;
    }

    /**
     * Get the trading partner of the input user
     * @param user a user
     * @return the other user involved in the traderequest
     */
    public String getTradePartner (String user){
        if (user.equals(userA)){
            return this.userB;
        }
        else{
            return this.userA;
        }
    }

    public String toString(){
        String info = "";
        if (itemA.isEmpty()){
            info = getUserA() + " is requesting a trade with " + getUserB() + " for " +
                    getItemB().get(0).getName();
        }
        else if (itemB.isEmpty()){
            info = getUserA() + " is requesting a trade with " + getUserB() + " for " +
                    getItemA().get(0).getName();
        }
        else info = getUserA() + " is requesting a two-way trade " + getUserB() + " trading " +
                    getItemA().get(0).getName() + " for " +  getItemB().get(0).getName();

        if(perm) info = info + " in a permanent trade";
        else info = info + " in a temporary trade";
        info = info + " and want to meet at " + getPlace() + " on " + getDate().toString() +".";
        return info;
    }
}

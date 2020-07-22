package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class TradeRequest implements Serializable {
    /**
     * Creates a one-way trade request that sends to the lander, or creates a two-way trade request to another user
     */
    private String userA; // username of user who initiate the trade request (borrower in one way trade)
    private String userB; // username of user who gets the trade request (lander in one way trade)
    private ArrayList<entities.Item> itemA; // userA's items
    private ArrayList<entities.Item> itemB;  // userB's items
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
    public TradeRequest(String userA, String userB, ArrayList<entities.Item> itemB, boolean perm, LocalDateTime date,
                        String place) {
        this.userA = userA;
        this.userB = userB;
        this.itemB = itemB;
        itemA = new ArrayList<>();
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


    public TradeRequest(String userA, String userB, ArrayList<entities.Item> itemA, ArrayList<entities.Item> itemB, boolean perm,
                        LocalDateTime date, String place) {
        this.userA = userA;
        this.userB = userB;
        this.itemA = itemA;
        this.itemB = itemB;
        this.perm = perm;
        this.date = date;
        this.place = place;
    }

    /**
     * get the userA involved in this trade request
     * @return userA
     */
    public String getUserA() {
        return userA;
    }

    /**
     * get the userA involved in this trade request
     * @return userB
     */
    public String getUserB() {
        return userB;
    }

    /**
     * get the items belongs to userA
     * @return list of items involved in this trade request belong to userA
     */
    public ArrayList<entities.Item> getItemA() {
        return itemA;
    }
    /**
     * get the items belongs to userB
     * @return list of items involved in this trade request belong to userB
     */
    public ArrayList<Item> getItemB() {
        return itemB;
    }

    /**
     * returns if this a permanent trade request
     * @return true if this is a permanent trade request, flase if this is a temporary trade request
     */
    public boolean isPerm() {
        return perm;
    }

    /**
     * get the meeting date set by the user
     * @return the date of the meeting
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * get the place of the meeting
     * @return the place of the meeting
     */
    public String getPlace() {
        return place;
    }

    /**
     * get the number of edit for userA
     * @return the number of edit for userA
     */
    public int getNumberOfEditA() {
        return numberOfEditA;
    }

    /**
     * get the number of edit for userB
     * @return the number of edit for userB
     */
    public int getNumberOfEditB() {
        return numberOfEditB;
    }

//    public boolean isConfirmationA() {
//        return confirmationA;
//    }
//
//    public boolean isConfirmationB() {
//        return confirmationB;
//    }

    /**
     * set the date of the meeting
     * @param date date of the meeting
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * set the place of the meeting
     * @param place the place of the meeting
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * set the confirmation state for userA
     * @param confirmationA true if user confirm, flase if user does not confirm
     */
    public void setConfirmationA(boolean confirmationA) {
        this.confirmationA = confirmationA;
    }

    /**
     * set the confirmation state for userB
     * @param confirmationB true if user confirm, flase if user does not confirm
     */
    public void setConfirmationB(boolean confirmationB) {
        this.confirmationB = confirmationB;
    }

    /**
     * set the number of edits for userA
     * @param numberOfEditA number of edit for userA
     */
    public void setNumberOfEditA(int numberOfEditA) {
        this.numberOfEditA = numberOfEditA;
    }

    /**
     * set the number of edits for userB
     * @param numberOfEditB number of edit for userB
     */
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

    /**
     * toString method for trade request
     * @return a String represents the trade request
     */
    public String toString(){
        String info = "";
        if (itemA.isEmpty()){
            info =  "TraderA (Borrower): " + getUserA() +
                    "\nTraderB: " + getUserB() +
                    "\nItem from B: " + getItemB().get(0).getName() +
                    "\nPlace: " + getPlace() +
                    "\nDate: " + getDate().toString();
        }
        else if (itemB.isEmpty()){
            info = "TraderA: " + getUserA() +
                    "\nTraderB (Borrower): " + getUserB() +
                    "\nItem from A: " + getItemA().get(0).getName() +
                    "\nPlace: " + getPlace() +
                    "\nDate: " + getDate().toString();
        }
        else info = "TraderA: " + getUserA() +
                    "\nTraderB: " + getUserB() +
                    "\nItem from A: " + getItemA().get(0).getName() +
                    "\nItem from B: " + getItemB().get(0).getName() +
                    "\nPlace: " + getPlace() +
                    "\nDate: " + getDate().toString();

        if(perm) info = info + "\nTrade type: permanent trade";
        else info = info + "\nTrade type: temporary trade";
        return info;


    }
}

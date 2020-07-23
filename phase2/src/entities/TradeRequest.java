package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TradeRequest extends Request implements Serializable {

    private String userA; // username of user who initiate the trade request (borrower in one way trade)
    private String userB; // username of user who gets the trade request (lander in one way trade)
    private ArrayList<Item> itemA; // userA's items
    private ArrayList<entities.Item> itemB;  // userB's items
    private boolean perm;
    private LocalDateTime date;
    private String place;
    private boolean confirmation = true;
    private int numberOfEditA = 3;
    private int numberOfEditB = 3;

    /**
     * Class constructor.
     * A message to a User from another user that asks them to make a decision on a trade request
     * @param content is the content of the message
     * @param sender is the sender's username
     */
    public TradeRequest(String content, String sender) {
        super(content, new String[]{"confirm", "deny", "edit"}, sender);
        this.userA  = sender;
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
     * set the userB involved in this trade request
     * @param userB a user
     */
    public void setUserB(String userB) {
        this.userB = userB;
    }

    /**
     * set to true if this trade request is a permanent trade request, false if this trade request is a
     * temporary trade request
     * @param perm
     */
    public void setPerm(boolean perm) {
        this.perm = perm;
    }

    /**
     * set the confirmation state for userA
     * @param confirmation true if user confirm, flase if user does not confirm
     */
    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public void addItemA (Item item){
        itemA.add(item);
    }

    public void addItemB (Item item){
        itemB.add(item);
    }

    /**
     * Returns a string representation of the message
     * @return the content, decisions, and trade request of the message in a string representation
     */

    @Override
    public String toString(){
        String info = "";
        String itema = "";
        String itemb = "";
        for (int i = 0; i < itemB.size(); i ++){
            itema += itemA.get(i).getName() + ", ";
        }
        for (int i = 0; i < itemB.size(); i ++){
            itemb += itemB.get(i).getName() + ", ";
        }

        if (itemA.isEmpty()){
            info =  "TraderA (Borrower): " + getUserA() +
                    "\nTraderB: " + getUserB() +
                    "\nItem from B: " + itemb +
                    "\nPlace: " + getPlace() +
                    "\nDate: " + getDate().toString();
        }
        else if (itemB.isEmpty()){
            info = "TraderA: " + getUserA() +
                    "\nTraderB (Borrower): " + getUserB() +
                    "\nItem from A: " +itema +
                    "\nPlace: " + getPlace() +
                    "\nDate: " + getDate().toString();
        }
        else info = "TraderA: " + getUserA() +
                    "\nTraderB: " + getUserB() +
                    "\nItem from A: " + itema +
                    "\nItem from B: " + itemb +
                    "\nPlace: " + getPlace() +
                    "\nDate: " + getDate().toString();

        if(perm) info = info + "\nTrade type: permanent trade";
        else info = info + "\nTrade type: temporary trade";
        return info;
        //TO DO: loop over item list to get all the items
    }
}

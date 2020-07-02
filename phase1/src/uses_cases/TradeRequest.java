package uses_cases;

import entities.Item;

import java.util.Date;


public class TradeRequest {
    /**
     * Creates a one-way trade request that sends to the lander, or creates a two-way trade request to another user
     * @param userA the username of user who initiate the trade request (borrower in one-way trade)
     * @param userB the user who gets the trade request (lander in one-way trade)
     * @param itemA userA's item
     * @param itemB userB's item
     * @param perm true if this user is requesting a perm trade, false if requesting a temp trade
     * @param date the date of this trade request meeting date
     * @param place the place for the meeting
     * @param confirmationA true if userA has confirmed the trade request, false if userA does not confirm
     * @param confirmationB true if userB has confirmed the trade request, false if userB does not confirm
     */
    private String userA; // username of user who initiate the trade request (borrower in one way trade)
    private String userB; // username of user who gets the trade request (lander in one way trade)
    private Item itemA; // userA's items
    private Item itemB;  // userB's items
    private boolean perm;
    private Date date; // Date(int year, int month, int date, int hrs, int min)
    private String place;

    private boolean confirmationA = true;
    private boolean confirmationB = false;

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
    public TradeRequest(String userA, String userB, Item itemB, boolean perm, Date date, String place) {
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


    public TradeRequest(String userA, String userB, Item itemA, Item itemB, boolean perm,
                        Date date, String place) {
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

    public Item getItemA() {
        return itemA;
    }

    public Item getItemB() {
        return itemB;
    }

    public boolean isPerm() {
        return perm;
    }

    public Date getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public boolean isConfirmationA() {
        return confirmationA;
    }

    public boolean isConfirmationB() {
        return confirmationB;
    }

    public void setDate(Date date) {
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
}

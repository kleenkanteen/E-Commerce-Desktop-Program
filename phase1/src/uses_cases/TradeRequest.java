package uses_cases;

import entities.Item;

import java.util.Date;


public class TradeRequest {
    private String userA; // username of user who initiate the trade request (borrower in one way trade)
    private String userB; // username of user who gets the trade request (lander in one way trade)
    private Item itemA; // userA's items
    private Item itemB;  // userB's items
    private boolean Perm;
    private Date date; // Date(int year, int month, int date, int hrs, int min)
    private String place;

    private boolean confirmationA = true;
    private boolean confirmationB = false;

    // one way trade request

    public TradeRequest(String userA, String userB, Item itemB, boolean tempOrPerm, Date date, String place) {
        this.userA = userA;
        this.userB = userB;
        this.itemB = itemB;
        this.Perm = tempOrPerm;
        this.date = date;
        this.place = place;
    }


    // two way trade request


    public TradeRequest(String userA, String userB, Item itemA, Item itemB, boolean tempOrPerm,
                        Date date, String place) {
        this.userA = userA;
        this.userB = userB;
        this.itemA = itemA;
        this.itemB = itemB;
        this.Perm = tempOrPerm;
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
        return Perm;
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

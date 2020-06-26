import java.util.Date;
public class TradeRequest {
    private User userA; // user who initiate the trade request (borrower in one way trade)
    private User userB; // user who gets the trade request (lander in one way trade)
    private Item itemA; // userA's item
    private Item itemB; // userB's item
    private boolean tempOrPerm;
    private Date date; // Date(int year, int month, int date, int hrs, int min)
    private String place;
    private int numberOfEditA = 3;
    private boolean canEditA = true;
    private boolean canEditB = true;
    private int numberOfEditB = 3;
    private boolean confirmation = false;

    // one way trade request

    public TradeRequest(User userA, User userB, Item itemA, boolean tempOrPerm, Date date, String place) {
        this.userA = userA;
        this.userB = userB;
        this.itemA = itemA;
        this.tempOrPerm = tempOrPerm;
        this.date = date;
        this.place = place;
    }


    // two way trade request


    public TradeRequest(User userA, User userB, Item itemA, Item itemB, boolean tempOrPerm, Date date, String place) {
        this.userA = userA;
        this.userB = userB;
        this.itemA = itemA;
        this.itemB = itemB;
        this.tempOrPerm = tempOrPerm;
        this.date = date;
        this.place = place;
    }

    public User getUserA() {
        return userA;
    }

    public User getUserB() {
        return userB;
    }

    public Item getItemA() {
        return itemA;
    }

    public Item getItemB() {
        return itemB;
    }

    public boolean isTempOrPerm() {
        return tempOrPerm;
    }

    public Date getDate() {
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


    //user can set data, place and confirm
    public void setDate(User user, Date date) {
        canEdit(user, this);
        if (user == userA && canEditA){
            this.date = date;
            numberOfEditA -= 1;
        }
        else if (user == userB && canEditB){
            this.date = date;
            numberOfEditA -= 1;
        }

    }

    public void setPlace(User user, String place) {
        canEdit(user, this);
        if (user == userA && canEditA){
            this.place = place;
            numberOfEditA -= 1;
        }
        else if (user == userB && canEditB){
            this.place = place;
            numberOfEditA -= 1;
        }
    }

    public void setDateAndPlace(User user, Date date, String place){
        canEdit(user, this);
        if (user == userA && canEditA){
            this.date = date;
            this.place = place;
            numberOfEditA -= 1;
        }
        else if (user == userB && canEditB){
            this.date = date;
            this.place = place;
            numberOfEditA -= 1;
        }
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public void canEdit (User user, TradeRequest t){
        if (user==t.userA && t.numberOfEditA ==0){
            canEditA = false;
        }
        if (user == t.userB && t.numberOfEditB==0){
            canEditB = false;
        }
    }
}

import java.util.Date;
public class TradeRequest {
    private User userA; // user who initiate the trade request (borrower in one way trade)
    private User userB; // user who gets the trade request (lander in one way trade)
    private Item itemA; // userA's item
    private Item itemB; // userB's item
    private boolean tempOrPerm;
    private Date date; // Date(int year, int month, int date, int hrs, int min)
    private String place;
    private int NumberOfEditA = 3;
    private int NumberOfEditB = 3;
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
        return NumberOfEditA;
    }

    public int getNumberOfEditB() {
        return NumberOfEditB;
    }


    //user can set data, place and confirm
    public void setDate(User user, Date date) {
        if (user == userA && NumberOfEditA > 0){
            this.date = date;
            NumberOfEditA -= 1;
        }
        else if (user == userB && NumberOfEditB > 0){
            this.date = date;
            NumberOfEditA -= 1;
        }
        else System.out.println("Excess edit limit, This trade request has been cancelled");
    }

    public void setPlace(User user, String place) {
        if (user == userA && NumberOfEditA > 0){
            this.place = place;
            NumberOfEditA -= 1;
        }
        else if (user == userB && NumberOfEditB > 0){
            this.place = place;
            NumberOfEditA -= 1;
        }
        else System.out.println("Excess edit limit, This trade request has been cancelled");
    }

    public void setDateAndPlace(User user, Date date, String place){

        if (user == userA && NumberOfEditA > 0){
            this.date = date;
            this.place = place;
            NumberOfEditA -= 1;
        }
        else if (user == userB && NumberOfEditB > 0){
            this.date = date;
            this.place = place;
            NumberOfEditA -= 1;
        }
        else System.out.println("Excess edit limit, This trade request has been cancelled");
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }
}

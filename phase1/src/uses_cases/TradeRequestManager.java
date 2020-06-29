package uses_cases;

import entities.User;

import java.util.Date;

public class TradeRequestManager {
    private TradeRequest t;
    private int numberOfEditA = 3;
    private boolean canEditA = true;
    private boolean canEditB = true;
    private int numberOfEditB = 3;

    public TradeRequestManager(TradeRequest t) {
        this.t = t;
    }

    //user can set data, place and confirm
    public void setDate(User user, Date date) {
        canEdit(user, t);
        if (user == t.getUserA() && canEditA){
            t.setDate(date);
            numberOfEditA -= 1;
            t.setConfirmationB(false);
            t.setConfirmationA(true);
        }
        else if (user == t.getUserB() && canEditB){
            t.setDate(date);
            numberOfEditA -= 1;
            t.setConfirmationA(false);
            t.setConfirmationB(true);
        }

    }

    public void setPlace(User user, String place) {
        canEdit(user, t);
        if (user == t.getUserA() && canEditA){
            t.setPlace(place);
            numberOfEditA -= 1;
            t.setConfirmationB(false);
            t.setConfirmationA(true);
        }
        else if (user == t.getUserB() && canEditB){
            t.setPlace(place);
            numberOfEditA -= 1;
            t.setConfirmationA(false);
            t.setConfirmationB(true);
        }
    }

    public void setDateAndPlace(User user, Date date, String place){
        canEdit(user, t);
        if (user == t.getUserA() && canEditA){
            t.setDate(date);
            t.setPlace(place);
            numberOfEditA -= 1;
            t.setConfirmationB(false);
            t.setConfirmationA(true);
        }
        else if (user == t.getUserB() && canEditB){
            t.setDate(date);
            t.setPlace(place);
            numberOfEditA -= 1;
            t.setConfirmationA(false);
            t.setConfirmationB(true);
        }
    }

    public void setConfirmation(User user, boolean confirmation) {
        if (user == t.getUserA()){
            t.setConfirmationA(confirmation);
        }
        else t.setConfirmationB(confirmation);

        if (t.isConfirmationA() && t.isConfirmationB()) {
            // Once both user confirms, create a TemTrade, or entities.PermTrade based on t.isPerm
        }
    }

    public void canEdit (User user, TradeRequest t){
        if (user==t.getUserA() && this.numberOfEditA ==0){
            canEditA = false;
        }
        if (user == t.getUserB() && this.numberOfEditB==0){
            canEditB = false;
        }
    }
}

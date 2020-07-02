package uses_cases;

import entities.Trade;

import java.util.Date;

public class TradeRequestManager {
    private TradeRequest t;
    private int numberOfEditA = 3;
    private boolean canEditA = true;
    private boolean canEditB = true;
    private int numberOfEditB = 3;
    private Trade trade;

    public TradeRequestManager(TradeRequest t) {
        this.t = t;
    }

    //user can set data, place and confirm
    public void setDate(String user, Date date) {
        canEdit(user, t);
        if (user.equals(t.getUserA()) && canEditA){
            t.setDate(date);
            numberOfEditA -= 1;
            t.setConfirmationB(false);
            t.setConfirmationA(true);
        }
        else if (user.equals(t.getUserB()) && canEditB){
            t.setDate(date);
            numberOfEditB -= 1;
            t.setConfirmationA(false);
            t.setConfirmationB(true);
        }

    }

    public void setPlace(String user, String place) {
        canEdit(user, t);
        if (user.equals(t.getUserA()) && canEditA){
            t.setPlace(place);
            numberOfEditA -= 1;
            t.setConfirmationB(false);
            t.setConfirmationA(true);
        }
        else if (user.equals(t.getUserB()) && canEditB){
            t.setPlace(place);
            numberOfEditB -= 1;
            t.setConfirmationA(false);
            t.setConfirmationB(true);
        }
    }

    public void setDateAndPlace(String user, Date date, String place){
        canEdit(user, t);
        if (user.equals(t.getUserA()) && canEditA){
            t.setDate(date);
            t.setPlace(place);
            numberOfEditA -= 1;
            t.setConfirmationB(false);
            t.setConfirmationA(true);
        }
        else if (user.equals(t.getUserB()) && canEditB){
            t.setDate(date);
            t.setPlace(place);
            numberOfEditB -= 1;
            t.setConfirmationA(false);
            t.setConfirmationB(true);
        }
    }

    public void setConfirmation(String user, boolean confirmation) {
        if (user.equals(t.getUserA())){
            t.setConfirmationA(confirmation);
        }
        else t.setConfirmationB(confirmation);

        if (t.isConfirmationA() && t.isConfirmationB()) {
            // Once both user confirms, create a TemTrade, or entities.PermTrade based on t.isPerm
        }
    }

    public TradeRequest getTradeRequest() {
        return t;
    }

    public void canEdit (String user, TradeRequest t){
        if (user.equals(t.getUserA()) && this.numberOfEditA ==0){
            canEditA = false;
        }
        if (user.equals(t.getUserB()) && this.numberOfEditB==0){
            canEditB = false;
        }
    }

    public Trade getTrade() {
        return trade;
    }

    public int getNumberOfEditA() {
        return numberOfEditA;
    }

    public int getNumberOfEditB() {
        return numberOfEditB;
    }
}

package uses_cases;

import entities.Trade;
import entities.TradeRequest;

import java.util.Date;

public class TradeRequestManager {
    private TradeRequest t;

    private boolean canEditA = true;
    private boolean canEditB = true;

    private Trade trade;

    public TradeRequestManager(TradeRequest t) {
        this.t = t;
    }

    //user can set data, place and confirm
    public void setDate(String user, Date date) {
        if (user.equals(t.getUserA()) && canEditA){
            t.setDate(date);
            t.setNumberOfEditA(t.getNumberOfEditA() - 1);
            t.setConfirmationB(false);
            t.setConfirmationA(true);
        }
        else if (user.equals(t.getUserB()) && canEditB){
            t.setDate(date);
            t.setNumberOfEditB(t.getNumberOfEditB() - 1);
            t.setConfirmationA(false);
            t.setConfirmationB(true);
        }

    }

    public void setPlace(String user, String place) {
        if (user.equals(t.getUserA()) && canEditA){
            t.setPlace(place);
            t.setNumberOfEditA(t.getNumberOfEditA() - 1);
            t.setConfirmationB(false);
            t.setConfirmationA(true);
        }
        else if (user.equals(t.getUserB()) && canEditB){
            t.setPlace(place);
            t.setNumberOfEditB(t.getNumberOfEditB() - 1);
            t.setConfirmationA(false);
            t.setConfirmationB(true);
        }
    }

    public void setDateAndPlace(String user, Date date, String place){
        if (user.equals(t.getUserA()) && canEditA){
            t.setDate(date);
            t.setPlace(place);
            t.setNumberOfEditA(t.getNumberOfEditA() - 1);
            t.setConfirmationB(false);
            t.setConfirmationA(true);
        }
        else if (user.equals(t.getUserB()) && canEditB){
            t.setDate(date);
            t.setPlace(place);
            t.setNumberOfEditB(t.getNumberOfEditB() - 1);
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

    public boolean canEdit (String user, TradeRequest t){
        if (user.equals(t.getUserA()) && t.getNumberOfEditA() ==0){
            canEditA = false;
            return false;
        }
        else if (user.equals(t.getUserB()) && t.getNumberOfEditB()==0){
            canEditB = false;
            return false;
        } else return true;
    }

    public Trade getTrade() {
        return trade;
    }
}

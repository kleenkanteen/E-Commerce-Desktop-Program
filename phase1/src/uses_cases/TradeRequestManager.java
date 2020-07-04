package uses_cases;

import entities.*;

import java.util.Calendar;

public class TradeRequestManager {
    private TradeRequest t;

    private boolean canEditA = true;
    private boolean canEditB = true;

    private Trade trade;

    public TradeRequestManager(TradeRequest t) {
        this.t = t;
    }

    //user can set data, place and confirm
    public void setDate(String user, Calendar date) {
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

    public void setDateAndPlace(String user, Calendar date, String place){
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

    public Trade setConfirmation(String user, boolean confirmation) {
        if (user.equals(t.getUserA())){
            t.setConfirmationA(confirmation);
        }
        else t.setConfirmationB(confirmation);

        if (t.isPerm()){
            this.trade = new PermTrade(t.getUserA(), t.getUserB(), t.getItemA() , t.getItemB(), t.getDate());
            return this.trade;
        }
        else
            trade = new TempTrade(t.getUserA(), t.getUserB(), t.getItemA(), t.getItemB(), t.getDate());
            return trade;
        // Once both user confirms, create a TemTrade, or entities.PermTrade based on t.isPerm
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

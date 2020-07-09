package use_cases;

import entities.PermTrade;
import entities.TempTrade;
import entities.Trade;
import entities.TradeRequest;

import java.time.LocalDateTime;

public class TradeRequestManager {
    private TradeRequest t;

    private boolean canEditA = true;
    private boolean canEditB = true;

    private Trade trade;

    /**
     * a constructor for TradeRequestManager to edit the traderequet object
     * @param t the traderequest that is been edit
     */
    public TradeRequestManager(TradeRequest t) {
        this.t = t;
    }

    //user can set data, place and confirm

    /**
     * change the date in the tradreqeust object
     * @param user the user who is making the edit
     * @param date new date of the meeting
     */
    public void setDate(String user, LocalDateTime date) {
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

    /**
     * change the place in the traderequest object
     * @param user the user who is making the edit
     * @param place new place of the meeting
     */
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

    /**
     * change the date and place of the traderequest object
     * @param user the user who is making the edit
     * @param date new date of the meeting
     * @param place new place of the meeting
     */
    public void setDateAndPlace(String user, LocalDateTime date, String place){
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

    /**
     * set the user's confirmation state in the traderequest obejct.
     * @param user the user who is confirming
     * @return the trade object that store the involved users, item and meeting date, due date and place of the meeting
     */
    public Trade setConfirmation(String user) {
        if (user.equals(t.getUserA())){
            t.setConfirmationA(true);
        }
        else t.setConfirmationB(true);

        if (t.isPerm()){
            this.trade = new PermTrade(t.getUserA(), t.getUserB(), t.getItemA() , t.getItemB(), t.getDate());
            return this.trade;
        }
        else
            trade = new TempTrade(t.getUserA(), t.getUserB(), t.getItemA(), t.getItemB(), t.getDate(), t.getDate().plusDays(30));
            return trade;
        // Once both user confirms, create a TemTrade, or entities.PermTrade based on t.isPerm
    }

    /**
     * return the tradrequest object
     * @return the traderequst object
     */
    public TradeRequest getTradeRequest() {
        return t;
    }

    /**
     * check that if the user can edit or not
     * @param user the user who is editing
     * @return true if user can edit, false if user can not edit
     */
    public boolean canEdit (String user){
        if (user.equals(t.getUserA()) && this.t.getNumberOfEditA() ==0){
            canEditA = false;
            return false;
        }
        else if (user.equals(t.getUserB()) && this.t.getNumberOfEditB()==0){
            canEditB = false;
            return false;
        } else return true;
    }

    /**
     * returns the trade created after two user confirm
     * @return the trade created after two user confirm
     */
    public Trade getTrade() {
        return trade;
    }
}

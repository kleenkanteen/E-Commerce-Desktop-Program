package use_cases;

import entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TradeRequestManager {
    private TradeRequest t;
    private Trade trade;
    /**
     * a constructor for TradeRequestManager to edit the traderequet object
     * @param t the traderequest that is been edit
     */
    public TradeRequestManager(TradeRequest t) {
        this.t = t;
    }

    /**
     * a constructor of TradeRequestManager to generate the new traderequest object
     * @param content trade request message content
     * @param sender userA who sends the trade request
     */
    public TradeRequestManager(String content, String sender){
        this.t = new TradeRequest(content, sender);
    }

    public void setInfo (String userB, ArrayList<Item> itemA, ArrayList<Item> itemB, boolean perm){
        t.setUserB(userB);
        t.setItemA(itemA);
        t.setItemB(itemB);
        t.setPerm(perm);
    }

    /**
     * change the date and place of the traderequest object
     * @param user the user who is making the edit
     * @param date new date of the meeting
     * @param place new place of the meeting
     */
    public void setDateAndPlace(String user, LocalDateTime date, String place){
        if (user.equals(t.getUserA()) && canEdit(user)){
            t.setDate(date);
            t.setPlace(place);
            t.setNumberOfEditA(t.getNumberOfEditA() - 1);
            t.setContent("Your trade request has been edited");
        }
        else if (user.equals(t.getUserB()) && canEdit(user)){
            t.setDate(date);
            t.setPlace(place);
            t.setNumberOfEditB(t.getNumberOfEditB() - 1);
            t.setContent("Your trade request has been edited");
        }
    }

    /**
     * set the user's confirmation state in the traderequest obejct.
     * @return the trade object that store the involved users, item and meeting date, due date and place of the meeting
     */
    public Trade setConfirmation() {
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
        if (user.equals(t.getUserA())){
            return t.getNumberOfEditA() > 0;
        }
         else {
            return t.getNumberOfEditB() > 0;
        }
    }

    /**
     * returns the trade created after two user confirm
     * @return the trade created after two user confirm
     */
    public Trade getTrade() {
        return trade;
    }
}

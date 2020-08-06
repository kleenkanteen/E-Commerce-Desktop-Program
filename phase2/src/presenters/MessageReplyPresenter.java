package presenters;

import entities.*;

import java.time.LocalDateTime;

public class MessageReplyPresenter {

    /**
     * Print to the user that they have no messages
     */
    public String printNoMessages(){
        return "You have no messages to look through";
    }

    public String messageString(Message m){
        return m.toString();
    }


    /**
     * Print out a content message and the options they have
     * @param m the message
     */
    public String[] systemMessageAction(Message m){
        String[] s = new String[1];
        s[0] = "Delete";
        return s;
    }
    /**
     * Print out a content message and the options they have
     * @param m the message
     */
    public String[] privateMessageAction(Message m){
        String[] s = new String[2];
        s[0] = "Delete";
        s[1] = "Report";
        return s;
    }

    public String tradeRequestWarning(){
        return "Warning, you and the other trader has reached the max number of edits,\nselecting edit means " +
                "you will cancel this trade request";
    }

    public String tradeRequestCancel(){
        return "Trade request cancelled due to too much edits";
    }

    /**
     * Print out a decision message and the options they have
     * @param m the message
     */
    public String[] requestAction(Request m){
        return m.getOptions();
    }

    public String exit(){
        return "Exit";
    }

    public String skip(){
        return "Skip";
    }

    public String success(){
        return "Success";
    }

    public String error(){
        return "Error";
    }

    public String delete(){
        return "Delete";
    }

    public String confirm(){
        return "Confirm";
    }

    public String emptyString(){
        return "";
    }

    public String instructions(){
        return "Select a Message you want to view on the side list. Then click confirm to view.";
    }



    //-------------------Report System-----------------------//
    public String reportPrompt(){
        return "Write your reason for reporting:";
    }

    public String messageContent(Message m){
        return m.getContent();
    }

    public String report(){
        return "Report";
    }

    public String reportTitle(){
        return "Report System";
    }

    public String reportCompleted(){
        return "Report already made";
    }

    public String reportSuccess(){
        return "Your report has been sent to the Admin.";
    }

    //--------------------TradeRequestCannotConfirmGUI--------------------//
    public String titleTradeRequestCannotConfirm(){
        return "You are unable to confirm this trade right now";
    }

    public String tradeRequestCannotConfirmPrompt(TradeRequest m){
        return "You or the other trader cannot create a new trade at this time or the items involved " +
                "are not for trade at this time, try again later.\nTrade Request: " +
                m.toString() +
                "\nYou can delete this trade request or skip it for now.";
    }

    public String keepAndSkip(){
        return "Keep and Skip";
    }

    //--------------------TradeRequestEdit-----------------------//
    public String edit(){
        return "Edit";
    }
    public String titleTradeRequestEdit(){
        return "Make changes to the old trade request:";
    }
    public String datePrompt(String oldDate){
        return "Old date " + oldDate +"\nEnter the new date in the format yyyy-mm-dd hh:mm (or leave it empty):";
    }

    public String placePrompt(String oldPlace){
        return "Old place "+oldPlace+"\nEnter the new place (or leave it empty):";
    }

    public String wrongFormat(){
        return "Wrong format";
    }

    public String enterDateInPast(){
        return "Enter a time in the future";
    }

    public String noEdit(){
        return "Make at least one Edit or Exit";
    }

}

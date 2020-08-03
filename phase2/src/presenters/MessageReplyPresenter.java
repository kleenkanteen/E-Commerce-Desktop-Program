package presenters;

import entities.*;

import java.time.LocalDateTime;

public class MessageReplyPresenter {
    /**
     * Class constructor.
     * Create a new MessageReplyMenu, the presenter that prints to the user in console
     * for message replying functions
     */
    public MessageReplyPresenter(){

    }

    /**
     * Prompts the user to ask if they would like to look through their messages
     * @param messageSize the size of the list of messages
     */
    public void printMenuPrompt(int messageSize){
        System.out.println("You have "+messageSize+" messages");
        System.out.println("Choose your option below:");
        System.out.println("[1] Look through messages");
        System.out.println("[2] Exit");
    }

    /**
     * Print to the user that they have no messages
     */
    public String printNoMessages(){
        return "You have no messages to look through";
    }

    public String returnMessageString(Message m){
        return m.toString();
    }

    /**
     * Print to the user that they will exit this menu
     */
    public void printExit(){
        System.out.println("Returning you to the user menu");
    }

    /**
     * Print out a content message and the options they have
     * @param m the message
     */
    public String[] systemMessageActionPrompt(Message m){
        String[] s = new String[1];
        s[0] = "Delete";
        return s;
    }
    /**
     * Print out a content message and the options they have
     * @param m the message
     */
    public String[] privateMessageActionPrompt(Message m){
        String[] s = new String[2];
        s[0] = "Delete";
        s[1] = "Report";
        return s;
    }
    /**
     * Print out a decision message and the options they have
     * @param m the message
     */
    public String[] requestActionPrompt(Request m){
        return m.getOptions();
    }
    /**
     * Print to the user that an error as occurred
     */
    public void printErrorOccurred(){
        System.out.println("Something went wrong");
    }

    /**
     * Print to the user that they are entering invalid input
     */
    public void printInvalidInput(){
        System.out.println("Not an option. Try again, enter the option number or character");
    }

    /**
     * Print to the user that their trade request has reached it's limit of edits
     */
    public void tradeRequestWarning(){
        System.out.println("Warning, you and the other trader has reached the max number of edits,\nselecting edit means " +
                "you will cancel this trade request");
    }

    /**
     * Print to the user that their trade request is cancelled due to too much edits
     */
    public void tradeRequestCancel(){
        System.out.println("Trade request cancelled due to too much edits");
    }

    /**
     * Prompt the user to enter a new date for the trade request
     * @param oldTime The old time of the meet up
     */
    public void changeDatePrompt(LocalDateTime oldTime){
        System.out.println("Enter the new date in the format yyyy-MM-dd HH:mm");
        System.out.println("Your old date: "+oldTime);
    }

    /**
     * Prompt the user to enter a new place for the trade request
     * @param oldPlace The old place of the meet up
     */
    public void changePlacePrompt(String oldPlace){
        System.out.println("Enter the new place");
        System.out.println("Your old place: "+oldPlace);
    }

    /**
     * Print to the user that the format of their input is wrong
     */
    public void wrongFormat(){
        System.out.println("wrong format");
    }

    /**
     * Prompt the user to make a decision due to the trade request they want to confirm cannot be made into a trade
     */
    public void printCannotTradePrompt(){
        System.out.println("You or the other trader cannot create a new trade at this time or the items involved are not" +
                " for trade at this time, try again later. \n" +
                "You can delete this trade request or skip it for now.");
        System.out.println("Choose your option below:");
        System.out.println("[1] Delete");
        System.out.println("[2] Skip");
    }

    /**
     * Prompt the user their action is successful
     */
    public void success(){
        System.out.println("Success");
    }

    /**
     * Tell the user the date they enter is in the past
     */
    public void wrongDate(){
        System.out.println("Enter a date in the future");
    }

    public void promptReasonToReport(){
        System.out.println("Write in one line what you are reporting this message (examples: Harassment, Ads)");
        System.out.println("Enter [-1] to stop reporting and skip this message instead");
    }

    public String returnExitPrompt(){
        return "Exit";
    }

    public String returnSkipPrompt(){
        return "Skip";
    }
}

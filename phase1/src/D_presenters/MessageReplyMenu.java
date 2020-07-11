package D_presenters;

import F_entities.DecisionMessage;
import F_entities.Message;
import F_entities.TradeRequest;

import java.time.LocalDateTime;

public class MessageReplyMenu {
    /**
     * Class constructor.
     * Create a new MessageReplyMenu, the presenter that prints to the user in console
     * for message replying functions
     */
    public MessageReplyMenu(){

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
     * Print out a content message and the options they have
     * @param m the message
     */
    public void printContentMessagePrompt(Message m){
        System.out.println(m);
        System.out.println("Choose your option below:");
        System.out.println("[1] Delete this message");
        System.out.println("[2] Skip this message");
        System.out.println("[3] Exit");
    }
    /**
     * Print out a decision message and the options they have
     * @param m the message
     */
    public void printDecisionMessagePrompt(DecisionMessage m){
        System.out.println(m);
        System.out.println("Or Choose your option below:");
        System.out.println("[a] Skip this message");
        System.out.println("[b] Exit");
    }
    /**
     * Print out the old date and place of the old trade request and the options they have to edit the
     * trade request
     * @param t the trade request
     */
    public void printEditTradeRequestPrompt(TradeRequest t){
        System.out.println("Old Date: "+ t.getDate());
        System.out.println("Old Place: "+ t.getPlace());
        System.out.println("Choose your option below:");
        System.out.println("[1] Change Place");
        System.out.println("[2] Change Date");
        System.out.println("[3] Change Both");
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
        System.out.println("Warning, if the max number of edits for both traders are reach, selecting edit means " +
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
     */
    public void changeDatePrompt(LocalDateTime oldTime){
        System.out.println("Enter the new date in the format yyyy-MM-dd HH:mm");
        System.out.println("Your old date: "+oldTime);
    }

    /**
     * Prompt the user to enter a new place for the trade request
     */
    public void changePlacePrompt(String oldPlace){
        System.out.println("Enter the new date in the format yyyy-MM-dd HH:mm");
        System.out.println("Your old date: "+oldPlace);
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
        System.out.println("You or the other trader cannot create a new trade at this time or the items involved or not" +
                "for trade at this time, try again later. " +
                "You can delete this trade request or skip it for now.");
        System.out.println("Choose your option below:");
        System.out.println("[1] Delete");
        System.out.println("[2] Skip");
    }

}
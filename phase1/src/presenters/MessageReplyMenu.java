package presenters;

import entities.DecisionMessage;
import entities.Message;
import entities.TradeRequest;

import java.time.LocalDateTime;

public class MessageReplyMenu {

    public MessageReplyMenu(){

    }
    public void printMenuPrompt(int messageSize){
        System.out.println("You have "+messageSize+" messages");
        System.out.println("Choose your option below:");
        System.out.println("[1] Look through messages");
        System.out.println("[2] Exit");
    }
    public void printContentMessagePrompt(Message m){
        System.out.println(m);
        System.out.println("Choose your option below:");
        System.out.println("[1] Delete this message");
        System.out.println("[2] Skip this message");
        System.out.println("[3] Exit");
    }
    public void printDecisionMessagePrompt(DecisionMessage m){
        System.out.println(m);
        System.out.println("Or Choose your option below:");
        System.out.println("[a] Skip this message");
        System.out.println("[b] Exit");
    }
    public void printEditTradeRequestPrompt(TradeRequest t){
        System.out.println("Old Date: "+ t.getDate());
        System.out.println("Old Place: "+ t.getPlace());
        System.out.println("Choose your option below:");
        System.out.println("[1] Change Place");
        System.out.println("[2] Change Date");
        System.out.println("[3] Change Both");
    }
    public void printErrorOccurred(){
        System.out.println("Something went wrong");
    }

    public void printInvalidInput(){
        System.out.println("Not an option. Try again, enter the option number or character");
    }

    public void tradeRequestWarning(){
        System.out.println("Warning, if the max number of edits for both traders are reach, selecting edit means " +
                "you will cancel this trade request");
    }
    public void tradeRequestCancel(){
        System.out.println("Trade request cancelled");
    }
    public void changeDatePrompt(LocalDateTime oldTime){
        System.out.println("Enter the new date in the format yyyy-MM-dd HH:mm");
        System.out.println("Your old date: "+oldTime);
    }
    public void changePlacePrompt(String oldPlace){
        System.out.println("Enter the new date in the format yyyy-MM-dd HH:mm");
        System.out.println("Your old date: "+oldPlace);
    }
    public void wrongFormat(){
        System.out.println("wrong format");
    }
    public void printCannotTradePrompt(){
        System.out.println("You or the other trader cannot create a new trade at this time or the items involved or not" +
                "for trade at this time, try again later. " +
                "You can delete this trade request or skip it for now.");
        System.out.println("Choose your option below:");
        System.out.println("[1] Delete");
        System.out.println("[2] Skip");
    }

}

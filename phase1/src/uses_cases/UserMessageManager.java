package uses_cases;

import entities.Message;
import entities.TradeRequestMessage;
import entities.TradeRequest;
import entities.PermTrade;
import entities.TempTrade;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.System;

// THESE WILL ALL BE MOVED TO USERMANAGER BEING PUT HERE FOR ORGANIZATION PURPOSES

public class UserMessageManager {
    ArrayList<Message> userMessages;

    public UserMessageManager(ArrayList<Message> message) {
        this.userMessages = message;
    }

    /**
     * Add a message to this user's inbox.
     * @param message the message object to be added
     */
    // for when I move these methods into UserManager
    /**
    public void addMessage(String username, Message message) {
        ArrayList<Message> userMessages = this.allUsers.get(username).getMessages();
        userMessages.add(message);
        this.allUsers.get(username).setMessages(userMessages);
     }
    */
    public void addMessage(Message message) { this.userMessages.add(message); }

    /**
     * Remove a specified message.
     * @param message the message object to remove
     */
    // for when I move these methods into UserManager
    /**
    public void removeMessage(String username, Message message) {
        ArrayList<Message> userMessages = this.allUsers.get(username).getMessages();
        userMessages.remove(message);
        this.allUsers.get(username).setMessages(userMessages);
    }
     */
    public void removeMessage(Message message) { this.userMessages.remove(message); }

    // this should be in UserManager
    // public ArrayList<Message> getUserMessages(String username) {this.allUsers.get(username).getMessages()}

    /**
     * Allows a user to send another user (admin or user) a message.
     * @param username the user sending the message.
     * @return the message object
     */
    public Message sendMessage(String username, String messageContent) {
        return new Message("From user: " + username + "\n" + messageContent, username);
    }

    /**
     * Allows a user to respond to a TradeRequestMessage
     * @param username the username of the user responding (PersonB) to this message
     * @param message the TradeRequestMessage object
     */
    public void manageTradeRequest(String username, TradeRequestMessage message) {
        // read in Scanner inputs
        Scanner input = new Scanner(System.in);
        TradeRequest tradeRequest = message.getTradeContent();
        String exitInput = "";
        while(!exitInput.equals("back")) {
            System.out.println(message.toString());
            System.out.println("Enter 'back' to go back to the messages menu.");
            exitInput = input.nextLine();
            // confirm
            if (exitInput.toLowerCase().equals("confirm")) {
                // create a trade object from the TradeRequest
                // confirmTrade(trade whatever);
                System.out.println("Trade confirmed.");
                exitInput = "back";
            }
            // deny
            else if (exitInput.toLowerCase().equals("deny")) {
                String tradePartner = tradeRequest.getTradePartner(username);
                Message deniedRequest = new Message("Your message to + " + username + " has been rejected.");
                // this.allUsers.get(tradePartner).addMessage(tradePartner, deniedRequest);
                // this.allUsers.get(username).removeMessage(message);
                System.out.println("Trade denied.");
                exitInput = "back";
            }
            // edit
            else if (exitInput.toLowerCase().equals("edit")) {
                TradeRequestManager tradeRequestManager = new TradeRequestManager(tradeRequest);
                if(tradeRequestManager.canEdit(username, tradeRequest)) {
                    try {
                        TradeRequestMessage requestMessage = editTradeRequest(username,
                                message.getTradeContent(), tradeRequestManager);
                        String tradePartner = tradeRequest.getTradePartner(username);
                        // this.allUsers.get(username).removeMessage(message);
                        // this.allUsers.get(tradePartner).addMessage(requestMessage);
                        System.out.println("Edit to this trade request successful! Returning from menu.");
                        exitInput = "back";
                    }
                    catch(ParseException ex) {
                        System.out.println("You input date information incorrectly! Try again.");
                    }
                }
                else System.out.println("You have reached the maximum number of edits. Confirm or deny this trade.");
            }
            // if none of the above and not "back"
            else if(!exitInput.toLowerCase().equals("back")) {
                System.out.println("Could not understand input. Please try again.");
            }
        }
    }

    /**
     * Private helper for manageTradeRequest that edits a trade request.
     * @param username name of user editing this trade request
     * @param tradeRequest the trade request object
     * @return The now edited trade request object in a TradeRequestMessage
     * @throws ParseException if date is input wrong (might be finicky!)
     */
    private TradeRequestMessage editTradeRequest(String username, TradeRequest tradeRequest,
                                                 TradeRequestManager tradeRequestManager) throws ParseException {
        Scanner input = new Scanner(System.in);
        System.out.println("Edit time, location, or both?");
        String choice = input.nextLine();
        // edit time
        if(choice.toLowerCase().equals("time")) {
            System.out.println("Old time + " + tradeRequest.getDate() + "\n" +
                    "Enter in new time to meet up in 'DD/MM/YYYY format.");
            String newDateInput = input.nextLine();
            Date newDate = new SimpleDateFormat("dd/MM/yyyy").parse(newDateInput);
            tradeRequestManager.setDate(username, newDate);
            return new TradeRequestMessage("User " + username + " has made an edit to your trade request.",
                    tradeRequestManager.getTradeRequest());
        }
        // edit location
        else if(choice.toLowerCase().equals("location")) {
            System.out.println("Old location: " + tradeRequest.getPlace() + "\n" +
                    "Enter in new location to meet up. ");
            String newPlace = input.nextLine();
            tradeRequestManager.setPlace(username, newPlace);
            return new TradeRequestMessage("User " + username + " has made an edit to your trade request.",
                    tradeRequestManager.getTradeRequest());
        }
        // edit both
        else {
            System.out.println("Old time + " + tradeRequest.getDate() + "\n" +
                    "Enter in new time to meet up in 'DD/MM/YYYY format.");
            String newDateInput = input.nextLine();
            Date newDate = new SimpleDateFormat("dd/MM/yyyy").parse(newDateInput);
            tradeRequestManager.setDate(username, newDate);
            System.out.println("Old location: " + tradeRequest.getPlace() + "\n" +
                    "Enter in new location to meet up. ");
            String newPlace = input.nextLine();
            tradeRequestManager.setPlace(username, newPlace);
            return new TradeRequestMessage("User " + username + " has made an edit to your trade request.",
                    tradeRequestManager.getTradeRequest());
        }
    }
}

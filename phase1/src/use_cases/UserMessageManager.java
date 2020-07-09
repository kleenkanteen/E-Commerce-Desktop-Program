package use_cases;

import entities.Message;
import entities.TradeRequestMessage;
import entities.TradeRequest;
import entities.Trade;
import entities.User;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.System;

public class UserMessageManager {
    HashMap<String, User> mapOfUsers;
    String nameOfCurrentUser;

    /**
     * Constructs a UserMessageManager
     * @param mapOfUsers a hashmap of all user objects (HOPEFULLY NOT NEEDED)
     * @param nameOfCurrentUser the string username of the current user logged in
     */
    public UserMessageManager(HashMap<String, User> mapOfUsers, String nameOfCurrentUser) {
        this.mapOfUsers = mapOfUsers;
        this.nameOfCurrentUser = nameOfCurrentUser;
    }

    /**
     * Add a message to this user's inbox.
     * @param message the message object to be added
     * @param recipient the user that receives this message
     */
    public void addMessage(Message message, String recipient) {
        ArrayList<Message> currMessages = this.mapOfUsers.get(recipient).getMessages();
        currMessages.add(message);
        this.mapOfUsers.get(recipient).setMessages(currMessages);
    }

    /**
     * Remove a specified message.
     * @param message the message object to remove
     * @param username the user from which the message is removed
     */
    public void removeMessage(Message message, String username) {
        ArrayList<Message> currMessages = this.mapOfUsers.get(username).getMessages();
        currMessages.remove(message);
        this.mapOfUsers.get(username).setMessages(currMessages);
    }

    /**
     * Allows a user to send another user (admin or user) a message.
     * @param username the user sending the message.
     */
    public void sendMessage(String username, String recipient, String messageContent) {
        Message newMessage =  new Message("From user: " + username + "\n" + messageContent, username);
        addMessage(newMessage, recipient);
    }

    // probably won't be needed, but would return the hashmap of users in this Manager to presumably
    // reset the controller's version of users? idk how the aliasing would work but something like that
    // public HashMap<String, User> setUserObjects() {  return this.mapOfUsers; }

    /**
     * Allows a user to respond to a TradeRequestMessage
     * @param message the TradeRequestMessage object
     */
    public void manageTradeRequest(TradeRequestMessage message) {
        // read in Scanner inputs
        Scanner input = new Scanner(System.in);
        TradeRequest tradeRequest = message.getTradeContent();
        String tradePartner = tradeRequest.getTradePartner(this.nameOfCurrentUser);
        TradeRequestManager tradeRequestManager = new TradeRequestManager(tradeRequest);
        String exitInput = "";
        while(!exitInput.equals("back")) {
            System.out.println(message.toString());
            System.out.println("Enter 'back' to go back to the messages menu.");
            exitInput = input.nextLine();
            // confirm
            if (exitInput.toLowerCase().equals("confirm")) {
                confirmTrade(tradeRequestManager.setConfirmation(this.nameOfCurrentUser, true), tradePartner);
                System.out.println("Trade confirmed.");
                exitInput = "back";
            }
            // deny
            else if (exitInput.toLowerCase().equals("deny")) {
                Message deniedRequest = new Message("Your message to " +
                        this.nameOfCurrentUser + " has been rejected.");
                addMessage(deniedRequest, tradePartner);
                removeMessage(message, this.nameOfCurrentUser);
                System.out.println("Trade denied.");
                exitInput = "back";
            }
            // edit
            else if (exitInput.toLowerCase().equals("edit")) {
                // if this user can edit
                if(tradeRequestManager.canEdit(this.nameOfCurrentUser)) {
                    try {
                        TradeRequestMessage requestMessage = editTradeRequest(this.nameOfCurrentUser,
                                tradeRequest, tradeRequestManager);
                        removeMessage(message, this.nameOfCurrentUser);
                        addMessage(requestMessage, tradePartner);
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
            System.out.println("Old time: " + tradeRequest.getDate() + "\n" +
                    "Enter in new time to meet up in 'DD/MM/YYYY HH:MM' format.");
            String newDateInput = input.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
            // tradeRequestManager.setDate(username, LocalDateTime.parse(newDateInput, formatter));
            return new TradeRequestMessage("User " + username + " has made an edit to your trade request.",
                    tradeRequestManager.getTradeRequest(), this.nameOfCurrentUser);
        }
        // edit location
        else if(choice.toLowerCase().equals("location")) {
                System.out.println("Old location: " + tradeRequest.getPlace() + "\n" +
                        "Enter in new location to meet up. ");
                String newPlace = input.nextLine();
                tradeRequestManager.setPlace(username, newPlace);
                return new TradeRequestMessage("User " + username + " has made an edit to your trade request.",
                        tradeRequestManager.getTradeRequest(), this.nameOfCurrentUser);
        }
        // edit both
        else {
            System.out.println("Old time + " + tradeRequest.getDate() + "\n" +
                    "Enter in new time to meet up in 'DD/MM/YYYY format.");
            String newDateInput = input.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
            // tradeRequestManager.setDate(username, LocalDateTime.parse(newDateInput, formatter));
            System.out.println("Old location: " + tradeRequest.getPlace() + "\n" +
                    "Enter in new location to meet up. ");
            String newPlace = input.nextLine();
            tradeRequestManager.setPlace(username, newPlace);
            return new TradeRequestMessage("User " + username + " has made an edit to your trade request.",
                    tradeRequestManager.getTradeRequest(), this.nameOfCurrentUser);
        }
    }

    /**
     * Helper function to add the trade to both users involved in said trade.
     * @param trade the trade object to be stored in both user's Trade Histories
     * @param tradePartner the name of the currently logged in user's trade partner
     */
    private void confirmTrade(Trade trade, String tradePartner) {
        this.mapOfUsers.get(tradePartner).addTradeHistory(trade);
        this.mapOfUsers.get(this.nameOfCurrentUser).addTradeHistory(trade);
    }
}

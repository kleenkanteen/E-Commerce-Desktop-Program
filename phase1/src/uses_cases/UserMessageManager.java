package uses_cases;

import entities.Message;
import entities.TradeRequestMessage;
import entities.PermTrade;
import entities.TempTrade;
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
        // NEED A SET MESSAGES FUNCTION IN USER
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
        // NEED A SET MESSAGES FUNCTION IN USER
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
        return new Message("From user: " + username + "\n" + messageContent);
    }

    /**
     * WORK IN PROGRESS!!!
     * Allows a user to respond to a TradeRequestMessage
     * @param username the username of the user responding (PersonB) to this message
     * @param message the TradeRequestMessage object
     */
    public void manageTradeRequest(String username, TradeRequestMessage message) {
        // read in Scanner inputs, set up tradeRequestManager
        Scanner input = new Scanner(System.in);
        TradeRequestManager tradeRequestManager = new TradeRequestManager(message.getTradeContent());
        String exitInput = "";
        while(!exitInput.equals("back")) {
            System.out.println(message);
            System.out.println("Enter 'new trade' to offer a counter-trade. \n " +
                    "Enter 'back' to go back to the messages menu.");
            exitInput = input.nextLine();
            // confirm
            if (exitInput.toLowerCase().equals("confirm")) {

            }
            // deny
            else if (exitInput.toLowerCase().equals("deny")) {

            }
            // edit
            else if (exitInput.toLowerCase().equals("edit")) {

            }
            // if none of the above and not "back"
            else if(!exitInput.toLowerCase().equals("back")) {
                System.out.println("Could not understand input.");
            }
        }
    }
}

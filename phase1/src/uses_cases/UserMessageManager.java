package uses_cases;

import entities.Message;
import entities.TradeRequestMessage;
import entities.TradeRequestMessage;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.System;

public class UserMessageManager {
    ArrayList<Message> userMessages;

    public UserMessageManager(ArrayList<Message> message) {
        this.userMessages = message;
    }

    // public boolean removeMessage() {}

    // this should be in UserManager
    // public ArrayList<Message> getUserMessages(String username) {this.allUsers.get(username).getMessages()}

    public Message sendAdminUnfreezeMessage(String username) {
        return new Message("Please unfreeze " + username + ", they are very sorry! :)");
    }

    public void acceptTradeRequest(String username) {}


}

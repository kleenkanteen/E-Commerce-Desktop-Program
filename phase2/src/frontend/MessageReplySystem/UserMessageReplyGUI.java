package frontend.MessageReplySystem;

import entities.Message;
import use_cases.AdminManager;
import use_cases.GlobalInventoryManager;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.util.*;

public class UserMessageReplyGUI extends MessageReplyGUI{
    public UserMessageReplyGUI(AdminManager adminManager, GlobalInventoryManager globalInventoryManager,
                                  TradeManager tradeManager, UserManager userManager, String accountUsername){
        super(adminManager, globalInventoryManager, tradeManager, userManager, accountUsername);
    }

    public List<Message> getMessage(){
        return userManager.getUserMessages(accountUsername);
    }

    public void saveMessage(List<Message> messages){
        userManager.setUserMessages(accountUsername, messages);
    }
}
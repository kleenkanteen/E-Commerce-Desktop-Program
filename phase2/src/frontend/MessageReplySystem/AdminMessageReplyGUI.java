package frontend.MessageReplySystem;

import entities.Message;
import use_cases.AdminManager;
import use_cases.GlobalInventoryManager;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.util.*;

public class AdminMessageReplyGUI extends MessageReplyGUI{
    public AdminMessageReplyGUI(AdminManager adminManager, GlobalInventoryManager globalInventoryManager,
                               UserManager userManager, String accountUsername){
        super(adminManager, globalInventoryManager, null, userManager, accountUsername);
    }

    public List<Message> getMessage(){
        return adminManager.getAdminMessages();
    }

    public void saveMessage(List<Message> messages){
        adminManager.setAdminMessages(messages);
    }
}
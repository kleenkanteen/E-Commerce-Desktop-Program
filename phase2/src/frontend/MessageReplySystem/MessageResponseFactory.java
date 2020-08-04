package frontend.MessageReplySystem;

import entities.*;
import use_cases.AdminManager;
import use_cases.GlobalInventoryManager;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.util.List;

public class MessageResponseFactory {
    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;
    private TradeManager tradeManager;
    private String accountUsername;
    private AdminManager adminManager;
    private List<Message> messageList;

    MessageResponseFactory(AdminManager adminManager, GlobalInventoryManager globalInventoryManager,
                                  TradeManager tradeManager, UserManager userManager,
                                  List<Message> messageList, String accountUsername){
        this.adminManager = adminManager;
        this.globalInventoryManager = globalInventoryManager;
        this.tradeManager = tradeManager;
        this.userManager = userManager;
        this.accountUsername = accountUsername;
        this.messageList = messageList;
    }

    MessageResponse getMessageResponse(Message message){
        if(message instanceof SystemMessage){
            return new SystemMessageResponse(message, messageList);
        }
        else if(message instanceof PrivateMessage){
            return new PrivateMessageResponse(message, adminManager, messageList, accountUsername);
        }
        else{
            return new FreezeRequestResponse(message);
        }
    }
}

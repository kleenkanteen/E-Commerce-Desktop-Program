package frontend.MessageReplySystem;

import entities.*;
import use_cases.AdminManager;
import use_cases.GlobalInventoryManager;
import use_cases.TradeManager;
import use_cases.UserManager;

public class MessageResponseFactory {
    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;
    private TradeManager tradeManager;
    private String accountUsername;
    private AdminManager adminManager;

    public MessageResponseFactory(){

    }

    public MessageResponseFactory(AdminManager adminManager, GlobalInventoryManager globalInventoryManager,
                                  TradeManager tradeManager, UserManager userManager, String accountUsername){
        this.adminManager = adminManager;
        this.globalInventoryManager = globalInventoryManager;
        this.tradeManager = tradeManager;
        this.userManager = userManager;
        this.accountUsername = accountUsername;
    }

    public MessageResponse getMessageResponse(Message message){
        if(message instanceof SystemMessage){
            return new SystemMessageResponse(message);
        }
        else if(message instanceof PrivateMessage){
            return new PrivateMessageResponse(message);
        }
        else{
            return new FreezeRequestResponse(message);
        }
    }
}

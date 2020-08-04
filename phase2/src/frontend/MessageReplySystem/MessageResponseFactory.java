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
                                  String accountUsername){
        this.adminManager = adminManager;
        this.globalInventoryManager = globalInventoryManager;
        this.tradeManager = tradeManager;
        this.userManager = userManager;
        this.accountUsername = accountUsername;
    }

    void setMessageList(List<Message> messageList){
        this.messageList = messageList;
    }

    List<Message> getMessageList(){
        return messageList;
    }

    MessageResponse getMessageResponse(Message message){
        if(message instanceof SystemMessage){
            return new SystemMessageResponse(message, messageList);
        }
        else if(message instanceof PrivateMessage){
            return new PrivateMessageResponse(message, adminManager, messageList, accountUsername);
        }
        else if(message instanceof FreezeRequest){
            return new FreezeRequestResponse((FreezeRequest) message, messageList, userManager);
        }
        else if(message instanceof UnfreezeRequest){
            return new UnfreezeRequestResponse((UnfreezeRequest) message, messageList, userManager);
        }
        else if(message instanceof ReportRequest){
            return new ReportRequestResponse((ReportRequest) message, messageList, userManager);
        }
        else if(message instanceof UnbanRequest){
            return new UnbanRequestResponse((UnbanRequest) message, messageList, userManager);
        }
        else {
            return new TradeRequestResponse((TradeRequest)message, messageList, userManager, globalInventoryManager,
                    tradeManager, accountUsername);
        }
    }
}

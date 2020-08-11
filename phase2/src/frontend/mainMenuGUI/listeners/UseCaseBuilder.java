package frontend.mainMenuGUI.listeners;
import entities.*;
import use_cases.*;

import java.util.Map;
import java.util.List;

public class UseCaseBuilder {
    public AdminManager getAdminManager(Map<String, Admin> adminList, List<Message> messageList){
        return new AdminManager(adminList, messageList);
    }
    public UserManager getUserManager(Map<String, User> userList){
        return new UserManager(userList);
    }
    public TradeManager getTradeManager(Map<String, List<Trade>> tradeList){
        return new TradeManager(tradeList);
    }
    public GlobalInventoryManager getGlobalInventoryManager(GlobalInventory globalInventory){
        return new GlobalInventoryManager(globalInventory);
    }
    public GlobalWishlistManager getGlobalWishlistManager(GlobalWishlist globalWishlist){
        return new GlobalWishlistManager(globalWishlist);
    }
    public DemoUserManager getDemoUserManager(String username, String password){
        return new DemoUserManager(username, password);
    }

}

package C_controllers;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Arrays;
import F_entities.*;
import E_use_cases.*;
import D_presenters.UserPresenter;
import G_exceptions.UserFrozenException;


public class UserMenu {
    private String currUser;
    private ArrayList<Message> adminMessages;
    private UserPresenter userPresenter;
    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;
    private TradeManager tradeManager;
    private Scanner input;


    public UserMenu(String currUser, UserManager userManager, TradeManager tradeManager,
                    GlobalInventoryManager globalInventoryManager, GlobalWishlistManager globalWishlistManager,
                    ArrayList<Message> adminMessages) {
        this.currUser = currUser;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
        this.adminMessages = adminMessages;
        this.userPresenter = new UserPresenter();
    }

    /**
     * Runs the user menu.
     */
    public void run() {
        // instantiate needed objects
        Scanner input = new Scanner(System.in);
        String userInput = "";

        // check to see if user can trade
        checkUserStatus();
        // check for all incomplete trades to confirm
        confirmIncompleteUserTrades();

        while(!userInput.equals("exit")) {
            this.userPresenter.promptUserMenu();
            userInput = input.nextLine();
            // look at/change user information
            if (userInput.equals("1")) {
                browseThroughUserInfo();
            }
            // look at global inventory
            else if (userInput.equals("2")) {
                GlobalInventoryController globalInventory = new GlobalInventoryController();
                globalInventory.run(this.globalInventoryManager, this.userManager, this.currUser);
            }
            // access global wishlist/lend to other users
            else if (userInput.equals("3")) {
                loanPersonalItemToOtherUsers();
            }
            // messages
            else if (userInput.equals("4")) {
                UserMessageReplySystem messageSystem = new UserMessageReplySystem(this.userManager,
                        this.globalInventoryManager, this.tradeManager, this.currUser);
                messageSystem.run();
            }
            // create a new item for admin approval
            else if (userInput.equals("5")) {
                this.userPresenter.createNewItemPrompt(0);
                String itemName = input.nextLine();
                this.userPresenter.createNewItemPrompt(1);
                String itemDescription = input.nextLine();
                this.adminMessages.add(this.userManager.createNewItem(this.currUser, itemName, itemDescription));
                this.userPresenter.newItemMessageSentToAdmin();
            }
            // send admin an unfreeze request message
            else if (userInput.equals("6")) {
                if(this.userManager.getUserFrozenStatus(this.currUser)) {
                    this.adminMessages.add(new UnfreezeRequestMessage("User " +
                            this.currUser +" has requested to be unfrozen.", this.currUser));
                    this.userPresenter.unfreezeRequestSent();
                }
                else {
                    this.userPresenter.userNotFrozen();
                }
            }
            // exit
            else if (userInput.equals("7")) {
                userInput = "exit";
            }
            else {
                this.userPresenter.inputError();
            }
        }
    }

    /**
     * Helper method for run() that allows a user to access their personal information
     */
    private void browseThroughUserInfo() {
        Scanner input = new Scanner(System.in);
        String userInput = "";
        while(!userInput.equals("exit")) {
            this.userPresenter.userMenuUserInfoPrompts();
            userInput = input.nextLine();
            // change password
            if (userInput.equals("1")) {
                this.userPresenter.setNewPasswordPrompt();
                String newPass = input.nextLine();
                if(!newPass.toLowerCase().equals("exit")) {
                    this.userManager.changePassword(this.currUser, newPass);
                }
            }
            // view frequent trading partners
            else if (userInput.equals("2")) {
                String[] tradingPartners = this.tradeManager.getFrequentTradingPartners(this.currUser);
                // find a better way to do this
                for(String tradePartner: tradingPartners) {
                    if(tradePartner == null) {
                        this.userPresenter.noTradingPartners();
                        break;
                    }
                    this.userPresenter.printUserTradePartners(tradePartner);
                }
            }
            // view 3 most recent trades
            else if (userInput.equals("3")) {
                Trade[] recentTradeHistory = this.tradeManager.getRecentCompletedTrade(this.currUser);
                // find a better way to do this
                for(Trade trade : recentTradeHistory) {
                    if(trade == null) {
                        this.userPresenter.noRecentTrades();
                        break;
                    }
                    this.userPresenter.tradeToString(trade);
                }
            }
            // look at personal inventory
            else if (userInput.equals("4")) {
                browseThroughUserInventory();
            }
            // look at personal wishlist
            else if (userInput.equals("5")) {
                browseThroughUserWishlist();
            }
            // exit
            else if (userInput.equals("6")) {
                userInput = "exit";
            }
            // input error
            else {
                this.userPresenter.inputError();
            }
        }
    }

    /**
     * The helper method for run() that checks on a user's trade status
     */
    private void checkUserStatus() {
        boolean tooManyIncomplete = false;
        boolean tooManyBorrowVLoan = false;
        if (!this.userManager.getUserFrozenStatus(this.currUser)) {
            // check num of incomplete trades
            if(this.tradeManager.tradesToConfirm(this.currUser).size() >=
                    this.userManager.getUserIncompleteTrades(this.currUser)) {
                this.userPresenter.tooManyIncompleteTrades();
                tooManyIncomplete = true;
            }
            // check num of borrows v. loans
            if((this.tradeManager.getBorrowedTimes(this.currUser) - this.tradeManager.getLendTimes(this.currUser)) >
                    this.userManager.getUserBorrowsVLoans(this.currUser)) {
                this.userPresenter.tooManyBorrowsVLoans(this.tradeManager.getBorrowedTimes(this.currUser) -
                        this.tradeManager.getLendTimes(this.currUser));
                tooManyBorrowVLoan = true;
            }
            // check num of trades
            if(tradeManager.numberOfTradesCreatedThisWeek(this.currUser) >=
                    this.userManager.getTradesPerWeekForUser(this.currUser)) {
                this.userPresenter.tooManyTradesThisWeek();
            }
            // if too many incompletes or too many borrows, request Freeze of this account
            if(tooManyIncomplete || tooManyBorrowVLoan) {
                this.userPresenter.requestFreezeOfUser();
                FreezeRequestMessage newFreezeRequest = new FreezeRequestMessage("User " + this.currUser +
                        " has too many incomplete trades and their account should be frozen.", this.currUser);
                this.adminMessages.add(newFreezeRequest);
            }
        }
        else {
            this.userPresenter.userAccountFrozen();
        }
    }

    /**
     * Helper for run() that allows a user to confirm of their incomplete trades.
     */
    private void confirmIncompleteUserTrades() {
        Scanner input = new Scanner(System.in);
        ArrayList<Trade> incompletes = this.tradeManager.tradesToConfirm(this.currUser);
        // check to make sure that the user has unconfirmed trades
        if(incompletes.size() != 0) {
            // instantiate unconfirmed trades
            this.userPresenter.promptUserToConfirmTrades();
            String userInput;
            boolean continueCheckingUnconfirmed;
            // go through all unconfirmed trades
            for(Trade trade : incompletes) {
                this.userPresenter.tradeToString(trade);
                continueCheckingUnconfirmed = true;
                while(continueCheckingUnconfirmed) {
                    this.userPresenter.checkUnconfirmedTradesPrompts();
                    userInput = input.nextLine();
                    // confirm meeting
                    if(userInput.equals("1")) {
                        this.tradeManager.setConfirm(this.currUser, trade, true);
                        this.userPresenter.unconfirmedTradeSystemResponse(0);
                        // if the trade is a permanent trade
                        if(trade instanceof PermTrade) {
                            // remove all traderA items from wishlist
                            for(Item item : trade.getTraderAItemsToTrade()) {
                                this.globalInventoryManager.removeItem(item.getItemID());
                            }
                            // remove all tradeB items from wishlist
                            for (Item item : trade.getTraderBItemsToTrade()) {
                                this.globalInventoryManager.removeItem(item.getItemID());
                            }
                        }
                        continueCheckingUnconfirmed = false;
                    }
                    // deny
                    else if(userInput.equals("2")) {
                        this.tradeManager.setConfirm(this.currUser, trade, false);
                        this.userPresenter.unconfirmedTradeSystemResponse(1);
                        continueCheckingUnconfirmed = false;
                    }
                    // input error
                    else {
                        this.userPresenter.inputError();
                    }
                }
            }
        }
    }

    /**
     * Helper that allows a user to offer to loan one of their items to another user if their item is in that user's
     * wishlist.
     */
    private void loanPersonalItemToOtherUsers() {
        // check to see if anything exists in user's personal inventory, if not
        if(this.userManager.getUserInventory(this.currUser).size() == 0) {
            this.userPresenter.emptyPersonalInventoryWhileLoaning();
        }
        // if user's personal inventory is populated
        else {
            // get an item id and the user id from the global wishlist
            ArrayList<String> itemsToLend =
                    this.globalWishlistManager.userWhoWants(this.userManager.getUserInventory(this.currUser));
            // if another user has one of this user's items on their wishlist
            if(itemsToLend.size() != 0) {
                // check to see if user can trade, if yes loan
                try {
                    if(this.userManager.getCanTradeIgnoreBorrowsLoans(this.currUser,
                            this.tradeManager.getIncompleteTimes(this.currUser),
                            this.tradeManager.numberOfTradesCreatedThisWeek(this.currUser))) {
                        // some loan method in TradeController?
                    }
                }
                // if the user is frozen
                catch(UserFrozenException ex) {
                    this.userPresenter.userAccountFrozen();
                }
            }
            // if this user's items do not exist in another user's wishlist
            else {
                this.userPresenter.itemNotInOtherUsersWishlist();
            }
        }
    }

    /**
     * Helper...for a helper that allows a user to browse through their personal inventory.
     */
    private void browseThroughUserInventory() {
        ArrayList<Item> userInventory = this.userManager.getUserInventory(this.currUser);
        int index = 0;
        Scanner input = new Scanner(System.in);
        String userInventoryInput = "";
        while(!userInventoryInput.equals("exit")) {
            // check to see if inventory is empty or not
            if (userInventory.size() == 0) {
                this.userPresenter.isEmpty("inventory");
                break;
            }
            this.userPresenter.itemToString(userInventory.get(index).toString());
            // prompt user on what to do with this item
            this.userPresenter.userInventoryPrompts();
            userInventoryInput = input.nextLine();
            // remove the item
            if(userInventoryInput.equals("1")) {
                this.userManager.removeItemFromUserInventory(this.currUser, userInventory.get(index).getItemID());
                userInventory = this.userManager.getUserInventory(this.currUser);
                index = 0;
                this.userPresenter.itemRemoved();
            }
            // next
            else if(userInventoryInput.equals("2")) {
                if(index == userInventory.size() - 1) {
                    this.userPresenter.endOfUserInventory();
                }
                else{
                    index++;
                }
            }
            // back
            else if(userInventoryInput.equals("3")) {
                if(index == 0) {
                    this.userPresenter.endOfUserInventory();
                }
                else {
                    index--;
                }
            }
            // exit
            else if(userInventoryInput.equals("4")) {
                userInventoryInput = "exit";
            }
            // if the user puts in something weird
            else {
                this.userPresenter.inputError();
            }
        }
    }

    /**
     * Helper...for a helper...allows a user to browse through their personal wishlist I swear if wishlist gets deleted
     */
    private void browseThroughUserWishlist() {
        ArrayList<Item> userWishlist = this.userManager.getUserWishlist(this.currUser);
        int index = 0;
        Scanner input = new Scanner(System.in);
        String userWishlistInput = "";
        while(!userWishlistInput.equals("exit")) {
            // if the wishlist is empty
            if (userWishlist.size() == 0) {
                this.userPresenter.isEmpty("wishlist");
                break;
            }
            this.userPresenter.itemToString(userWishlist.get(index).toString());
            // prompt user on what to do with this item
            this.userPresenter.userWishlistPrompts();
            userWishlistInput = input.nextLine();
            // remove the item
            if(userWishlistInput.equals("1")) {
                this.userManager.removeItemFromUserWishlist(this.currUser, userWishlist.get(index).getItemID());
                userWishlist= this.userManager.getUserInventory(this.currUser);
                index = 0;
                this.userPresenter.itemRemoved();
            }
            // next
            else if(userWishlistInput.equals("2")) {
                if(index == userWishlist.size() - 1) {
                    this.userPresenter.endOfUserWishlist();
                }
                else{
                    index++;
                }
            }
            // back
            else if(userWishlistInput.equals("3")) {
                if(index == 0) {
                    this.userPresenter.endOfUserWishlist();
                }
                else {
                    index--;
                }
            }
            // send a trade request
            else if(userWishlistInput.equals("4")) {
                ArrayList<Item> traderItem = new ArrayList<>();
                traderItem.add(userWishlist.get(index));
                new TradeController(this.userManager);
                this.userPresenter.tradeRequestSent(userWishlist.get(index).getOwnerName());
            }
            // exit
            else if(userWishlistInput.equals("5")) {
                userWishlistInput = "exit";
            }
            // if the user puts in something weird
            else {
                this.userPresenter.inputError();
            }
        }
    }
}

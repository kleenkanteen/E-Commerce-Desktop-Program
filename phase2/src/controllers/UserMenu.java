package controllers;

import entities.*;
import presenters.UserPresenter;
import exceptions.UserFrozenException;
import use_cases.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class UserMenu {

    private String currUser;
    private AdminManager adminManager;
    private UserPresenter userPresenter;
    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;
    private TradeManager tradeManager;
    private MessageBuilder messageBuilder;

    /**
     * Instantiates a new UserMenu instance
     * @param currUser the String username of the currently logged in user
     * @param userManager the UserManager object
     * @param tradeManager the TradeManager object
     * @param globalInventoryManager the GlobalInventoryManager object
     * @param globalWishlistManager the GlobalWishlistManager object
     * @param adminManager the AdminManager object
     */
    public UserMenu(String currUser, UserManager userManager, TradeManager tradeManager,
                    GlobalInventoryManager globalInventoryManager, GlobalWishlistManager globalWishlistManager,
                    AdminManager adminManager) {
        this.currUser = currUser;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
        this.adminManager = adminManager;
        this.userPresenter = new UserPresenter();
        this.messageBuilder = new MessageBuilder();
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
            // will call a branching series of subclasses
            if (userInput.equals("1")) {
                BrowseThroughUserInfo browseThroughUserInfo = new BrowseThroughUserInfo(this.currUser,
                        this.userManager, this.tradeManager, this.globalInventoryManager, this.globalWishlistManager);
                browseThroughUserInfo.run();
            }
            // look at global inventory
            else if (userInput.equals("2")) {
                if(!this.globalInventoryManager.hasNoItem()) {
                    GlobalInventoryController globalInventory = new GlobalInventoryController();
                    globalInventory.run(this.globalInventoryManager, this.userManager, this.currUser,
                            this.tradeManager, this.globalWishlistManager);
                }
                else {
                    this.userPresenter.emptyGlobalInventory();
                }
            }
            // access global wishlist/lend to other users
            else if (userInput.equals("3")) {
                loanPersonalItemToOtherUsers();
            }
            // messages
            else if (userInput.equals("4")) {
                UserMessageReplySystem messageSystem = new UserMessageReplySystem(this.userManager,
                        this.globalInventoryManager, this.tradeManager, this.currUser, adminManager);
                messageSystem.run();
            }
            // create a new item for admin approval
            else if (userInput.equals("5")) {
                // prompt user for new item name/description
                this.userPresenter.createNewItemPrompt(0);
                String itemName = input.nextLine();
                this.userPresenter.createNewItemPrompt(1);
                String itemDescription = input.nextLine();
                // add this new item request to admin inbox
                List<Message> adminMessages = this.adminManager.getAdminMessages();
                adminMessages.add(this.messageBuilder.getNewItemRequest("User " + this.currUser +
                        " has created a new item that requires approval.",
                        new Item(itemName, this.currUser, itemDescription)));
                this.adminManager.setAdminMessages(adminMessages);
                this.userPresenter.newItemMessageSentToAdmin();
            }
            // send admin an unfreeze request message
            else if (userInput.equals("6")) {
                if(this.userManager.getUserFrozenStatus(this.currUser)) {
                    List<Message> adminMessages = this.adminManager.getAdminMessages();
                    adminMessages.add(this.messageBuilder.getUnfreezeRequest("User " + this.currUser +
                            " has requested to be unfrozen.", this.currUser));
                    this.adminManager.setAdminMessages(adminMessages);
                    this.userPresenter.unfreezeRequestSent();
                }
                else {
                    this.userPresenter.userNotFrozen();
                }
            }
            // send a private message to a user
            else if (userInput.equals("7")) {
                // prompt user for the recipient user name
                boolean continueToRun = true;
                String user = "";
                while(continueToRun) {
                    this.userPresenter.userMessagePrompt();
                    user = input.nextLine();
                    // if the username is invalid
                    if(!this.userManager.isValidUser(user)) {
                        this.userPresenter.invalidUsername();
                    }
                    // else move on
                    else {
                        continueToRun = false;
                    }
                }
                // prompt user for message content
                this.userPresenter.userMessagePromptSecundus();
                String message = input.nextLine();
                // add to recipient inbox
                this.userManager.addUserMessage(user, this.messageBuilder.getContentMessage(message, this.currUser));
            }
            // exit
            else if(userInput.equals("8")) {
                userInput = "exit";
            }
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
                    this.userManager.getUserThreshold(this.currUser)) {
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
                List<Message> adminMessages = this.adminManager.getAdminMessages();
                adminMessages.add(this.messageBuilder.getFreezeRequest("User " + this.currUser +
                        " should have their account frozen.", this.currUser));
                this.adminManager.setAdminMessages(adminMessages);
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
        List<Trade> incompletes = this.tradeManager.tradesToConfirm(this.currUser);
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
                // loop through user menu for this particular incomplete trade
                while(continueCheckingUnconfirmed) {
                    this.userPresenter.checkUnconfirmedTradesPrompts();
                    userInput = input.nextLine();
                    // confirm meeting
                    if(userInput.equals("1")) {
                        this.tradeManager.setConfirm(this.currUser, trade, true);
                        this.userPresenter.unconfirmedTradeSystemResponse(0);
                        // if the trade is a permanent trade
                        if(trade instanceof PermTrade) {
                            // remove all traderA items from Global and Personal wishlists if not empty
                            if(trade.getTraderAItemsToTrade().size() != 0) {
                                for(Item item : trade.getTraderAItemsToTrade()) {
                                    // check to make sure that this item exists on the global wishlist
                                    if(this.globalWishlistManager.isItemWanted(item.getItemID())) {
                                        this.globalWishlistManager.removeItem(item.getItemID());
                                    }
                                }
                            }
                            // remove all tradeB items from Global and Personal wishlists if not empty
                            if(trade.getTraderBItemsToTrade().size() != 0) {
                                for (Item item : trade.getTraderBItemsToTrade()) {
                                    // check to make sure item exists in global wishlist
                                    if(this.globalWishlistManager.isItemWanted(item.getItemID())) {
                                        this.globalWishlistManager.removeItem(item.getItemID());
                                    }
                                }
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
        List<Item> userInventory = this.globalInventoryManager.getPersonInventory(this.currUser);
        if(userInventory.size() == 0) {
            this.userPresenter.emptyPersonalInventoryWhileLoaning();
        }
        // if user's personal inventory is populated
        else {
            // get an item id and the user id from the global wishlist
            List<String> itemsToLend = this.globalWishlistManager.userWhoWants(userInventory);
            // if another user has one of this user's items on their wishlist
            if(itemsToLend.size() != 0) {
                // check to see if user can trade, if yes loan
                try {
                    // check user status
                    if(this.userManager.getCanTradeIgnoreBorrowsLoans(this.currUser,
                            this.tradeManager.getIncompleteTimes(this.currUser),
                            this.tradeManager.numberOfTradesCreatedThisWeek(this.currUser))) {
                        // find the selected item from userInventory
                        List<Item> userItem = new ArrayList<>();
                        for(Item item : userInventory) {
                            if (item.getItemID().equals(itemsToLend.get(0))) {
                                userItem.add(item);
                                break;
                            }
                        }
                        // give user choice whether to continue with trade offer or return to main UserMenu
                        Scanner input = new Scanner(System.in);
                        boolean continueLoanInput = true;
                        while(continueLoanInput) {
                            this.userPresenter.loanToOtherUserPrompt(userItem.get(0).getName(),
                                    userItem.get(0).getOwnerName());
                            int userLoanInput = input.nextInt();
                            // yes, continue with the trade offer
                            if(userLoanInput == 1) {
                                TradeController tradeController =
                                        new TradeController(this.globalInventoryManager, this.globalWishlistManager);
                                TradeRequest newTradeRequest =
                                        tradeController.runFromLoan(userItem, this.currUser, itemsToLend.get(1));
                                this.userManager.addUserMessage(userItem.get(0).getOwnerName(), newTradeRequest);
                                continueLoanInput = false;
                            }
                            // if no, exit and return to main menu
                            else if(userLoanInput == 2) {
                                this.userPresenter.returnToMainMenu();
                                continueLoanInput = false;
                            }
                            // if input error
                            else {
                                this.userPresenter.inputError();
                            }
                        }
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
}

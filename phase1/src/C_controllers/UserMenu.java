package C_controllers;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import F_entities.*;
import E_use_cases.*;
import D_presenters.UserPresenter;


public class UserMenu {
    private String currUser;
    private HashMap<String, User> allUsers;
    private GlobalInventory globalInventory;
    private GlobalWishlist globalWishlist;
    private HashMap<String, ArrayList<Trade>> userTrades;
    private ArrayList<Message> adminMessages;
    private UserPresenter userPresenter;

    /**
     * Create a new UserMenu object, the primary controller that the user would interact with.
     * @param currUser the username of the currently logged in User
     * @param allUser the hashmap of all user objects
     * @param userTrades the hashmap of all user trades
     * @param globalInventory the entity storing all items on the system inventory
     * @param globalWishlist the entity storing the global wishlist
     * @param adminMessages the arraylist of all admin messages
     */
    public UserMenu(String currUser, HashMap<String, User> allUser, HashMap<String, ArrayList<Trade>> userTrades,
                    GlobalInventory globalInventory, GlobalWishlist globalWishlist, ArrayList<Message> adminMessages) {
        this.currUser = currUser;
        this.allUsers = allUser;
        this.userTrades = userTrades;
        this.globalInventory = globalInventory;
        this.globalWishlist = globalWishlist;
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
        UserManager userManager = new UserManager(this.allUsers);
        GlobalInventoryManager globalInventoryManager = new GlobalInventoryManager(this.globalInventory);
        GlobalWishlistManager globalWishlistManager = new GlobalWishlistManager(this.globalWishlist);
        TradeManager tradeManager = new TradeManager(this.userTrades);

        // check to see if user can trade
        checkUserStatus(userManager, tradeManager);
        // check for all incomplete trades to confirm
        confirmIncompleteUserTrades(tradeManager);

        while(!userInput.equals("exit")) {
            this.userPresenter.promptUserMenu();
            userInput = input.nextLine();
            // look at/change user information
            if (userInput.equals("1")) {
                browseThroughUserInfo(userManager, tradeManager);
            }
            // look at global inventory
            else if (userInput.equals("2")) {
                GlobalInventoryController globalInventory = new GlobalInventoryController();
                globalInventory.run(globalInventoryManager, userManager, this.currUser, tradeManager);
            }
            // global wishlist
            else if (userInput.equals("3")) {
                // separate menu for GlobalWishlist?
            }
            // messages
            else if (userInput.equals("4")) {
                UserMessageReplySystem messageSystem = new UserMessageReplySystem(userManager,
                        globalInventoryManager, tradeManager, this.currUser);
                messageSystem.run();
            }
            // create a new item for admin approval
            else if (userInput.equals("5")) {
                this.userPresenter.createNewItemPrompt(0);
                String itemName = input.nextLine();
                this.userPresenter.createNewItemPrompt(1);
                String itemDescription = input.nextLine();
                this.adminMessages.add(userManager.createNewItem(this.currUser, itemName, itemDescription));
                this.userPresenter.newItemMessageSentToAdmin();
            }
            // exit
            else if (userInput.equals("6")) {
                userInput = "exit";
            }
            else {
                this.userPresenter.inputError();
            }
        }
    }

    /**
     * Helper method for run() that allows a user to access their personal information
     * @param userManager the UserManager object that the info will be accessed from
     * @param tradeManager the TradeManager object
     */
    private void browseThroughUserInfo(UserManager userManager, TradeManager tradeManager) {
        Scanner input = new Scanner(System.in);
        String userInput = "";
        while(!userInput.equals("exit")) {
            this.userPresenter.userMenuUserInfoPrompts();
            userInput = input.nextLine();
            // change password
            if (userInput.equals("1")) {
                this.userPresenter.setNewPasswordPrompt();
                String newPass = input.nextLine();
                userManager.changePassword(this.currUser, newPass);
            }
            // view frequent trading partners
            else if (userInput.equals("2")) {
                String[] tradingPartners = tradeManager.getFrequentTradingPartners(this.currUser);
                if (tradingPartners.length > 0) {
                    this.userPresenter.printUserTradePartners(tradingPartners);
                }
                else {
                    this.userPresenter.noTradingPartners();
                }
            }
            // view 3 most recent trades
            else if (userInput.equals("3")) {
                Trade[] recentTradeHistory = tradeManager.getRecentCompletedTrade(this.currUser);
                for(Trade trade : recentTradeHistory) {
                    System.out.println(trade.toString() + "\n");
                }
            }
            // look at personal inventory
            else if (userInput.equals("4")) {
                browseThroughUserInventory(userManager);
            }
            // look at personal wishlist
            else if (userInput.equals("5")) {
                browseThroughUserWishlist(userManager, tradeManager);
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
     * @param userManager the UserManager object
     * @param tradeManager the TradeManager object
     */
    private void checkUserStatus(UserManager userManager, TradeManager tradeManager) {
        boolean tooManyIncomplete = false;
        boolean tooManyBorrowVLoan = false;
        if (!userManager.getUserFrozenStatus(this.currUser)) {
            // check num of incomplete trades
            if(tradeManager.tradesToConfirm(this.currUser).size() >=
                    this.allUsers.get(this.currUser).getLimitOfIncompleteTrade()) {
                this.userPresenter.tooManyIncompleteTrades();
                tooManyIncomplete = true;
            }
            // check num of borrows v. loans
            if((tradeManager.getBorrowedTimes(this.currUser) - tradeManager.getLendTimes(this.currUser)) >
                    userManager.getUserBorrowsVLoans(this.currUser)) {
                this.userPresenter.tooManyBorrowsVLoans(tradeManager.getBorrowedTimes(this.currUser) -
                        tradeManager.getLendTimes(this.currUser));
                tooManyBorrowVLoan = true;
            }
            // check num of trades
            if(tradeManager.numberOfTradesCreatedThisWeek(this.currUser) >=
                    userManager.getTradesPerWeekForUser(this.currUser)) {
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
     * @param tradeManager the TradeManager object
     */
    private void confirmIncompleteUserTrades(TradeManager tradeManager) {
        Scanner input = new Scanner(System.in);
        // check to make sure that the user has unconfirmed trades
        if(tradeManager.tradesToConfirm(this.currUser).size() != 0) {
            // instantiate unconfirmed trades
            this.userPresenter.promptUserToConfirmTrades();
            ArrayList<Trade> incompletes = tradeManager.tradesToConfirm(this.currUser);
            String userInput;
            boolean continueCheckingUnconfirmed;
            // go through all unconfirmed trades
            for(Trade trade : incompletes) {
                System.out.println(trade.toString() + "\n");
                continueCheckingUnconfirmed = true;
                while(continueCheckingUnconfirmed) {
                    System.out.println("Can you confirm that this meeting happened? " +
                            "\n[1] The meeting happened " +
                            "\n[2] The meeting did not happen.");
                    userInput = input.nextLine();
                    // confirm meeting
                    if(userInput.equals("1")) {
                        tradeManager.setConfirm(this.currUser, trade, true);
                        System.out.println("Trade confirmed.");
                        continueCheckingUnconfirmed = false;
                    }
                    // deny
                    else if(userInput.equals("2")) {
                        tradeManager.setConfirm(this.currUser, trade, false);
                        System.out.println("Trade marked as failed.");
                        continueCheckingUnconfirmed = false;
                    }
                    else {
                        this.userPresenter.inputError();
                    }
                }
            }
        }
    }

    /**
     * Helper...for a helper that allows a user to browse through their personal inventory.
     * @param userManager the UserManager object
     */
    private void browseThroughUserInventory(UserManager userManager) {
        ArrayList<Item> userInventory = userManager.getUserInventory(this.currUser);
        int index = 0;
        Scanner input = new Scanner(System.in);
        String userInventoryInput = "";
        while(!userInventoryInput.equals("exit")) {
            // check to see if inventory is empty or not
            if (userInventory.size() == 0) {
                this.userPresenter.isEmpty("inventory");
                break;
            }
            System.out.println(userInventory.get(index).toString() + "\n");
            // prompt user on what to do with this item
            this.userPresenter.userInventoryPrompts();
            userInventoryInput = input.nextLine();
            // remove the item
            if(userInventoryInput.equals("1")) {
                userManager.removeItemFromUserInventory(this.currUser, userInventory.get(index).getItemID());
                userInventory = userManager.getUserInventory(this.currUser);
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
     * @param userManager the UserManager object
     */
    private void browseThroughUserWishlist(UserManager userManager, TradeManager tradeManager) {
        ArrayList<Item> userWishlist = userManager.getUserWishlist(this.currUser);
        int index = 0;
        Scanner input = new Scanner(System.in);
        String userWishlistInput = "";
        while(!userWishlistInput.equals("exit")) {
            // if the wishlist is empty
            if (userWishlist.size() == 0) {
                this.userPresenter.isEmpty("wishlist");
                break;
            }
            System.out.println(userWishlist.get(index).toString() + "\n");
            // prompt user on what to do with this item
            this.userPresenter.userWishlistPrompts();
            userWishlistInput = input.nextLine();
            // remove the item
            if(userWishlistInput.equals("1")) {
                userManager.removeItemFromUserWishlist(this.currUser, userWishlist.get(index).getItemID());
                userWishlist= userManager.getUserInventory(this.currUser);
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
                TradeController tradeController = new TradeController(userManager, tradeManager, this.currUser);
                TradeRequestMessage tradeRequest =
                        tradeController.run(traderItem, userWishlist.get(index).getOwnerName());
                userManager.addUserMessage(this.currUser, tradeRequest);
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

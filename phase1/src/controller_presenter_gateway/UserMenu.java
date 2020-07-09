package controller_presenter_gateway;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import entities.*;
import exceptions.*;
import uses_cases.*;


public class UserMenu {
    private String currUser;
    private HashMap<String, User> allUsers;
    private GlobalInventory globalInventory;
    private GlobalWishlist globalWishlist;
    private HashMap<String, ArrayList<Trade>> userTrades;
    private ArrayList<Message> adminMessages;

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

        // check to see if user can trade/is frozen
        checkUserStatus(userManager, tradeManager);
        // check for all incomplete trades to confirm
        confirmIncompleteUserTrades(userManager, tradeManager);

        // any additional checks needed as soon as the user successfully logs in?

        while(!userInput.equals("exit")) {
            System.out.println("Enter in blah blah blah idk I'll work on this later");
            System.out.println("\n" + "Enter 'exit' to exit.");
            userInput = input.nextLine();
            // look at/change user information
            if (userInput.equals("1")) {
                browseThroughUserInfo(userManager, tradeManager);
            }
            // look at global inventory
            else if (userInput.equals("2")) {
                GlobalInventoryController globalInventory = new GlobalInventoryController();
                globalInventory.run(globalInventoryManager, userManager, this.currUser);
            }
            // global wishlist
            else if (userInput.equals("3")) {
                // separate menu for GlobalWishlist?
            }
            // look at personal inventory/personal wishlist
            else if (userInput.equals("4")) {
                // ?
            }
            // messages
            else if (userInput.equals("5")) {
                UserMessageReplySystem messageSystem = new UserMessageReplySystem(userManager, globalInventoryManager,
                        globalWishlistManager, tradeManager, this.currUser);
                messageSystem.run();
            }
            // create a new item for admin approval
            else if (userInput.equals("6")) {
                System.out.println("Enter in the item's name");
                String itemName = input.nextLine();
                System.out.println("Enter the item's description");
                String itemDescription = input.nextLine();
                this.adminMessages.add(userManager.createNewItem(this.currUser, itemName, itemDescription));
            }
            else {
                System.out.println("Input not understood, please try again.");
            }
        }
    }

    /**
     * Helper method for run() that allows a user to access their personal information
     * @param userManager the UserManager object that the info will be accessed from
     */
    private void browseThroughUserInfo(UserManager userManager, TradeManager tradeManager) {
        Scanner input = new Scanner(System.in);
        String userInput = "";
        while(!userInput.equals("exit")) {
            System.out.println("blah blah blah");
            userInput = input.nextLine();
            // change password
            if (userInput.equals("1")) {
                System.out.println("Enter in your new password");
                String newPass = input.nextLine();
                userManager.changePassword(this.currUser, newPass);
            }
            // view frequent trading partners
            else if (userInput.equals("2")) {
                System.out.println(tradeManager.getFrequentTradingPartners(this.currUser));
            }
            //
            else if(userInput.equals("3")) {

            }
        }
    }

    /**
     * The helper method for run() that checks on a user's trade status
     * @param userManager the UserManager object
     * @param tradeManager the TradeManager object
     */
    private void checkUserStatus(UserManager userManager, TradeManager tradeManager) {
        if(tradeManager.getTempTradeHistory(this.currUser).size() >=
                this.allUsers.get(this.currUser).getLimitOfIncompleteTrade()) {
            try {
                if(!userManager.getCanTrade(this.currUser,
                        tradeManager.getBorrowedTimes(this.currUser), tradeManager.getLendTimes(this.currUser))) {
                    System.out.println("You have too many incomplete trades. " + "\n" +
                            "A request for your account to be frozen has been sent.");
                    FreezeRequestMessage newFreezeRequest = new FreezeRequestMessage("User " + this.currUser +
                            " has too many incomplete trades and their account should be frozen", this.currUser);
                    this.adminMessages.add(newFreezeRequest);
                }
            }
            catch (UserFrozenException ex) {
                System.out.println("Your account has been frozen, and you are unable to trade.");
            }
        }
    }

    /**
     * Helper for run() that allows a user to confirm of their incomplete trades.
     * @param userManager the UserManager object
     * @param tradeManager the TradeManager object
     */
    private void confirmIncompleteUserTrades(UserManager userManager, TradeManager tradeManager) {
        Scanner input = new Scanner(System.in);
        System.out.println("Here are all of your unconfirmed trades. Please confirm any that are complete.");
    }
}

package controllers;

import entities.Item;
import exceptions.UserFrozenException;
import presenters.UserPresenter;
import use_cases.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BrowseThroughUserWishlist {

    private String currUser;
    private UserPresenter userPresenter;
    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;
    private TradeManager tradeManager;

    /**
     * Constructs a new BrowseThroughUserWishlist object
     * @param currUser String currUser
     * @param userManager UserManager object
     * @param tradeManager TradeManager object
     * @param globalInventoryManager GlobalInventoryManager object
     * @param globalWishlistManager GlobalWishlistManager object
     */
    public BrowseThroughUserWishlist(String currUser, UserManager userManager, TradeManager tradeManager,
                                     GlobalInventoryManager globalInventoryManager,
                                     GlobalWishlistManager globalWishlistManager) {
        this.currUser = currUser;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
        this.userPresenter = new UserPresenter();
    }

    /**
     * Runs the BrowseThroughUserWishlist object
     */
    public void run() {
        // get list of itemids in this person's wishlist
        List<String> userWishlistIDs = this.globalWishlistManager.getPersonWishlist(this.currUser);
        // if there are no items in the user's wishlist
        if(userWishlistIDs.size() == 0) {
            this.userPresenter.isEmpty("wishlist");
            return;
        }
        // get the list of items using the itemIDs
        List<Item> userWishlist = new ArrayList<>();
        for(String itemID : userWishlistIDs) {
            userWishlist.add(this.globalInventoryManager.getItemFromGI(itemID));
        }
        // begin running through this user's list of items in their wishlist
        int index = 0;
        Scanner input = new Scanner(System.in);
        String userWishlistInput = "";
        while(!userWishlistInput.equals("exit")) {
            // if the wishlist is empty
            // this is a second check to check to see if the user has removed all items in their wishlist while running
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
                this.globalWishlistManager.removeWish(userWishlist.get(index).getItemID(), this.currUser);
                userWishlist.remove(userWishlist.get(index));
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
                try {
                    // make sure the item isn't the user's own item and that they can trade
                    if(!userWishlist.get(index).getOwnerName().equals(this.currUser) &&
                            this.userManager.getCanTrade(this.currUser,
                                    this.tradeManager.getBorrowedTimes(this.currUser),
                                    this.tradeManager.getLendTimes(this.currUser),
                                    this.tradeManager.getIncompleteTimes(this.currUser),
                                    this.tradeManager.numberOfTradesCreatedThisWeek(this.currUser))) {
                        List<Item> traderItem = new ArrayList<>();
                        traderItem.add(userWishlist.get(index));
                        TradeController tradeController =
                                new TradeController(this.globalInventoryManager, this.globalWishlistManager);
                        tradeController.run(traderItem, this.currUser,
                                this.tradeManager.getTradeHistory(this.currUser).size());
                        this.userPresenter.tradeRequestSent(userWishlist.get(index).getOwnerName());
                    }
                }
                // if frozen
                catch (UserFrozenException ex) {
                    this.userPresenter.userAccountFrozen();
                }
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

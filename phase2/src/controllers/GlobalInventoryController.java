package controllers;

import presenters.GlobalInventoryPresenter;
import use_cases.GlobalInventoryManager;
import use_cases.GlobalWishlistManager;
import use_cases.TradeManager;
import use_cases.UserManager;
import entities.Item;
import exceptions.UserFrozenException;

import java.util.ArrayList;
import java.util.Scanner;

public class GlobalInventoryController {
    /**
     * runs the global inventory browsing menu
     * @param gim the GlobalInventoryManager object
     * @param UM the UserManager object
     * @param user the username for current user
     * @param TM the TradeManager object
     * @param GW the GlobalWishlistManager object
     */
    public void run(GlobalInventoryManager gim, UserManager UM, String user, TradeManager TM,
                    GlobalWishlistManager GW) {

        Scanner inputx = new Scanner(System.in);
        GlobalInventoryPresenter prompts = new GlobalInventoryPresenter(gim);


        int pageNumber = 1;
        Item item;
        prompts.printpage(pageNumber);

        String input = inputx.nextLine();
        while (!input.equals("e")) { // != compares memory addresses.
            if (input.equals("n")) {
                if (pageNumber < gim.generatePageNumber()) {
                    pageNumber += 1;
                } else
                    prompts.emptyPage();

            }
            if (input.equals("p")) {
                if (pageNumber == 1) {
                    prompts.atfirst();
                } else {
                    pageNumber -= 1;
                }
            }
            if (input.matches("[0-9]") && Integer.valueOf(input) <= gim.generatePage(pageNumber).size() - 1) {
                item = gim.generatePage(pageNumber).get(Integer.parseInt(input)); //user have selected one item
                if (!item.getOwnerName().equals(user)) {
                    try {
                        if (UM.getCanTrade(user, TM.getBorrowedTimes(user), TM.getLendTimes(user),
                                TM.getIncompleteTimes(user), TM.numberOfTradesCreatedThisWeek(user))) {
                            prompts.addToWishlishandTradeRequest(item);
                            String selection = inputx.nextLine();

                            if (selection.equals("1")) { // adding to wishlist
                                if (GW.getPersonWishlist(user).contains(item)) { // if user already has it in wishlist
                                    prompts.alreadyHave();
                                } else {
                                    GW.addWish(item.getItemID(), user); // user does not have it in wishlit, adding it
                                    prompts.addedToWishlist(item);
                                    GW.addWish(item.getItemID(), user);
                                }
                            } else if (selection.equals("2")) { // user selected trade,
                                controllers.TradeController trademenu = new TradeController(gim);
                                ArrayList<Item> items = new ArrayList<>();
                                items.add(item);
                                trademenu.run(items, user, TM.getTradeHistory(user).size());
                            }
                        } else { // if user cant trade, only allow to add to wishlist
                            prompts.addToWishlist(item);
                            String selection1 = inputx.nextLine();
                            if (selection1.equals("1")) {
                                if (!item.getOwnerName().equals(user)) {
                                    GW.addWish(item.getItemID(), user);
                                    prompts.addedToWishlist(item);
                                    GW.addWish(item.getItemID(), user);
                                } else prompts.ownItem();
                            }
                        }
                    } catch (UserFrozenException ex) {
                        prompts.FrozenAcc();
                    }
                    if (input.matches("[0-9]") &&
                            Integer.valueOf(input) > gim.generatePage(pageNumber).size() - 1) {
                        prompts.invalid();
                    }
                }
                else prompts.ownItem();
            }
            prompts.printpage(pageNumber);
            input = inputx.nextLine();
        }

    }
}

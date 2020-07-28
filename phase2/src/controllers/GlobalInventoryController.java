package controllers;

import entities.Item;
import entities.TradeRequest;
import exceptions.IncompleteTradeException;
import exceptions.UserFrozenException;
import presenters.GlobalInventoryPresenter;
import use_cases.GlobalInventoryManager;
import use_cases.GlobalWishlistManager;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public class GlobalInventoryController {

    /**
     * runs the global inventory browsing menu
     * @param globalInventoryManager the GlobalInventoryManager object
     * @param userManager the UserManager object
     * @param user the username for current user
     * @param tradeManager the TradeManager object
     * @param globalWishlistManager the GlobalWishlistManager object
     */
    public void run(GlobalInventoryManager globalInventoryManager, UserManager userManager, String user,
                    TradeManager tradeManager, GlobalWishlistManager globalWishlistManager) {

        Scanner inputx = new Scanner(System.in);
        GlobalInventoryPresenter prompts = new GlobalInventoryPresenter(globalInventoryManager);


        int pageNumber = 1;
        Item item;
        prompts.printpage(pageNumber);

        String input = inputx.nextLine();
        while (!input.equals("e")) { // != compares memory addresses.
            if (input.equals("n")) {
                if (pageNumber < globalInventoryManager.generatePageNumber()) {
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
            if (input.matches("[0-9]") &&
                    Integer.valueOf(input) <= globalInventoryManager.generatePage(pageNumber).size() - 1) {
                item = globalInventoryManager.generatePage(pageNumber).get(Integer.parseInt(input)); //user have selected one item
                if (!item.getOwnerName().equals(user)) {
                    try {
                        if (userManager.getCanTrade(user, tradeManager.getBorrowedTimes(user),
                                tradeManager.getLendTimes(user), tradeManager.getIncompleteTimes(user),
                                tradeManager.numberOfTradesCreatedThisWeek(user))) {
                            prompts.addToWishlishandTradeRequest(item);
                            String selection = inputx.nextLine();

                            if (selection.equals("1")) { // adding to wishlist
                                if (globalWishlistManager.getPersonWishlist(user).contains(item)) { // if user already has it in wishlist
                                    prompts.alreadyHave();
                                } else {
                                    globalWishlistManager.addWish(item.getItemID(), user); // user does not have it in wishlit, adding it
                                    prompts.addedToWishlist(item);
                                    globalWishlistManager.addWish(item.getItemID(), user);
                                }
                            } else if (selection.equals("2")) { // user selected trade,
                                try{
                                ArrayList<Item> items = new ArrayList<>();
                                items.add(item);
                                prompts.seeTraderInventory();
                                String seeTraderInventorySelection = inputx.nextLine();
                                if (seeTraderInventorySelection.equals("2")) {// just one item trade
                                    controllers.TradeController trademenu = new TradeController(globalInventoryManager,
                                            globalWishlistManager);
                                    TradeRequest request = trademenu.run
                                            (items, user, tradeManager.getTradeHistory(user).size());
                                    userManager.addUserMessage(item.getOwnerName(), request);
                                } else {
                                    prompts.traderItem(item);// prints owner inventory
                                    String inputitemselect = "";
                                    inputitemselect = inputx.nextLine();
                                    while (!inputitemselect.equals("e")) {
                                        if (inputitemselect.matches("[0-9]*") &&
                                                Integer.valueOf(inputitemselect) < globalInventoryManager.
                                                        getPersonInventory(item.getOwnerName()).size()) {
                                            if (items.contains(globalInventoryManager.getPersonInventory
                                                    (item.getOwnerName()).get(Integer.valueOf(inputitemselect)))) {
                                                prompts.alreadySelected();
                                            } else {
                                                items.add(globalInventoryManager.getPersonInventory(item.getOwnerName())
                                                        .get(Integer.valueOf(inputitemselect)));
                                            }
                                        }
                                        else{
                                            prompts.invalid();
                                            prompts.traderItem(item);// prints owner inventory
                                            inputitemselect = inputx.nextLine();
                                        }
                                    }
                                    controllers.TradeController trademenu =
                                            new TradeController(globalInventoryManager, globalWishlistManager);
                                    TradeRequest request = trademenu.run(items, user, tradeManager.getTradeHistory(user).size());
                                    userManager.addUserMessage(item.getOwnerName(), request);
                                }
                                } catch (IncompleteTradeException e){
                                    prompts.incompleteTrade();
                                }
                            } else { // if user cant trade, only allow to add to wishlist
                                prompts.addToWishlist(item);
                                String selection1 = inputx.nextLine();
                                if (selection1.equals("1")) {
                                    if (!item.getOwnerName().equals(user)) {
                                        globalWishlistManager.addWish(item.getItemID(), user);
                                        prompts.addedToWishlist(item);
                                        globalWishlistManager.addWish(item.getItemID(), user);
                                    } else prompts.ownItem();
                                }
                            }
                        }
                    } catch (UserFrozenException ex) {
                        prompts.FrozenAcc();
                    }
                    if (input.matches("[0-9]") &&
                            Integer.valueOf(input) > globalInventoryManager.generatePage(pageNumber).size() - 1) {
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

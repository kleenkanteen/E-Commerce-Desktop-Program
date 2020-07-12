package C_controllers;

import D_presenters.GlobalInventoryPresenter;
import E_use_cases.GlobalInventoryManager;
import E_use_cases.TradeManager;
import E_use_cases.UserManager;
import F_entities.Item;
import G_exceptions.UserFrozenException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GlobalInventoryController {
    public void run(GlobalInventoryManager gim, UserManager UM, String user, TradeManager TM) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        GlobalInventoryPresenter prompts = new GlobalInventoryPresenter(gim);


        int pageNumber = 1;
        Item item;
        prompts.printpage(pageNumber);

        String input = br.readLine();
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
                try {
                    if (UM.getCanTrade(user, TM.getBorrowedTimes(user), TM.getLendTimes(user),// check if user can trade
                            TM.getIncompleteTimes(user), TM.numberOfTradesCreatedThisWeek(user))) {
                        prompts.addToWishlishandTradeRequest(item);
                        input = br.readLine();
                        if (input.equals("1")) { // adding to wishlist
                            if (UM.getUserWishlist(user).contains(item)) { // if user already has it in wishlist
                                prompts.alreadyHave();
                            } else {
                                UM.addItemToWishlist(user, item); // user does not have it in wishlit, adding it to wishlist
                            }
                        if (input.equals("2")) { // user selected trade,
                            TradeController trademenu = new TradeController(UM);
                            ArrayList<Item> items = new ArrayList<>();
                            items.add(item);
                            trademenu.run(items, user);
                        }
                    } else { // if user cant trade, only allow to add to wishlist
                        prompts.addToWishlist(item);
                        input = br.readLine();
                        if (input.equals("1")) {
                            if (!item.getOwnerName().equals(user)) {
                                UM.addItemToWishlist(user, item);
                                prompts.addedToWishlist(item);
                            } else prompts.ownItem();
                        }
                    }
                    }
                }
                catch (UserFrozenException ex){
                    prompts.FrozenAcc();
                }
                if (input.matches("[0-9]") && Integer.valueOf(input) > gim.generatePage(pageNumber).size() - 1) {
                    prompts.invalid();
                }
                prompts.printpage(pageNumber);
                input = br.readLine();
            }
        }

    }
}

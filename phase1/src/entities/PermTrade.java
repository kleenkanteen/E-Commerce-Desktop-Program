package entities;// Written by Thanusun

import java.util.ArrayList;
import java.util.Calendar;

public class PermTrade extends Trade {

    private boolean borrowed, lent = false;


    protected void addItems(User trader, ArrayList<Item> itemsToTrade) {
        // for every item in the users' items to trade.
        for (Item tradeItem : itemsToTrade) {
            // add it to their inventory.
            trader.getPersonalInventory().add(tradeItem);
            tradeItem.setOwnerName(trader.getUsername());
        }
        itemsToTrade.clear();
    }

    /**
     * Creates a trade with an item that both the seller wants to sell
     * and the buyer wants to buy.
     * The buyer creates the trade with the item that they want to sell.
     *
     * NOTE: <i>userAItemsToTrade</i> and <i>userBItemsToTrade</i> must not be empty at the same time.
     * It will lead to very bad things.
     * @param traderA takes in a entities.User that wants to create the entities.Trade.
     * @param traderB takes in a entities.User that wants to borrow the item based on the trade.
     * @param userAItemsToTrade takes in items that want to be traded to userB.
     * @param userBItemsToTrade takes in items that want to be traded to userA.
     * @param date is a string that follows a specific date format.
     */
    public PermTrade(User traderA, User traderB, ArrayList<Item> userAItemsToTrade, ArrayList<Item> userBItemsToTrade, Calendar date) {
        super(traderA, traderB, userAItemsToTrade, userBItemsToTrade, date);

        // two-way trade.
        if (!(userAItemsToTrade.isEmpty() && userBItemsToTrade.isEmpty())) {
            // for every item add it into personalInventory. (traderA)
            addItems(traderB, userAItemsToTrade);
            // traderB items'
            addItems(traderA, userBItemsToTrade);
        // one-way trade.
        } else {
            if (userAItemsToTrade.isEmpty()) {
                addItems(traderA, userBItemsToTrade);

            } else {
                addItems(traderB, userBItemsToTrade);
            }
        }
    }
}

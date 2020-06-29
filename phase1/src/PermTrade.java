// Written by Thanusun

import java.util.ArrayList;

public class PermTrade extends Trade {

    private void addItems(User trader, ArrayList<Item> itemsToTrade) {
        for (Item tradeItem : itemsToTrade) {
            trader.getPersonalInventory().add(tradeItem);
            tradeItem.setOwnerName(trader.getUsername());
        }
    }

    /**
     * Creates a trade with an item that both the seller wants to sell
     * and the buyer wants to buy.
     * The buyer creates the trade with the item that they want to sell.
     *
     * NOTE: <i>userAItemsToTrade</i> and <i>userBItemsToTrade</i> must not be empty at the same time.
     * It will lead to very bad things.
     * @param traderA     takes in a User that wants to create the Trade.
     * @param traderB     takes in a User that wants to borrow the item based on the trade.
     * @param userAItemsToTrade takes in items that want to be traded to userB.
     * @param userBItemsToTrade takes in items that want to be traded to userA.
     * @param date        is a string that follows a specific date format.
     * @param time        is a string that follows a time.
     */
    public PermTrade(User traderA, User traderB, ArrayList<Item> userAItemsToTrade, ArrayList<Item> userBItemsToTrade, String date, String time) {
        super(traderA, traderB, userAItemsToTrade, userBItemsToTrade, date, time);

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

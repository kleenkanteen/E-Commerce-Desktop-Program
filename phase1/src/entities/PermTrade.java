package entities;// Written by Thanusun

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PermTrade extends Trade {

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
     * @param date is a LocalDateTime that follows a specific date format.
     */
    public PermTrade(String traderA, String traderB, ArrayList<Item> userAItemsToTrade, ArrayList<Item> userBItemsToTrade, LocalDateTime date) {
        super(traderA, traderB, userAItemsToTrade, userBItemsToTrade, date);
    }
}

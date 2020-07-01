package entities;

import exceptions.TradeNotProcessedException;

import java.util.ArrayList;
import java.util.Calendar;

public class TempTrade extends PermTrade {

    private int startDate;
    private Calendar returnDate;
    private boolean trade_active = false; // will help us determining whether the transaction is still active.

    /**
     * Creates a trade with an item that both the seller wants to sell
     * and the buyer wants to buy.
     * The buyer creates the trade with the item that they want to sell.
     *
     * @param traderA           takes in a entities.User that wants to create the entities.Trade.
     * @param traderB           takes in a entities.User that wants to borrow the item based on the trade.
     * @param userAItemsToTrade takes in items that want to be traded to userB.
     * @param userBItemsToTrade takes in items that want to be traded to userA.
     * @param date              is a string that follows a specific date format.
     */
    public TempTrade(User traderA, User traderB, ArrayList<Item> userAItemsToTrade, ArrayList<Item> userBItemsToTrade, Calendar date) {
        super(traderA, traderB, userAItemsToTrade, userBItemsToTrade, date);
        trade_active = true;
        // two-way trade.
        if (!(userAItemsToTrade.isEmpty() && userBItemsToTrade.isEmpty())) {
            // for every item add it into personalInventory. (traderA)
            addItems(traderB, userAItemsToTrade);
            // traderB items'
            addItems(traderA, userBItemsToTrade);
        } else {
            if (userAItemsToTrade.isEmpty()) {
                addItems(traderA, userBItemsToTrade);
            } else {
                addItems(traderB, userBItemsToTrade);
            }
        }
        // dates for temporary trading.
        startDate = date.get(Calendar.DAY_OF_MONTH);
        returnDate.add(startDate, 30);
    }
    /**
     * This method provides you with the number of days left after a trade has been processed.
     * @return an integer that indicates the number of days left in the trade.
     * @throws TradeNotProcessedException only when trade is not processed/not active.
     */
    public int daysLeft() throws TradeNotProcessedException {
        if (!trade_active) {
            throw new TradeNotProcessedException();
        }
        int finishDate = returnDate.get(Calendar.DAY_OF_MONTH);
        return finishDate - startDate;
    }
}

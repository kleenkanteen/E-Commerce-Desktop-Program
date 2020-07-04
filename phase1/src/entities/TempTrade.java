package entities;

import java.util.ArrayList;
import java.util.Calendar;

public class TempTrade extends PermTrade {

    private int startDate;
    private Calendar returnDate;

    /**
     * Creates a trade with an item that both the seller wants to sell
     * and the buyer wants to buy.
     * The buyer creates the trade with the item that they want to sell.
     * @param traderA           takes in a entities.User that wants to create the entities.Trade.
     * @param traderB           takes in a entities.User that wants to borrow the item based on the trade.
     * @param userAItemsToTrade takes in items that want to be traded to userB.
     * @param userBItemsToTrade takes in items that want to be traded to userA.
     * @param date              is a string that follows a specific date format.
     */
    public TempTrade(String traderA, String traderB, ArrayList<Item> userAItemsToTrade, ArrayList<Item> userBItemsToTrade, Calendar date) {
        super(traderA, traderB, userAItemsToTrade, userBItemsToTrade, date);
    }
    /**
     * This method provides you with the number of days left after a trade has been processed.
     * @return an integer that indicates the number of days left in the trade.
     */
    public int daysLeft() {
        int finishDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return finishDate - startDate;
    }
}

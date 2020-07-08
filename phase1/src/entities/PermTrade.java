package entities;// Written by Thanusun

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PermTrade extends Trade implements Serializable {

    private final LocalDateTime startDate;
    private final int daysInYear = 365;

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
     * @param startDate is a LocalDateTime that follows a start date of a trade.
     *                  Note: this can be used for invoicing once a trade is completed.
     */
    public PermTrade(String traderA, String traderB, ArrayList<Item> userAItemsToTrade, ArrayList<Item> userBItemsToTrade, LocalDateTime startDate) {
        super(traderA, traderB, userAItemsToTrade, userBItemsToTrade, startDate);
        this.startDate = startDate;
    }

    /**
     * This method provides you with the number of days left after a trade has been processed.
     * @return an integer that indicates the number of days left in the trade.
     */
    public int daysLeft() {
        LocalDateTime finishDate = LocalDateTime.now();
        return (daysInYear - startDate.getDayOfYear()) - finishDate.getDayOfYear();
    }
}

package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TempTrade extends PermTrade implements Serializable {

    private final LocalDateTime finishDate, startDate;
    private final int daysInYear = 365;

    /**
     * Creates a trade with an item that both the seller wants to sell
     * and the buyer wants to buy.
     * The buyer creates the trade with the item that they want to sell.
     * @param traderA takes in a entities.User that wants to create the entities.Trade.
     * @param traderB takes in a entities.User that wants to borrow the item based on the trade.
     * @param userAItemsToTrade takes in items that want to be traded to userB.
     * @param userBItemsToTrade takes in items that want to be traded to userA.
     * @param startDate is a LocalDateTime that follows a specific date format.
     * @param finishDate is a LocalDateTime that indicates the end of a temporary trade.
     */
    public TempTrade(String traderA, String traderB, ArrayList<Item> userAItemsToTrade, ArrayList<Item> userBItemsToTrade, LocalDateTime startDate, LocalDateTime finishDate) {
        super(traderA, traderB, userAItemsToTrade, userBItemsToTrade, startDate);
        this.finishDate = finishDate;
        this.startDate = startDate;
    }
    /**
     * This method provides you with the number of days left after a trade has been processed.
     * @return an integer that indicates the number of days left in the trade.
     */
    public int daysLeft() {
        return (daysInYear - startDate.getDayOfYear()) - (finishDate.getDayOfYear());
    }
}

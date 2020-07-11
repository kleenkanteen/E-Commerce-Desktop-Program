package F_entities;// Written by Thanusun

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PermTrade extends Trade implements Serializable {

    private final LocalDateTime startDate;
    private final int daysInYear = 365;
    private int traderAConfirmTimes = 0, traderBConfirmTimes = 0;
    private boolean failed = getFailed();

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

    @Override
    public boolean getCompleted() {
        return traderBConfirmTimes == 1 && traderAConfirmTimes == 1 && !failed;
    }

    /**
     * Confirms a meeting for the user if they are part of a trade.
     * If they are not part of a trade then an exception is thrown!
     *
     * @param traderName takes in a String that determines if they are part of the trade.
     * @param confirmation takes a boolean that determines if they confirmed the meeting.
     */
    @Override
    public void setConfirm(String traderName, boolean confirmation) {
        if (traderName.equals(getTraderA())) {
            if (needToConfirmMeetingOne(traderName)) {
                traderAConfirmTimes++;
                if (!confirmation) {
                    failed = true;
                }
            }
        } else if (traderName.equals(getTraderB())) {
            if (needToConfirmMeetingOne(traderName)) {
                traderBConfirmTimes++;
                if (!confirmation) {
                    failed = true;
                }
            }
        }
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

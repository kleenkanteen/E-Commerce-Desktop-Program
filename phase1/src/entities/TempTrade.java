package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TempTrade extends Trade implements Serializable {

    private int traderAConfirmTimes = 0, traderBConfirmTimes = 0;
    private boolean failed = getFailed();

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

    /**
     * Returns a boolean that determines whether the user has confirmed a meeting for the trade or not.
     * Even after the first meeting has been confirmed already.
     * If true is returned then the user has confirmed the trade, if false then the user has not
     * confirmed the meeting.
     *
     * @param traderName takes in a String that determines if they are part of the trade.
     * @return a boolean that determines if the trade has been confirmed by the user.
     */
    public boolean needToConfirmMeetingTwo(String traderName) {
        boolean meetingCompleted = false;
        boolean confirmTimes = traderAConfirmTimes == 1 && traderBConfirmTimes == 1,
                endTimePast = (LocalDateTime.now()).compareTo(finishDate) >= 0;
        if (traderName.equals(getTraderA())) {
            if (!failed && confirmTimes && endTimePast) {
                meetingCompleted = true;
            }
        } else if (traderName.equals(getTraderB())) {
            if (!failed && confirmTimes && endTimePast) {
                meetingCompleted = true;
            }
        }
        return meetingCompleted;
    }

    @Override
    public boolean getCompleted() {
        return traderBConfirmTimes == 2 && traderAConfirmTimes == 2 && !failed;
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
            if (needToConfirmMeetingOne(traderName) || needToConfirmMeetingTwo(traderName)) {
                traderAConfirmTimes++;
                if (!confirmation) {
                    failed = true;
                }
            }
        } else if (traderName.equals(getTraderB())) {
            if (needToConfirmMeetingOne(traderName) || needToConfirmMeetingTwo(traderName)) {
                traderBConfirmTimes++;
                if (!confirmation) {
                    failed = true;
                }
            }
        }
    }
}

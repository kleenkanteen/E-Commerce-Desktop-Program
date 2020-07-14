package F_entities;// Written by Thanusun

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Trade implements Serializable {
    private final LocalDateTime startDate;
    private final ArrayList<Item> traderBItemsToTrade;
    private final ArrayList<Item> traderAItemstoTrade;
    private final String traderA;
    private final String traderB;
    private boolean failed = false;
    private int traderAConfirmTimes = 0, traderBConfirmTimes = 0;
    private LocalDateTime creationDate = LocalDateTime.now();


    /**
     * Creates a trade with an item that both the seller wants to sell
     * and the buyer wants to buy.
     * The buyer creates the trade with the item that they want to sell.
     *
     * @param traderA             takes in a entities.trader that wants to create the entities.Trade.
     * @param traderB             takes in a entities.trader that wants to borrow the item based on the trade.
     * @param traderAItemsToTrade takes in items that want to be traded to traderB.
     * @param traderBItemsToTrade takes in items that want to be traded to traderA.
     * @param startDate           is a LocalDateTime type that indicates the start date of a Trade.
     *                            Note: this is also used to identify the rental process if the trade is temporary.
     */
    public Trade(String traderA, String traderB, ArrayList<Item> traderAItemsToTrade, ArrayList<Item> traderBItemsToTrade, LocalDateTime startDate) {
        this.traderA = traderA;
        this.traderB = traderB;
        this.startDate = startDate;
        this.traderAItemstoTrade = traderAItemsToTrade;
        this.traderBItemsToTrade = traderBItemsToTrade;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    boolean getFailed() {
        return failed;
    }

    void setFailed(boolean failed) {
        this.failed = failed;
    }

    int getTraderAConfirmTimes(){
        return traderAConfirmTimes;
    }

    int getTraderBConfirmTimes(){
        return traderBConfirmTimes;
    }

    void addTraderAConfirmTimes(){
        traderAConfirmTimes++;
    }

    void addTraderBConfirmTimes(){
        traderBConfirmTimes++;
    }

    public abstract boolean getCompleted();

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public ArrayList<Item> getTraderAItemsToTrade() {
        return traderAItemstoTrade;
    }

    public ArrayList<Item> getTraderBItemsToTrade() {
        return traderBItemsToTrade;
    }

    public String getTraderA() {
        return traderA;
    }

    public String getTraderB() {
        return traderB;
    }

    /**
     * Confirms a meeting for the user if they are part of a trade.
     * If they are not part of a trade then an exception is thrown!
     *
     * @param traderName takes in a String that determines if they are part of the trade.
     * @param confirmation takes a boolean that determines if they confirmed the meeting.
     */
    public abstract void setConfirm(String traderName, boolean confirmation);

    /**
     * Returns a boolean that determines whether the trader is the borrower of
     * the current trade.
     *
     * @param traderName takes in a trader to determine if its part of trade
     * @return a boolean that determines if the trader is part of the trade and is a borrower.
     */
    public boolean isBorrowed(String traderName) {
        boolean borrowed = false;
        if (traderName.equals(traderA)) {
            if (traderAItemstoTrade.isEmpty()) {
                borrowed = true;
            }
        } else if (traderName.equals(traderB)) {
            if (traderAItemstoTrade.isEmpty()) {
                borrowed = true;
            }
        }
        return borrowed;
    }

    /**
     * Returns a boolean that determines whether the trader is the lender of
     * the current trade.
     *
     * @param traderName takes in a trader to determine if its part of trade
     * @return a boolean that determines if the trader is part of the trade and is a lender.
     */
    public boolean isLent(String traderName) {
        boolean lent = false;
        if (traderName.equals(traderA)) {
            if (!traderAItemstoTrade.isEmpty()) {
                lent = true;
            }
        } else if (traderName.equals(traderB)) {
            if (!traderBItemsToTrade.isEmpty()) {
                lent = true;
            }
        }
        return lent;
    }

    /**
     * Returns the trading partner if there exists one in the Trade.
     *
     * @param traderName takes in a trader to find the other trader.
     * @return a string if there exists one. Otherwise, it returns a null.
     */
    public String tradingPartner(String traderName) {
        String otherTrader = null;
        // if TraderA, then return its partner: traderB
        if (traderName.equals(traderA)) {
            otherTrader = traderB;
            // if traderB, return its partner: traderA.
        } else if (traderName.equals(traderB)) {
            otherTrader = traderA;
        }
        return otherTrader;
    }

    /**
     * Returns a boolean that determines whether the user has confirmed a meeting for the trade or not.
     * If true is returned then the user has confirmed the trade, if false then the user has not
     * confirmed the meeting.
     *
     * @param traderName takes a String that represents the user trading.
     * @return a boolean that determines if the trade has been confirmed by the user.
     */
    public boolean needToConfirmMeetingOne(String traderName) {
        boolean meetingConfirmed = false;
        boolean startTimePast = (LocalDateTime.now()).compareTo(startDate) >= 0;
        if (traderName.equals(traderA)) {
            if (!failed && traderAConfirmTimes == 0 && startTimePast) {
                meetingConfirmed = true;
            }
        } else if (traderName.equals(traderB)) {
            if (!failed && traderBConfirmTimes == 0 && startTimePast) {
                meetingConfirmed = true;
            }
        }
        return meetingConfirmed;
    }

    @Override
    public String toString() {
        String info = "";
        if (traderBItemsToTrade.isEmpty()){
            info =  "The Trade is generated on" + getCreationDate() + "\n" +
                    "TraderA(Sender): " + getTraderA() +
                    "\nTraderB(Receiver): " + getTraderB() +
                    "\nItem: " + getTraderAItemsToTrade().get(0).getName()+
                    "\nThe Trade has been made on: " + getStartDate();
        }
        else if (traderAItemstoTrade.isEmpty()){
            info = "The Trade is generated on" + getCreationDate() + "\n" +
                    "TraderA(Receiver): " + getTraderA() +
                    "\nTraderB(Sender): " + getTraderB() +
                    "\nItem: " + getTraderBItemsToTrade().get(0).getName()+
                    "\nThe Trade has been made on: " + getStartDate();
        }
        else info = "The Trade is generated on" + getCreationDate() + "\n" +
                    "TraderA: " + getTraderA() +
                    "\nTraderB: " + getTraderB() +
                    "\n" + getTraderA()+ "confirmed to trade with: " + getTraderAItemsToTrade().get(0).getName() +
                    "\n" + getTraderB()+ "confirmed to trade with: " + getTraderBItemsToTrade().get(0).getName() +
                    "\nThe Trade has been made on: " + getStartDate();

        return info;
    }


    public boolean equals(Trade trade) {
        boolean traderAEqual = this.traderA.equals(trade.traderA);
        boolean traderBEqual = this.traderB.equals(trade.traderB);
        boolean traderAItemsEqual = this.traderAItemstoTrade.equals(trade.traderAItemstoTrade);
        boolean traderBItemsEqual = this.traderBItemsToTrade.equals(trade.traderBItemsToTrade);
        boolean startDateEquals = this.startDate.equals(trade.startDate);


        return traderAEqual
                && traderBEqual
                && traderAItemsEqual
                && traderBItemsEqual
                && startDateEquals;
    }
}

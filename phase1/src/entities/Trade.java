package entities;// Written by Thanusun

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Trade implements Serializable {
    private final LocalDateTime startDate;
    private final ArrayList<Item> traderBItemsToTrade;
    private final ArrayList<Item> traderAItemstoTrade;
    private final String traderA;
    private final String traderB;
    private boolean completed = false;

    /**
     * Creates a trade with an item that both the seller wants to sell
     * and the buyer wants to buy.
     * The buyer creates the trade with the item that they want to sell.
     * @param traderA takes in a entities.trader that wants to create the entities.Trade.
     * @param traderB takes in a entities.trader that wants to borrow the item based on the trade.
     * @param traderAItemsToTrade takes in items that want to be traded to traderB.
     * @param traderBItemsToTrade takes in items that want to be traded to traderA.
     * @param startDate is a LocalDateTime type that indicates the start date of a Trade.
     *             Note: this is also used to identify the rental process if the trade is temporary.
     */
    public Trade(String traderA, String traderB, ArrayList<Item> traderAItemsToTrade, ArrayList<Item> traderBItemsToTrade, LocalDateTime startDate) {
        this.traderA = traderA;
        this.traderB = traderB;
        this.startDate = startDate;
        this.traderAItemstoTrade = traderAItemsToTrade;
        this.traderBItemsToTrade = traderBItemsToTrade;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean isCompleted) {
        completed = isCompleted;
    }

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
     * Returns a boolean that determines whether the trader is the borrower of
     * the current trade.
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
}

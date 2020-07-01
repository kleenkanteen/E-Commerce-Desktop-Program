package entities;// Written by Thanusun

import java.util.ArrayList;
import java.util.Calendar;

public abstract class Trade {
    private final Calendar date;
    private final ArrayList<Item> userBItemsToTrade;
    private final ArrayList<Item> userAItemstoTrade;
    private final User traderA;
    private final User traderB;
    private boolean borrowed, lent = false;

    /**
     * Creates a trade with an item that both the seller wants to sell
     * and the buyer wants to buy.
     * The buyer creates the trade with the item that they want to sell.
     * @param traderA takes in a entities.User that wants to create the entities.Trade.
     * @param traderB takes in a entities.User that wants to borrow the item based on the trade.
     * @param userAItemsToTrade takes in items that want to be traded to userB.
     * @param userBItemsToTrade takes in items that want to be traded to userA.
     * @param date is a Calendar type that indicates the date of the trade.
     *             Note: this is also used to identify the rental process if the trade is temporary.
     */
    public Trade(User traderA, User traderB, ArrayList<Item> userAItemsToTrade, ArrayList<Item> userBItemsToTrade, Calendar date) {
        this.traderA = traderA;
        this.traderB = traderB;
        this.date = date;
        this.userAItemstoTrade = userAItemsToTrade;
        this.userBItemsToTrade = userBItemsToTrade;
    }

    /**
     * Returns a boolean that determines whether the trade has been completed.
     * If not, the method returns false.
     *
     * Note: Useful for admins, especially if they want to freeze accounts!
     *
     * @return a boolean that tells the user if the trade is completed.
     */
    public boolean tradeCompleted() {
        boolean completed = false;
        if (userAItemstoTrade.isEmpty() && userBItemsToTrade.isEmpty()) {
            completed = true;
        }
        return completed;
    }

    public Calendar getDate() {
        return date;
    }

    public ArrayList<Item> getUserAItemstoTrade() {
        return userAItemstoTrade;
    }

    public ArrayList<Item> getUserBItemsToTrade() {
        return userBItemsToTrade;
    }

    /**
     * Returns a boolean that determines whether the user is the borrower of
     * the current trade.
     * @param trader takes in a user to determine if its part of trade
     * @return a boolean that determines if the user is part of the trade and is a borrower.
     */
    public boolean isBorrowed(User trader) {
        if (trader == traderA) {
            if (userAItemstoTrade.isEmpty()) {
                borrowed = true;
            }
        } else if (trader == traderB) {
            if (userAItemstoTrade.isEmpty()) {
                borrowed = true;
            }
        }
        return borrowed;
    }

    /**
     * Returns a boolean that determines whether the user is the lender of
     * the current trade.
     * @param trader takes in a user to determine if its part of trade
     * @return a boolean that determines if the user is part of the trade and is a lender.
     */
    public boolean isLent(User trader) {
        if (trader == traderA) {
            if (!userAItemstoTrade.isEmpty()) {
                lent = true;
            }
        } else if (trader == traderB) {
            if (!userBItemsToTrade.isEmpty()) {
                lent = true;
            }
        }
        return lent;
    }

    /**
     * Returns the trading partner if there exists one in the Trade.
     * @param trader takes in a user to find the other trader.
     * @return a user if there exists one. Otherwise, it returns a null.
     */
    public User tradingPartner(User trader) {
        User otherTrader = null;
        if (trader == traderA) {
            otherTrader = traderB;
        } else if (trader == traderB) {
            otherTrader = traderA;
        }
        return otherTrader;
    }
}

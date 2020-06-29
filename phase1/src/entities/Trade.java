package entities;// Written by Thanusun

import entities.Item;
import entities.User;

import java.util.ArrayList;

public abstract class Trade {
    private final String time;
    private final String date;
    private final ArrayList<Item> userBItemsToTrade;
    private final ArrayList<Item> userAItemstoTrade;
    private final User traderA;
    private final User traderB;

    /**
     * Creates a trade with an item that both the seller wants to sell
     * and the buyer wants to buy.
     * The buyer creates the trade with the item that they want to sell.
     * @param traderA takes in a entities.User that wants to create the entities.Trade.
     * @param traderB takes in a entities.User that wants to borrow the item based on the trade.
     * @param userAItemsToTrade takes in items that want to be traded to userB.
     * @param userBItemsToTrade takes in items that want to be traded to userA.
     * @param date is a string that follows a specific date format.
     * @param time is a string that follows a time.
     */
    public Trade(User traderA, User traderB, ArrayList<Item> userAItemsToTrade, ArrayList<Item> userBItemsToTrade, String date, String time) {
        this.traderA = traderA;
        this.traderB = traderB;
        this.date = date;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Item> getUserAItemstoTrade() {
        return userAItemstoTrade;
    }

    public ArrayList<Item> getUserBItemsToTrade() {
        return userBItemsToTrade;
    }

}

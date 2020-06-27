// Written by Thanusun

public class PermTrade extends Trade {

    /**
     * Creates a trade with an item that both the seller wants to sell
     * and the buyer wants to buy.
     * The buyer creates the trade with the item that they want to sell.
     *
     * @param seller      takes in a User that wants to create the Trade.
     * @param buyer       takes in a User that wants to borrow the item based on the trade.
     * @param itemToTrade takes in an item that both the buyer and seller want.
     * @param date        is a string that follows a specific date format.
     * @param time        is a string that follows a time.
     */
    public PermTrade(User seller, User buyer, Item itemToTrade, String date, String time) {
        super(seller, buyer, itemToTrade, date, time);
    }
}
public class PermTrade extends Trade {


    /**
     * Creates a trade with an item that both the seller wants to sell
     * and the buyer wants to buy.
     * The buyer creates the trade with the item that they want to sell.
     *
     * @param itemToTrade takes in an item that both the buyer and seller want.
     * @param date        is a string that follows a specific date format.
     * @param time        is a string tha follows a time.
     */
    public PermTrade(Item itemToTrade, String date, String time) {
        super(itemToTrade, date, time);
    }


}


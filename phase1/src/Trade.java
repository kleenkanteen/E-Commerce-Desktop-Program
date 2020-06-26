// Written by Thanusun

public abstract class Trade {
    private final User seller;
    private final User buyer;
    private final Item itemToTrade;
    private final String time;
    private final String date;

    /**
     * Creates a trade with an item that both the seller wants to sell
     * and the buyer wants to buy.
     * The buyer creates the trade with the item that they want to sell.
     * @param seller takes in a User that wants to create the Trade.
     * @param buyer takes in a User that wants to borrow the item based on the trade.
     * @param itemToTrade takes in an item that both the buyer and seller want.
     * @param date is a string that follows a specific date format.
     * @param time is a string tha follows a time.
     */
    public Trade(User seller, User buyer, Item itemToTrade, String date, String time) {
        this.seller = seller;
        this.buyer = buyer;
        this.date = date;
        this.time = time;
        this.itemToTrade = itemToTrade;
    }

    /**
     * Returns a boolean that determines whether the trade has been completed.
     * If not, the method returns false.
     *
     * Note: Useful for admins, especially if they want to freeze accounts!
     * @param sellerAccepts takes a boolean and tells us that the seller accepts all details
     * @param buyerAccepts takes a boolean and tells us that the buyer accepts all the details.
     * @return
     */
    public boolean tradeCompleted(boolean sellerAccepts, boolean buyerAccepts) {
        boolean completed = false;
        if (sellerAccepts == buyerAccepts) {
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

    public Item getItemToTrade() {
        return itemToTrade;
    }
}

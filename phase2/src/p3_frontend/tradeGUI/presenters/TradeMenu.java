package p3_frontend.tradeGUI.presenters;


public class TradeMenu {
    public final static String TRADE = "Trade";
    public final static String TRADELEND = "Trade lending";
    public final static String TRADETYPE = "What type of trade?";
    public final static String TEMP = "Temporary";
    public final static String PERM = "Permanent";
    public final static String ONETWOWAY = "Is it a one way or two way trade?";
    public final static String ONEWAY = "One way trade";
    public final static String TWOWAY = "Two way trade";
    public final static String SUBMIT = "Submit";
    public final static String SUCCESS = "Success";
    public final static String EXIT = "Exit the program";
    public final static String WRONGFORMAT = "Wrong format";
    public final static String ERROR = "You didn't complete the trade! Check if you're missing anything.";
    public final static String NOITEMS = "Looks like you don't have any items to give to the other user, try again after adding items!";
    public final static String SUGGEST = "Here are a list of items that you should lend in the trade: ";
    public final static String PASTDATE = "Entered a date in the past";
    public final static String INVENTORY_PROMPT = "Please select from your items the items you want to trade";
    public final static String SELECT_ITEM = "Items selected";

    public static String inventoryPrompt(String username){
        return username + "'s inventory";
    }

    public static String itemSelected(String itemName){
        return itemName +" is selected";
    }
}

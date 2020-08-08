package presenters;

import entities.Item;
import java.util.ArrayList;
import java.util.List;


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
    public final static String EXIT = "Exit the program";
    public final static String ERROR = "You didn't complete the trade! Check if you're missing anything.";
    public final static String NOITEMS = "Looks like you don't have any items to give to the other user, try again after adding items!";


    /**
     * Presenter for choosing permanent trade or temporary trade.
     */
    public void choosePermTemp() {
        System.out.println("What trade would you like to complete today? Type '-1' if you want to exit the trade." +
                "\n[1] Start a permanent transaction with a user." +
                "\n[2] Start a temporary transaction with a user.");
    }

    /**
     * Presenter for choosing one way trade or two way trade.
     */
    public void chooseOneOrTwo() {
        System.out.println("What type of trade is it? \n" +
                "\n[1] One way trade." +
                "\n[2] Two way trade.");
    }

    /**
     * Presenter for providing all items available to trade.
     * @param personalInventory is an ArrayList that takes in the personal inventory of a trader.
     */
    public void itemsAvailableToTrade(List<Item> personalInventory)  {
        int i = 1;
        for (Item item : personalInventory) {
            System.out.println("[" + i + "]" + " " + item.getName());
            i++;
        }
    }

    /**
     * Presenter for items that want to be traded.
     */
    public void itemsWantToTrade() {
        System.out.println("Type in the corresponding number to add to the items you want to trade." +
                "\nWhen finished, type -1 as input: ");
    }

    /**
     * Presenter for entering a date.
     */
    public void enterDate() {
        System.out.println("Enter the date and time (in 24-hour format!!!) in " +
                "which the meeting occurs (format: YYYY-MM-DD HH:MM): ");
    }

    /**
     * Presenter for entering a place.
     */
    public void enterPlace() {
        System.out.print("Enter the place of the meeting: ");
    }

    /**
     * Presenter for sending a trade request.
     * @param userB is a string that is the second trader.
     */
    public void tradeRequestSent(String userB) {
        System.out.println("Congratulations! Your trade request has been sent to " + userB + ".\n");
    }

    /**
     * Presenter for entering a wrong format.
     */
    public void wrongFormat(){
        System.out.println("That is incorrect! Try again.\n");
    }

    /**
     * Presenter for entering an incorrect date.
     */
    public void enteredPastDate() {
        System.out.println("Date and Time must be in future, try again.\n");
    }

    /**
     * Presenter for entering invalid input.
     */
    public void invalidInput(){
        System.out.println("Invalid input.\n");
    }

    /**
     * Presenter for an unavailable choice.
     */
    public void unavailableChoice() {
        System.out.println("Sorry, this selection is not available for you! :-( Make a two way trade or a lend first");
    }

    /**
     * Presenter for adding an item as an item to trade by a user.
     */
    public void addedItem() {
        System.out.println("Item has been added!");
    }

    /**
     * Presenter for telling the user they have no more items to put up for trade.
     */
    public void noMoreItems() {
        System.out.println("Uh Oh! :O Looks like you don't have any items to trade! Bummer :-(\nExiting you from adding more items.");
    }

    // TODO test this.
    public void showSuggestedItems(List<Item> suggestedItems) {
        System.out.println("Here's a list of suggested items you should try trading to the other person:");
        int i = 1;
        for (Item item : suggestedItems) {
            System.out.println(item.getName());
            i++;
        }
    }
}

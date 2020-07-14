package D_presenters;

import F_entities.Item;
import java.util.ArrayList;


public class TradeMenu {
    /**
     * Presenter for choosing permanent trade or temporary trade.
     */
    public void choosePermTemp() {
        System.out.println("What trade would you like to complete today? \n" +
                "[1] Start a permanent transaction with a user.\n" +
                "[2] Start a temporary transaction with a user.\n" +
                "Enter any other number to exit.\n");
    }

    /**
     * Presenter for choosing one way trade or two way trade.
     */
    public void chooseOneOrTwo() {
        System.out.println("What type of trade is it? \n" +
                "[1] One way trade \n" +
                "[2] Two way trade \n");
    }

    /**
     * Presenter for providing all items avaliable to trade.
     * @param personalInventory is an ArrayList that takes in the personal inventory of a trader.
     */
    public void itemsAvaliableToTrade(ArrayList<Item> personalInventory)  {
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
        System.out.print("Type in the corresponding number to add to the items you want to trade \n" +
                "When finished, type -1 as input: ");
    }

    /**
     * Presenter for entering a date.
     */
    public void enterDate() {
        System.out.print("Enter the date and time (in 24-hour format) in which the meeting occurs (format: 2020-04-20 4:20): ");
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
        System.out.println("Congratulations! Your trade request has been sent to " + userB + ".");
    }

    /**
     * Presenter for entering a wrong format.
     */
    public void wrongFormat(){
        System.out.println("That is incorrect! Try again.");
    }

    /**
     * Presenter for entering an incorrect date.
     */
    public void enteredPastDate() {
        System.out.println("Date and Time must be in future, try again.");
    }

    /**
     * Presenter for entering invalid input.
     */
    public void invalidInput(){
        System.out.println("Invalid input");
    }
}

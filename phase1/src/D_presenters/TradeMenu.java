package D_presenters;

import F_entities.Item;
import java.util.ArrayList;


public class TradeMenu {
    public void choosePermTemp() {
        System.out.println("What trade would you like to complete today? \n" +
                "Type: \n[1] Start a permanent transaction with a user.\n" +
                "[2] Start a temporary transaction with a user.\n" +
                "Enter any other number to exit.\n");
    }

    public void chooseOneOrTwo() {
        System.out.println("What type of trade is it? \n" +
                "[1] One way trade \n" +
                "[2] Two way trade \n");
    }

    public void itemsAvaliableToTrade(ArrayList<Item> personalInventory)  {
        int i = 1;
        for (Item item : personalInventory) {
            System.out.println("[" + i + "]" + " " + item.getName());
            i++;
        }
    }

    public void itemsWantToTrade() {
        System.out.print("Type in the corresponding number to add to the items you want to trade \n" +
                "When finished, type -1 as input: ");
    }

    public void enterDate() {
        System.out.print("Enter the date and time (in 24-hour format) in which the meeting occurs (format: 2020-04-20 4:20): ");
    }

    public void enterPlace() {
        System.out.print("Enter the place of the meeting: ");
    }

    public void tradeRequestSent(String userB) {
        System.out.println("Congratulations! Your trade request has been sent to " + userB + ".");
    }

    public void wrongFormat(){
        System.out.println("That is incorrect! Try again.");
    }

    public void invalidInput(){
        System.out.println("Invalid input");
    }
}

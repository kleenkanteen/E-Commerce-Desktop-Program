package controller_presenter_gateway;

import java.io.IOException;
import java.util.Scanner;

public class TradeMenu {

    private final Scanner input = new Scanner(System.in);

    /**
     * Presents a User friendly trade menu that'll allow people to
     * trade different items to add to their collection of items!
     * @throws IOException when input is unknown or is not an integer.
     */
    public void run() throws IOException {
        System.out.println("What trade would you like to complete today?");
        System.out.println("Type: \n [1] Start a permanent transaction with a user.");
        System.out.println("[2] Start a lending transaction with a user.");
        System.out.println("[3] Go back.");
        System.out.println("Enter any other number to exit.");

        int choice = input.nextInt();

        switch (choice) {
            case 1:
              // TODO ask team what the presenter should do when a user wants to start a trade.
            case 2:
                // TODO ^ same as above.
            case 3:
                // TODO same as above.
            default:
                System.exit(0);
        }
    }
}

package controller_presenter_gateway;

import entities.Item;
import entities.TradeRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class TradeMenu {

    private final Scanner input = new Scanner(System.in);
    private final int oneWay = 1;
    private int tradeType;
    private boolean perm;
    private TradeRequest tradeRequest;

    public void permTradeRequest(int tradeType, String trader, ArrayList<Item> itemsToTrade) {
        perm = true;
        String userA = trader;
        LocalDateTime date = ;
        String place = ;
        ArrayList<Item> itemsFromTrader = itemsToTrade;

        switch (tradeType) {
            case 1:
                tradeRequest = new TradeRequest(trader, perm);
            case 2:

                tradeRequest = new TradeRequest(perm);
        }
    }

    public void tempTradeRequest(int tradeType, String trader, ArrayList<Item> itemsToTrade) {
        perm = false;
        String userA = trader;
        LocalDateTime date = ;
        String place = ;
        ArrayList<Item> itemsFromTrader = itemsToTrade;
        switch (tradeType) {
            case 1:
                tradeRequest = new TradeRequest(perm);
            case 2:
                tradeRequest = new TradeRequest(perm);
        }
    }

    public void runMakeLent(ArrayList<Item> itemsToTrade) {
        System.out.println("What trade would you like to complete today?");
        System.out.println("Type: \n [1] Start a permanent transaction with a user.");
        System.out.println("[2] Start a lending transaction with a user.");
        System.out.println("[3] Go back.");
        System.out.println("Enter any other number to exit.");

        int choice = input.nextInt();

        switch (choice) {
            case 1:
                System.out.println("What type of trade is it? Type: " +
                        "\n [1] One Way Trade " +
                        "\n [2] Two Way Trade");
                tradeType = input.nextInt();
                permTradeRequest(tradeType, itemsToTrade);
            case 2:
                System.out.println("What type of trade is it? Type: " +
                        "\n [1] One Way Trade " +
                        "\n [2] Two Way Trade");
                tradeType = input.nextInt();
                tempTradeRequest(tradeType, itemsToTrade);
            case 3:
            default:
                input.close();
                System.exit(0);
        }

    }

    // two way trade or one way trade.
    public void runBorrow(ArrayList<Item> itemsToTrade, String trader) {



        int choice = input.nextInt();

        switch (choice) {
            case 1:
                System.out.println("What type of trade is it? Type: " +
                        "\n [1] One Way Trade " +
                        "\n [2] Two Way Trade");
                tradeType = input.nextInt();
                permTradeRequest(trader, itemsToTrade, tradeType);
            case 2:
                System.out.println("Is this a one way trade or two way trade?");
                tradeType = input.nextInt();
                tempTradeRequest(tradeType);
            case 3:
            default:
                input.close();
                System.exit(0);
        }

    }

    /**
     * Presents a User friendly trade menu that'll allow people to
     * trade different items to add to their collection of items!
     * @throws IOException when input is unknown or is not an integer.
     */
    // TODO add items choice within the user input.

}

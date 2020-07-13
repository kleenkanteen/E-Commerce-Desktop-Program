package C_controllers;

import D_presenters.TradeMenu;
import F_entities.Item;
import F_entities.TradeRequest;
import F_entities.TradeRequestMessage;
import E_use_cases.TradeManager;
import E_use_cases.UserManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.time.LocalDateTime.parse;

public class TradeController {

    private final Scanner input = new Scanner(System.in);
    private final TradeMenu tradeMenu = new TradeMenu();
    private LocalDateTime date;

    private final UserManager allUsers;

    private String tradeType;
    private boolean done;
    private TradeRequest tradeRequest;


    public TradeController(UserManager allUsers) {
        this.allUsers = allUsers;
    }

    /**
     * Does the same thing as run except it only works on temporary trades and it MUST be a one way trade.
     * @param itemsToTrade takes in an arraylist of items that represent the items to trade from userB.
     * @param trader is a string that indicates the second trader (userA).
     */
    public void runFromLoan(ArrayList<Item> itemsToTrade, String trader) {
        TradeRequestMessage tradeRequestMessage = null;
        TradeRequest tradeRequest = null;
        String userA = trader;
        String userB = itemsToTrade.get(0).getOwnerName();

        LocalDateTime date = getDateInput();

        // asks for place.
        tradeMenu.enterPlace();
        String place = input.nextLine();

        ArrayList<Item> itemsToTradeB = itemsToTrade;

        tradeType = input.nextLine();
        tradeRequest = new TradeRequest(userA, userB, itemsToTradeB, false, date, place);
        tradeRequestMessage = new TradeRequestMessage("User " + userA + " wants to trade with you.", tradeRequest, userA);
        allUsers.addUserMessage(userB, tradeRequestMessage);
        tradeMenu.tradeRequestSent(userB);
    }

    /**
     * Presents a User friendly trade menu that'll allow people to
     * trade different items to add to their collection of items!
     * @param itemsToTrade takes in an arraylist of items that represent the items to trade from userB.
     * @param trader is a string that indicates the second trader (userA).
     */
    public void run(ArrayList<Item> itemsToTrade, String trader) {
        TradeRequestMessage tradeRequestMessage = null;
        String userA = trader;
        String userB = itemsToTrade.get(0).getOwnerName();

        LocalDateTime date = getDateInput();

        // asks for place.
        tradeMenu.enterPlace();
        String place = input.nextLine();

        ArrayList<Item> itemsToTradeA = new ArrayList<Item>();
        ArrayList<Item> itemsToTradeB = itemsToTrade;
        while(true) {
            // have a presenter that asks for perm trade or temp trade.
            tradeMenu.choosePermTemp();
            String selection = input.nextLine();

            switch (selection) {
                // perm trade
                case "1":
                    // ask the user if its one way or two way trade.
                    tradeMenu.chooseOneOrTwo();
                    tradeType = input.nextLine();
                    invalidTradeTypeChoice();
                    itemsToTradeA = oneOrTwoWayTrade(tradeType, userA, itemsToTradeA);
                    tradeRequestMessage = permTradeRequest(userA, userB, itemsToTradeA, itemsToTradeB, date, place);
                    allUsers.addUserMessage(userB, tradeRequestMessage);
                    tradeMenu.tradeRequestSent(userB);
                // temp trade
                case "2":
                    // ask the user if its one way or two way trade.
                    tradeMenu.chooseOneOrTwo();
                    tradeType = input.nextLine();
                    invalidTradeTypeChoice();
                    itemsToTradeA = oneOrTwoWayTrade(tradeType, userA, itemsToTradeA);
                    tradeRequestMessage = tempTradeRequest(userA, userB, itemsToTradeA, itemsToTradeB, date, place);
                    allUsers.addUserMessage(userB, tradeRequestMessage);
                    tradeMenu.tradeRequestSent(userB);
                default:
                    tradeMenu.invalidInput();
            }
        }
    }

    private LocalDateTime getDateInput() {
        // tell user that it has to be a specific format.
        String datePattern = "yyyy-MM-ddH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);

        boolean dateGiven = false;
        while (!dateGiven) {
            try {
                tradeMenu.enterDate();
                String dateInput = input.nextLine();
                // asks for date, but will throw an error telling the user its wrong.
                dateInput = dateInput.replaceAll("\\s+", "");
                date = parse(dateInput, formatter);
                LocalDateTime today = LocalDateTime.now();
                // checks if the date inputted is not before today's date.
                if (date.isBefore(today)) {
                    tradeMenu.wrongFormat();
                } else {
                    dateGiven = true;
                }
            } catch (DateTimeParseException e) {
                tradeMenu.wrongFormat();
                dateGiven = false;
            }
        }
        return date;
    }

    private void invalidTradeTypeChoice() {
        while(!tradeType.equals("1") && !tradeType.equals("2")){
            tradeMenu.invalidInput();
            tradeMenu.chooseOneOrTwo();
            tradeType = input.nextLine();
        }
    }

    private ArrayList<Item> oneOrTwoWayTrade(String tradeType, String userA, ArrayList<Item> itemsToTradeA) {
        switch (tradeType) {
            // one way trade
            case "1":
                itemsToTradeA = new ArrayList<Item>();
                return itemsToTradeA;
            // two way trade
            case "2":
                ArrayList<Item> items = allUsers.getUserInventory(userA);
                // ask the user what items they want to trade, then add it into itemsToTradeA.
                while (!done) {
                    tradeMenu.itemsAvaliableToTrade(items);
                    // choose the items you want to trade to the other user.
                    tradeMenu.itemsWantToTrade();
                    int choice = input.nextInt();
                    if (choice == -1) {
                        done = true;
                    } else if (choice > 0 && choice <= items.size()) {
                        itemsToTradeA.add(items.get(choice - 1));
                        done = true;
                    }
                }
        }
        return itemsToTradeA;
    }

    private TradeRequestMessage permTradeRequest(String userA,
                                                 String userB,
                                                 ArrayList<Item> itemsToTradeA,
                                                 ArrayList<Item> itemsToTradeB,
                                                 LocalDateTime date,
                                                 String place) {
        switch (tradeType) {
            case "1":
                tradeRequest = new TradeRequest(userA, userB, itemsToTradeB, true, date, place);
            case "2":
                tradeRequest = new TradeRequest(userA, userB, itemsToTradeB, itemsToTradeA, true, date, place);
        }

        return new TradeRequestMessage("User " + userA + " wants to trade with you.", tradeRequest, userA);
    }

    private TradeRequestMessage tempTradeRequest(String userA,
                                                 String userB,
                                                 ArrayList<Item> itemsToTradeA,
                                                 ArrayList<Item> itemsToTradeB,
                                                 LocalDateTime date,
                                                 String place) {

        switch (tradeType) {
            case "1":
                tradeRequest = new TradeRequest(userA, userB, itemsToTradeB, false, date, place);
            case "2":
                tradeRequest = new TradeRequest(userA, userB, itemsToTradeB, itemsToTradeA, false, date, place);
        }
        return new TradeRequestMessage("User " + userA + " wants to trade with you.", tradeRequest, userA);
    }

}


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
    private TradeRequest tradeRequest;


    /**
     * Takes in a UserManager in order to search for userA making a trade request to userB.
     * @param allUsers takes in a UserManager that contains all users that are in the system.
     */
    public TradeController(UserManager allUsers) {
        this.allUsers = allUsers;
    }

    /**
     * Takes an ArrayList and a String that will be used to loan items to userB.
     * This method is similar to run, except it only accomplishes a one way trade temp/perm trade request.
     * @param itemsToTrade takes in an arraylist of items that represent the items to loan to userB.
     * @param userA is a string that indicates the current trader (userA).
     * @param userB is a string that indicates the second trader (userB).
     */
    public void runFromLoan(ArrayList<Item> itemsToTrade, String userA, String userB) {
        TradeRequest tradeRequest;

        // set the date/time
        LocalDateTime date = getDateInput();

        // asks for place.
        this.tradeMenu.enterPlace();
        String place = this.input.nextLine();

        String selection;
        do {
            // have a presenter that asks for perm trade or temp trade.
            this.tradeMenu.choosePermTemp();
            selection = this.input.nextLine();
            switch (selection) {
                // perm trade
                case "1":
                    tradeRequest = new TradeRequest(userA, userB, itemsToTrade, new ArrayList<>(),
                            true, date, place);
                    this.allUsers.createAndAddNewTradeRequestMessage(userB,
                            "User " + userA + " wants to trade with you.", tradeRequest, userA);
                    break;
                // temp trade
                case "2":
                    tradeRequest = new TradeRequest(userA, userB, itemsToTrade, new ArrayList<>(),
                            false, date, place);
                    this.allUsers.createAndAddNewTradeRequestMessage(userB,
                            "User " + userA + " wants to trade with you.", tradeRequest, userA);
                    break;
                default:
                    tradeMenu.invalidInput();

            }
        }while(!selection.equals("1")&&!selection.equals("2"));
        tradeMenu.tradeRequestSent(userB);
    }

    /**
     * Presents a User friendly trade menu that'll allow people to
     * trade different items to add to their collection of items!
     * @param itemsToTradeB takes in an arraylist of items that represent the items to trade from userB.
     * @param userA is a string that indicates the current user (userA)
     */
    public void run(ArrayList<Item> itemsToTradeB, String userA) {
        TradeRequestMessage tradeRequestMessage;
        String userB = itemsToTradeB.get(0).getOwnerName();

        // get date/time
        LocalDateTime date = getDateInput();

        // asks for place.
        this.tradeMenu.enterPlace();
        String place = this.input.nextLine();


        ArrayList<Item> itemsToTradeA = new ArrayList<>();
        boolean done = false;
        do {
            // have a presenter that asks for perm trade or temp trade.
            this.tradeMenu.choosePermTemp();
            String selection = this.input.nextLine();

            switch (selection) {
                // perm trade
                case "1":
                    // ask the user if its one way or two way trade.
                    this.tradeMenu.chooseOneOrTwo();
                    this.tradeType = this.input.nextLine();
                    invalidTradeTypeChoice();
                    itemsToTradeA = oneOrTwoWayTrade(this.tradeType, userA, itemsToTradeA);
                    tradeRequestMessage = permTradeRequest(userA, userB, itemsToTradeB, itemsToTradeA, date, place);
                    this.allUsers.addUserMessage(userB, tradeRequestMessage);
                    this.tradeMenu.tradeRequestSent(userB);
                    done = true;
                    break;
                // temp trade
                case "2":
                    // ask the user if its one way or two way trade.
                    this.tradeMenu.chooseOneOrTwo();
                    this.tradeType = this.input.nextLine();
                    invalidTradeTypeChoice();
                    itemsToTradeA = oneOrTwoWayTrade(this.tradeType, userA, itemsToTradeA);
                    tradeRequestMessage = tempTradeRequest(userA, userB, itemsToTradeB, itemsToTradeA, date, place);
                    this.allUsers.addUserMessage(userB, tradeRequestMessage);
                    this.tradeMenu.tradeRequestSent(userB);
                    done = true;
                    break;
                default:
                    this.tradeMenu.invalidInput();
            }
        }while(!done);
    }

    private LocalDateTime getDateInput() {
        // tell user that it has to be a specific format.
        String datePattern = "yyyy-MM-ddH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);

        // get date input
        boolean dateGiven = false;
        while (!dateGiven) {
            try {
                this.tradeMenu.enterDate();
                String dateInput = this.input.nextLine();
                // asks for date, but will throw an exception telling the user its wrong.
                dateInput = dateInput.replaceAll("\\s+", "");
                this.date = parse(dateInput, formatter);
                LocalDateTime today = LocalDateTime.now();
                // checks if the date inputted is not before today's date.
                if (this.date.isBefore(today)) {
                    this.tradeMenu.enteredPastDate();
                } else {
                    dateGiven = true;
                }
            } catch (DateTimeParseException e) {
                this.tradeMenu.wrongFormat();
                dateGiven = false;
            }
        }
        return date;
    }

    private void invalidTradeTypeChoice() {
        while(!this.tradeType.equals("1") && !this.tradeType.equals("2")){
            this.tradeMenu.invalidInput();
            this.tradeMenu.chooseOneOrTwo();
            this.tradeType = this.input.nextLine();
        }
    }

    private ArrayList<Item> oneOrTwoWayTrade(String tradeType, String userA, ArrayList<Item> itemsToTradeA) {
        switch (tradeType) {
            // one way trade
            case "1":
                return new ArrayList<>();
            // two way trade
            case "2":
                ArrayList<Item> items = this.allUsers.getUserInventory(userA);
                boolean done = false;
                // ask the user what items they want to trade, then add it into itemsToTradeA.
                while (!done) {
                    this.tradeMenu.itemsAvailableToTrade(items);
                    // choose the items you want to trade to the other user.
                    this.tradeMenu.itemsWantToTrade();
                    int choice = this.input.nextInt();
                    // if input is invalid
                    if (choice == -1) {
                        done = true;
                    }
                    // add user input offered items
                    else if (choice > 0 && choice <= items.size()) {
                        itemsToTradeA.add(items.get(choice - 1));
                        // change for when you want to offer more than one item
                        done = true;
                    }
                }
        }
        return itemsToTradeA;
    }

    private TradeRequestMessage permTradeRequest(String userA, String userB, ArrayList<Item> itemsToTradeA,
                                                 ArrayList<Item> itemsToTradeB, LocalDateTime date, String place) {
        switch (this.tradeType) {
            // one way
            case "1":
                this.tradeRequest = new TradeRequest(userA, userB, itemsToTradeB, true, date, place);
            // two way
            case "2":
                this.tradeRequest = new TradeRequest(userA, userB, itemsToTradeB,
                        itemsToTradeA, true, date, place);
        }

        return new TradeRequestMessage("User " + userA + " wants to trade with you.", this.tradeRequest, userA);
    }

    private TradeRequestMessage tempTradeRequest(String userA, String userB, ArrayList<Item> itemsToTradeA,
                                                 ArrayList<Item> itemsToTradeB, LocalDateTime date, String place) {

        switch (this.tradeType) {
            // one way
            case "1":
                this.tradeRequest = new TradeRequest(userA, userB, itemsToTradeB, false, date, place);
            // two way
            case "2":
                this.tradeRequest = new TradeRequest(userA, userB, itemsToTradeB,
                        itemsToTradeA, false, date, place);
        }
        return new TradeRequestMessage("User " + userA + " wants to trade with you.", this.tradeRequest, userA);
    }

}


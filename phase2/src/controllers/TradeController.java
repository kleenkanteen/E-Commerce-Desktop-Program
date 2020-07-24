package controllers;

import presenters.TradeMenu;
import entities.Item;
import use_cases.GlobalInventoryManager;
import use_cases.TradeRequestManager;
import use_cases.UserManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.time.LocalDateTime.parse;

public class TradeController {

    private final Scanner input = new Scanner(System.in);
    private final TradeMenu tradeMenu = new TradeMenu();
    private LocalDateTime date;
    private final GlobalInventoryManager usersInventory;
    private final UserManager allUsers;
    private String tradeType;
    private TradeRequestManager tradeRequestManager;


    /**
     * Takes in a UserManager in order to search for userA making a trade request to userB.
     * @param usersInventory takes in a GlobalInventoryManager that contains all users that are in the system.
     */
    public TradeController(UserManager allUsers, GlobalInventoryManager usersInventory) {
        this.allUsers = allUsers;
        this.usersInventory = usersInventory;

    }

    /**
     * Takes an ArrayList and a String that will be used to loan items to userB.
     * This method is similar to run, except it only accomplishes a one way trade temp/perm trade request.
     * @param itemsToTrade takes in an arraylist of items that represent the items to loan to userB.
     * @param userA is a string that indicates the current trader (userA).
     * @param userB is a string that indicates the second trader (userB).
     */
    public void runFromLoan(ArrayList<Item> itemsToTrade, String userA, String userB) {

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
                    this.tradeRequestManager = new TradeRequestManager("User " + userA + "wants to trade with you.", userA);
                    this.tradeRequestManager.setInfo(userA, userB, itemsToTrade, new ArrayList<>(), true);
                    this.tradeRequestManager.setDateAndPlace(userB, date, place);
                    break;
                // temp trade
                case "2":
                    this.tradeRequestManager = new TradeRequestManager("User " + userA + "wants to trade with you.", userA);
                    this.tradeRequestManager.setInfo(userA, userB, itemsToTrade, new ArrayList<>(), false);
                    this.tradeRequestManager.setDateAndPlace(userB, date, place);
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
     * @param numTrades the num of trades this user has made
     */
    public void run(ArrayList<Item> itemsToTradeB, String userA, int numTrades) {
        TradeRequestManager tradeRequestMessage;
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
                    if(itemsToTradeA.isEmpty() && numTrades == 0){
                        tradeMenu.unavailableChoice();
                        return;
                    }
                    tradeRequestMessage = permTradeRequest(userA, userB, itemsToTradeB, itemsToTradeA, date, place);
                    this.allUsers.addUserMessage(userB, tradeRequestMessage.getTradeRequest());
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
                    if(itemsToTradeA.isEmpty() && numTrades == 0){
                        tradeMenu.unavailableChoice();
                        return;
                    }
                    tradeRequestMessage = tempTradeRequest(userA, userB, itemsToTradeB, itemsToTradeA, date, place);
                    usersInventory.getPersonInventory(userB);
                    this.allUsers.addUserMessage(userB, tradeRequestMessage.getTradeRequest());
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
                // asks for date
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
                ArrayList<Item> items = usersInventory.getPersonInventory(userA);
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

    private TradeRequestManager permTradeRequest(String userA, String userB, ArrayList<Item> itemsToTradeA,
                                          ArrayList<Item> itemsToTradeB, LocalDateTime date, String place) {

        // userA is current trader.
        // userB is second trader.
        switch (this.tradeType) {
            // one way
            case "1":
                this.tradeRequestManager = new TradeRequestManager("User " + userA + " wants to trade with you.", userA);
                this.tradeRequestManager.setInfo(userA, userB, itemsToTradeB, itemsToTradeA, true);
                tradeRequestManager.setDateAndPlace(userB, date, place);
            // two way
            case "2":
                tradeRequestManager = new TradeRequestManager("User " + userA + " wants to trade with you.", userA);
                tradeRequestManager.setInfo(userA, userB, itemsToTradeB, itemsToTradeA, true);
                tradeRequestManager.setDateAndPlace(userB, date, place);
        }

        return tradeRequestManager;
    }

    private TradeRequestManager tempTradeRequest(String userA, String userB, ArrayList<Item> itemsToTradeA,
                                          ArrayList<Item> itemsToTradeB, LocalDateTime date, String place) {

        switch (tradeType) {
            // one way
            case "1":
                tradeRequestManager = new TradeRequestManager("User " + userA + " wants to trade with you.", userA);
                tradeRequestManager.setInfo(userA, userB, itemsToTradeB, itemsToTradeA, false);
                tradeRequestManager.setDateAndPlace(userB, date, place);
            // two way
            case "2":
                tradeRequestManager = new TradeRequestManager("User " + userA + " wants to trade with you.", userA);
                tradeRequestManager.setInfo(userA, userB, itemsToTradeB, itemsToTradeA, false);
                tradeRequestManager.setDateAndPlace(userB, date, place);
        }
        return tradeRequestManager;
    }

}


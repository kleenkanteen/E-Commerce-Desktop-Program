package controllers;

import entities.TradeRequest;
import presenters.TradeMenu;
import entities.Item;
import use_cases.GlobalInventoryManager;
import use_cases.GlobalWishlistManager;
import use_cases.TradeRequestManager;
import use_cases.UserManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.time.LocalDateTime.parse;

public class TradeController {

    private final Scanner input = new Scanner(System.in);
    private final TradeMenu tradeMenu = new TradeMenu();
    private final GlobalWishlistManager usersWishlist;
    private LocalDateTime date;
    private final GlobalInventoryManager usersInventory;
    private String tradeType;
    private TradeRequestManager tradeRequestManager;


    /**
     * Takes in a UserManager in order to search for userA making a trade request to userB.
     * @param usersInventory takes in a GlobalInventoryManager that contains all users that are in the system.
     */
    public TradeController(GlobalInventoryManager usersInventory, GlobalWishlistManager usersWishlist) {
        this.usersInventory = usersInventory;
        this.usersWishlist = usersWishlist;
    }

    /**
     * Takes an List and a String that will be used to loan items to userB.
     * This method is similar to run, except it only accomplishes a one way trade temp/perm trade request.
     * @param itemsToTrade takes in an list of items that represent the items to loan to userB.
     * @param userA is a string that indicates the current trader (userA).
     * @param userB is a string that indicates the second trader (userB).
     */
    public TradeRequest runFromLoan(List<Item> itemsToTrade, String userA, String userB) {

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
            if (selection.equals("-1")) {
                System.out.println("this would exit");
            } else {
                switch (selection) {
                    // perm trade
                    case "1":
                        this.tradeRequestManager =
                                new TradeRequestManager("User " + userA + "wants to trade with you.", userA);
                        this.tradeRequestManager.setInfo(userA, userB, itemsToTrade, new ArrayList<>(), true);
                        this.tradeRequestManager.setDateAndPlace(userB, date, place);
                        break;
                    // temp trade
                    case "2":
                        this.tradeRequestManager =
                                new TradeRequestManager("User " + userA + "wants to trade with you.", userA);
                        this.tradeRequestManager.setInfo(userA, userB, itemsToTrade, new ArrayList<>(), false);
                        this.tradeRequestManager.setDateAndPlace(userB, date, place);
                        break;
                    default:
                        tradeMenu.invalidInput();

                }
            }

        }while(!selection.equals("1")&&!selection.equals("2"));
        tradeMenu.tradeRequestSent(userB);

        return this.tradeRequestManager.getTradeRequest();
    }

    /**
     * Presents a User friendly trade menu that'll allow people to
     * trade different items to add to their collection of items!
     * @param itemsToTradeB takes in an list of items that represent the items to trade from userB.
     * @param userA is a string that indicates the current user (userA)
     * @param numTrades the num of trades this user has made
     */
    public TradeRequest run(List<Item> itemsToTradeB, String userA, int numTrades) {
        TradeRequestManager tradeRequestMessage = null;
        String userB = itemsToTradeB.get(0).getOwnerName();

        // get date/time
        LocalDateTime date = getDateInput();

        // asks for place.
        this.tradeMenu.enterPlace();
        String place = this.input.nextLine();


        List<Item> itemsToTradeA = new ArrayList<>();
        boolean done = false;
        do {
            // have a presenter that asks for perm trade or temp trade.
            this.tradeMenu.choosePermTemp();
            String selection = this.input.nextLine();
            boolean isInteger = Pattern.matches("\\d", selection);

            // allows for integer input only.
            if (isInteger) {
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
                            break;
                        }
                        tradeRequestMessage = permTradeRequest(userA, userB, itemsToTradeB, itemsToTradeA, date, place);
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
                            done = true;
                            break;
                        }
                        tradeRequestMessage = tempTradeRequest(userA, userB, itemsToTradeB, itemsToTradeA, date, place);
                        usersInventory.getPersonInventory(userB);
                        this.tradeMenu.tradeRequestSent(userB);
                        done = true;
                        break;
                    default:
                        this.tradeMenu.invalidInput();
                        break;
                }
            } else {
                this.tradeMenu.invalidInput();
            }

        }while(!done);
        return tradeRequestMessage.getTradeRequest();
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

    private List<Item> oneOrTwoWayTrade(String tradeType, String userA, List<Item> itemsToTradeA) {
        int choice = 0;
        String choiceString;
        boolean isValid;
        switch (tradeType) {
            // one way trade
            case "1":
                return new ArrayList<>();
            // two way trade
            case "2":
                List<Item> userAInventory = usersInventory.getPersonInventory(userA);
                ArrayList<Item> itemsAvailableToTrade = (ArrayList<Item>) userAInventory;
                boolean done = false;
                // ask the user what items they want to trade, then add it into itemsToTradeA.
                while (!done) {
                    // inform the user there's no more items to trade and return back to previous screen.
                    if (itemsAvailableToTrade.isEmpty()) {
                        this.tradeMenu.noMoreItems();
                        done = true;
                    }
                    this.tradeMenu.itemsAvailableToTrade(itemsAvailableToTrade);
                    // choose the items you want to trade to the other user.
                    this.tradeMenu.itemsWantToTrade();

                    choiceString = this.input.nextLine();
                    isValid = choiceString.matches("\\d");
                    // check if the choice is valid.
                    if (!isValid) {
                        this.tradeMenu.invalidInput();
                    } else {
                        choice = Integer.parseInt(choiceString);
                    }

                    // time to exit :-)
                    if (choice == -1) {
                        done = true;
                    }

                    // add user input offered items
                    else if (choice > 0 && choice <= itemsAvailableToTrade.size()) {
                        itemsToTradeA.add(itemsAvailableToTrade.get(choice - 1));
                        itemsAvailableToTrade.remove(choice - 1);
                        tradeMenu.addedItem();
                    }
                }
        }
        return itemsToTradeA;
    }

    private TradeRequestManager templateTradeRequest(String userA, String userB, List<Item> itemsToTradeA,
                                                     List<Item> itemsToTradeB, LocalDateTime date, String place, boolean perm) {
        this.tradeRequestManager = new TradeRequestManager("User " + userA + " wants to trade with you.", userA);
        this.tradeRequestManager.setInfo(userA, userB, itemsToTradeB, itemsToTradeA, perm);
        this.tradeRequestManager.setDateAndPlace(userB, date, place);
        return this.tradeRequestManager;
    }

    private TradeRequestManager permTradeRequest(String userA, String userB, List<Item> itemsToTradeA,
                                          List<Item> itemsToTradeB, LocalDateTime date, String place) {

        // userA is current trader.
        // userB is second trader.
        this.tradeRequestManager = templateTradeRequest(userA,userB,itemsToTradeA,itemsToTradeB,date,place,true);

        return this.tradeRequestManager;
    }

    private TradeRequestManager tempTradeRequest(String userA, String userB, List<Item> itemsToTradeA,
                                          List<Item> itemsToTradeB, LocalDateTime date, String place) {

        this.tradeRequestManager = templateTradeRequest(userA, userB, itemsToTradeA, itemsToTradeB, date, place, false);

        return this.tradeRequestManager;
    }

    // TODO demo trade controller.
    // TODO findSuggestedItems after Lending suggestion is implemented by sabih
    /*
    reuse run method,
     */

//    private List<Item> findSuggestedItems(String userA, String userB) {
//        List<Item> suggestedItems = new ArrayList<Item>();
//
//        List<Item> userAInventory = this.usersInventory.getPersonInventory(userA);
//        List<String> userAWishlist = this.usersWishlist.getPersonWishlist(userB);
//
//        int inventoryAIndex = userAInventory.size()-1;
//        int wishlistBIndex = userAWishlist.size()-1;
//
//        while (inventoryAIndex > 0 && wishlistBIndex > 0) {
//            this.usersInventory.getItemFromGI(userAWishlist.get(wishlistBIndex));
//            // if the users' item is the same as the other users' item,
//            if (userAInventory.get(inventoryAIndex).equals(userAWishlist.get(wishlistBIndex))) {
//                // mark it as a suggestion for the user.
//
//            }
//            inventoryAIndex--;
//            wishlistBIndex--;
//        }
//        return suggestedItems;
//    }

}


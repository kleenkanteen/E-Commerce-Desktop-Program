package controllers;

import entities.TradeRequest;
import exceptions.IncompleteTradeException;
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
    private final GlobalInventoryManager usersInventory;


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
     * @return a TradeRequest if a successful trade is achieved. Otherwise, null is returned.
     */
    public TradeRequest runFromLoan(List<Item> itemsToTrade, String userA, String userB) throws IncompleteTradeException {

        // set the date/time
        LocalDateTime date = getDateInput();

        // asks for place.
        this.tradeMenu.enterPlace();
        String place = this.input.nextLine();

        String selection;
        TradeRequestManager tradeRequestMessage = null;
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
                        tradeRequestMessage = templateTradeRequest(userA, userB, itemsToTrade, new ArrayList<>(), date, place, true);
                        break;
                    // temp trade
                    case "2":
                        tradeRequestMessage = templateTradeRequest(userA, userB, itemsToTrade, new ArrayList<>(), date, place, false);
                        break;
                    default:
                        tradeMenu.invalidInput();
                }
            }

        }while(!selection.equals("1")&&!selection.equals("2"));
        tradeMenu.tradeRequestSent(userB);

        return tradeRequestMessage.getTradeRequest();
    }

    /**
     * Presents a User friendly trade menu that'll allow people to
     * trade different items to add to their collection of items!
     * @param itemsToTradeB takes in an list of items that represent the items to trade from userB.
     * @param userA is a string that indicates the current user (userA)
     * @param numTrades the num of trades this user has made
     * @return a TradeRequest when all the information is given by the user.
     * @throws IncompleteTradeException if a person doesn't complete a trade.
     */
    public TradeRequest run(List<Item> itemsToTradeB, String userA, int numTrades) throws IncompleteTradeException {
        TradeRequestManager tradeRequestMessage = null;
        String userB = itemsToTradeB.get(0).getOwnerName();

        // get date/time
        LocalDateTime date = getDateInput();

        // asks for place.
        this.tradeMenu.enterPlace();
        String place = this.input.nextLine();


        List<Item> itemsToTradeA = new ArrayList<>();
        boolean done = false;
        String tradeType = "";

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
                        tradeType = this.input.nextLine();
                        invalidTradeTypeChoice(tradeType);
                        itemsToTradeA = oneOrTwoWayTrade(tradeType, userA, userB, itemsToTradeA);
                        if(itemsToTradeA.isEmpty() && numTrades == 0){
                            tradeMenu.unavailableChoice();
                            break;
                        } else {
                            tradeRequestMessage = permTradeRequest(userA, userB, itemsToTradeB, itemsToTradeA, date, place);
                            this.tradeMenu.tradeRequestSent(userB);
                        }
                        done = true;
                        break;
                    // temp trade
                    case "2":
                        // ask the user if its one way or two way trade.
                        this.tradeMenu.chooseOneOrTwo();
                        tradeType = this.input.nextLine();
                        invalidTradeTypeChoice(tradeType);
                        itemsToTradeA = oneOrTwoWayTrade(tradeType, userA, userB, itemsToTradeA);
                        if(itemsToTradeA.isEmpty() && numTrades == 0){
                            tradeMenu.unavailableChoice();
                        } else {
                            tradeRequestMessage = tempTradeRequest(userA, userB, itemsToTradeB, itemsToTradeA, date, place);
                            this.tradeMenu.tradeRequestSent(userB);
                        }
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

        if (tradeRequestMessage == null) {
            throw new IncompleteTradeException();
        } else {
            return tradeRequestMessage.getTradeRequest();
        }
    }



    private LocalDateTime getDateInput() {
        // tell user that it has to be a specific format.
        LocalDateTime date = null;
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
                date = parse(dateInput, formatter);
                LocalDateTime today = LocalDateTime.now();
                // checks if the date inputted is not before today's date.
                if (date.isBefore(today)) {
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

    private void invalidTradeTypeChoice(String tradeType) {
        while(!tradeType.equals("1") && !tradeType.equals("2")){
            this.tradeMenu.invalidInput();
            this.tradeMenu.chooseOneOrTwo();
            tradeType = this.input.nextLine();
        }
    }

    private List<Item> oneOrTwoWayTrade(String tradeType, String userA, String userB, List<Item> itemsToTradeA) {
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
                ArrayList<Item> items = (ArrayList<Item>) userAInventory;
                boolean done = false;
                // ask the user what items they want to trade, then add it into itemsToTradeA.
                while (!done) {
                    // inform the user there's no more items to trade and return back to previous screen.
                    if (items.isEmpty()) {
                        this.tradeMenu.noMoreItems();
                        done = true;
                    } else {
                        // show suggested items along with the items available to trade.
                        this.tradeMenu.showSuggestedItems(findSuggestedItems(userA, userB));
                        this.tradeMenu.itemsAvailableToTrade(items);
                        this.tradeMenu.itemsWantToTrade();

                        choiceString = this.input.nextLine();
                        isValid = choiceString.matches("-?\\d");
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
                        else if (choice > 0 && choice <= items.size()) {
                            itemsToTradeA.add(items.get(choice - 1));
                            items.remove(choice - 1);
                            this.tradeMenu.addedItem();
                        }
                    }
                }
        }
        return itemsToTradeA;
    }

    private TradeRequestManager templateTradeRequest(String userA, String userB, List<Item> itemsToTradeA,
                                                     List<Item> itemsToTradeB, LocalDateTime date, String place, boolean perm) {
        TradeRequestManager tradeRequestManager = new TradeRequestManager("User " + userA + " wants to trade with you.", userA, userA, userB, itemsToTradeB, itemsToTradeA, perm);
        tradeRequestManager.setDateAndPlaceFirst(date, place);
        return tradeRequestManager;
    }

    private TradeRequestManager permTradeRequest(String userA, String userB, List<Item> itemsToTradeA,
                                          List<Item> itemsToTradeB, LocalDateTime date, String place) {

        return templateTradeRequest(userA,userB,itemsToTradeA,itemsToTradeB,date,place,true);
    }

    private TradeRequestManager tempTradeRequest(String userA, String userB, List<Item> itemsToTradeA,
                                          List<Item> itemsToTradeB, LocalDateTime date, String place) {

        return templateTradeRequest(userA, userB, itemsToTradeA, itemsToTradeB, date, place, false);
    }

    // TODO demo trade controller.
    // TODO findSuggestedItems after Lending suggestion is implemented by sabih
    /*
    reuse run method,
     */

    private List<Item> findSuggestedItems(String userA, String userB) {
        List<Item> userAInventory = this.usersInventory.getPersonInventory(userA);
        List<Item> suggestedItems = new ArrayList<Item>();
        List<String> interestedItemIDs = this.usersWishlist.getInterestedItems(userAInventory, userB);

        // converting itemIDs into Items.
        for (String itemID : interestedItemIDs) {
            suggestedItems.add(this.usersInventory.getItemFromGI(itemID));
        }

        return suggestedItems;
    }

}


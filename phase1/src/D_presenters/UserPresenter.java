package D_presenters;

import F_entities.Trade;

public class UserPresenter {

    /**
     * If an account is frozen, print the message that a freeze request has been sent.
     */
    public void requestFreezeOfUser() {
        System.out.println("A request for your account to be frozen has been sent.\n");
    }

    /**
     * Print user is frozen message.
     */
    public void userAccountFrozen() {
        System.out.println("Your account has been frozen, and you are unable to trade.\n");
    }

    /**
     * Prompt for the Unconfirmed trades menu.
     */
    public void promptUserToConfirmTrades() {
        System.out.println("Here are all of your unconfirmed trades. Please confirm any that are complete.\n");
    }

    /**
     * Prompt for the main User Menu
     */
    public void promptUserMenu() {
        System.out.println("What would you like to do today?" +
                "\n[1] Access your account information." +
                "\n[2] Browse through the global inventory." +
                "\n[3] Loan one of your items to another user." +
                "\n[4] Look at your message inbox." +
                "\n[5] Add a new item to the system." +
                "\n[6] Send admins an unfreeze request." +
                "\n[7] Exit and log out.");
    }

    /**
     * The prompt for new item prompt.
     * @param select 0 for item name input prompt, 1 for item's description prompt.
     */
    public void createNewItemPrompt(int select) {
        if(select == 0) {
            System.out.println("Enter in the item's name.");
        }
        else {
            System.out.println("Enter the item's description.");
        }
    }

    /**
     * Input error prompt.
     */
    public void inputError() {
        System.out.println("Input not understood, please try again.\n");
    }

    /**
     * Prompt for setting a new password.
     */
    public void setNewPasswordPrompt() {
        System.out.println("Enter in your new password." +
                "\nEnter 'exit' to exit.");
    }

    /**
     * Print this user's trade partner for get frequent trade partners.
     * @param tradePartner String username of trade partner
     */
    public void printUserTradePartners(String tradePartner) {
        System.out.println(tradePartner + " ");
    }

    /**
     * User Inventory Prompts.
     */
    public void userInventoryPrompts() {
        System.out.println("What would you like to do? You can: " +
                "\n [1] Remove this Item. " +
                "\n [2] Go to next Item. " +
                "\n [3] Return to previous Item. " +
                "\n [4] Exit.");
    }

    /**
     * User wishlist prompts.
     */
    public void userWishlistPrompts() {
        System.out.println("What would you like to do? You can: " +
                "\n [1] Remove this Item. " +
                "\n [2] Go to next Item. " +
                "\n [3] Return to previous Item. " +
                "\n [4] Send the owner of this item a trade offer." +
                "\n [5] Exit.");
    }

    /**
     * Reached end of user inventory.
     */
    public void endOfUserInventory() {
        System.out.println("Reached end of inventory.");
    }

    /**
     * Reached end of user wishlist.
     */
    public void endOfUserWishlist() {
        System.out.println("Reached the end of your wishlist.");
    }

    /**
     * Prints that a collection (inventory, wishlist, etc.) is empty
     * @param collection the collection name
     */
    public void isEmpty(String collection) {
        System.out.println("Your " + collection + " is empty!\n");
    }

    /**
     * Prompt for item removal
     */
    public void itemRemoved() {
        System.out.println("Item removed.\n");
    }

    /**
     * Prompt for too many incomplete trades.
     */
    public void tooManyIncompleteTrades() {
        System.out.println("You have too many incomplete trades.\n");
    }

    /**
     * Prompt for too many borrows and loans.
     * @param difference the difference of borrows - loans
     */
    public void tooManyBorrowsVLoans(int difference) {
        System.out.println("You have " + difference +  " more borrows than loans!\n");
    }

    /**
     * Prompt for too many trade offers made this week.
     */
    public void tooManyTradesThisWeek() {
        System.out.println("You made too many trade offers this week! You can trade again next week.\n");
    }

    /**
     * Prompt for no trading partners.
     */
    public void noTradingPartners() {
        System.out.println("You don't have any frequent trading partners yet!\n");
    }

    /**
     * Prompt for the user information section of user menu.
     */
    public void userMenuUserInfoPrompts() {
        System.out.println("What would you like to do with your account? " +
                "\n[1] View your trade history." +
                "\n[2] Set new password." +
                "\n[3] View your most frequent Trading Partners. " +
                "\n[4] Your three most recent trades. " +
                "\n[5] Your inventory. " +
                "\n[6] Your wishlist. " +
                "\n[7] Exit.");
    }

    /**
     * Prompt for a new item message successfully sent to admins.
     */
    public void newItemMessageSentToAdmin() {
        System.out.println("A request for an admin to approve of your item has been sent.\n");
    }

    /**
     * Prompt for confirmation of trade request sending
     * @param username the username of the other person invovled in the trade request
     */
    public void tradeRequestSent(String username) {
        System.out.println("Trade request sent to user " + username + "!\n");
    }

    /**
     * Print the trade toString.
     * @param trade the trade object to print the toString of
     */
    public void tradeToString(Trade trade) {
        System.out.println(trade.toString() + "\n");
    }

    /**
     * Prompt for user options during unconfirmed trades history menu
     */
    public void checkUnconfirmedTradesPrompts() {
        System.out.println("Can you confirm that this meeting happened? " +
                "\n[1] The meeting happened. " +
                "\n[2] The meeting did not happen.");
    }

    /**
     * Prompt for a successful operation in confirming incomplete user trades
     * @param response 0 if the trade was confirmed, any other int if the trade was marked as a failure
     */
    public void unconfirmedTradeSystemResponse(int response) {
        if(response == 0) {
            System.out.println("Trade confirmed.\n");
        }
        else {
            System.out.println("Trade marked as failed.\n");
        }
    }

    /**
     * Return the toString of an item
     * @param item the Item object to be printed
     */
    public void itemToString(String item) {
        System.out.println(item + "\n");
    }

    /**
     * Prompt to tell user that they have no recent trades.
     */
    public void noRecentTrades() {
        System.out.println("You have no recent trades!" + "\n");
    }

    /**
     * Confirmation of unfreeze request message success.
     */
    public void unfreezeRequestSent() {
        System.out.println("Admins have been sent an unfreeze request!\n");
    }

    /**
     * Confirm that the user is not frozen.
     */
    public void userNotFrozen() {
        System.out.println("You are not frozen right now.\n");
    }

    /**
     * If user attempts to loan but has not items in personal inventory
     */
    public void emptyPersonalInventoryWhileLoaning() {
        System.out.println("You have nothing to lend!\n");
    }

    /**
     * If none of the user's items are wanted by others
     */
    public void itemNotInOtherUsersWishlist() {
        System.out.println("None of your items exists in another user's wishlist. :(\n");
    }

    /**
     * Trade History Menu prompt
     */
    public void userTradeHistoryPrompts() {
        System.out.println("What would you like to see in your trade history?" +
                "\n[1] Next trade." +
                "\n[2] Previous trade." +
                "\n[3] Exit");
    }

    /**
     * Reached end of trades
     */
    public void userTradeHistoryEndOfIndex() {
        System.out.println("Reached end of user trades");
    }

    /**
     * If the global inventory is empty.
     */
    public void emptyGlobalInventory() {
        System.out.println("There are no items in the global inventory! Add some of your own! :)");
    }

    /**
     * Present prompts in loaning to other user menu
     * @param itemName the name of the selected item
     * @param userName the name of the other user
     */
    public void loanToOtherUserPrompt(String itemName, String userName) {
        System.out.println("Your offered item: " + itemName +
                "\nRecipient User: " + userName +
                "\nDo you wish to finish sending this user a trade offer? " +
                "\n[1] Yes" +
                "\n[2] No, and take me back to the main menu.");
    }

    /**
     * Notify user on return to main menu.
     */
    public void returnToMainMenu() {
        System.out.println("Returning to main menu.");
    }
}

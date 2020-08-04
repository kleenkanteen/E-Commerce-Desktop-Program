package presenters;

import entities.Trade;

public class UserPresenter {

    /**
     * If an account is frozen, print the message that a freeze request has been sent.
     */
    public String requestFreezeOfUser() {
        return "A request for your account to be frozen has been sent.\n";
    }

    /**
     * Print user is frozen message.
     */
    public String userAccountFrozen() {
        return "Your account has been frozen, and you are unable to trade.\n";
    }

    /**
     * Prompt for the Unconfirmed trades menu.
     */
    public String promptUserToConfirmTrades() {
        return "Here are all of your unconfirmed trades. Please confirm any that are complete.\n";
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
                "\n[7] Send a private message to another user. " +
                "\n[8] Exit and log out.");
    }

    public String userMenuPromptAccountInfo() {
        return "Access your account information";
    }

    public String userMenuPromptGlobalInventory() {
        return "Browse through the global inventory";
    }

    public String userMenuPromptLoanMenu() {
        return "Loan one of your items to another user";
    }

    public String userMenuPromptMessageMenu() {
        return "Look at your message inbox";
    }

    public String userMenuPromptNewItem() {
        return "Add a new item to the system";
    }

    public String userMenuPromptUnfreeze() {
        return "Send admins an unfreeze request";
    }

    public String userMenuPromptPrivateMessage() {
        return "Send a private message to another user";
    }

    public String userMenuPromptLogout() {
        return "Exit and log out";
    }

    /**
     * The prompt for new item prompt.
     * @param select 0 for item name input prompt, 1 for item's description prompt.
     */
    public String createNewItemPrompt(int select) {
        if(select == 0) {
            return "Enter in the item's name.";
        }
        else {
            return "Enter the item's description.";
        }
    }

    /**
     * Input error prompt.
     */
    public String inputError() { return "Input not understood, please try again.\n"; }

    /**
     * Prompt for setting a new password.
     */
    public String setNewPasswordPrompt() {
        return "Enter in your new password.";
    }

    /**
     * Print this user's trade partner for get frequent trade partners.
     * @param tradePartner String username of trade partner
     */
    public String printUserTradePartners(String tradePartner) { return tradePartner + " "; }

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

    public String menuPromptRemove() {
        return "Remove this Item";
    }

    public String menuPromptNext() {
        return "Go to next item";
    }

    public String menuPromptPrevious() {
        return "Return to the previous item";
    }

    public String menuPromptExit() {
        return "Exit";
    }

    /**
     * User wishlist prompts.
     */
    public void userWishlistPrompts() {
        System.out.println("What would you like to do? You can: " +
                "\n [1] Remove this Item. " +
                "\n [2] Go to next Item. " +
                "\n [3] Return to previous Item. " +
                "\n [4] Send the owner of this item a trade offer (can only ask to borrow this single item!)." +
                "\n [5] Exit.");
    }

    public String wishlistPromptTradeOffer() {
        return "Send a trade offer to this user";
    }

    /**
     * Reached end of user inventory.
     */
    public String endOfUserInventory() {
        return "Reached end of inventory.";
    }

    /**
     * Reached end of user wishlist.
     */
    public String endOfUserWishlist() {
        return "Reached the end of your wishlist.";
    }

    /**
     * Prints that a collection (inventory, wishlist, etc.) is empty
     * @param collection the collection name
     */
    public String isEmpty(String collection) {
        return "Your " + collection + " is empty!\n";
    }

    /**
     * Prompt for item removal
     */
    public String itemRemoved() {
        return "Item removed.\n";
    }

    /**
     * Prompt for too many incomplete trades.
     */
    public String tooManyIncompleteTrades() {
        return "You have too many incomplete trades.\n";
    }

    /**
     * Prompt for too many borrows and loans.
     * @param difference the difference of borrows - loans
     */
    public String tooManyBorrowsVLoans(int difference) {
        return "You have " + difference +  " more borrows than loans!\n";
    }

    /**
     * Prompt for too many trade offers made this week.
     */
    public String tooManyTradesThisWeek() {
        return "You made too many trade offers this week! You can trade again next week.\n";
    }

    /**
     * Prompt for no trading partners.
     */
    public String noTradingPartners() {
        return "You don't have any frequent trading partners yet!\n";
    }

    /**
     * Prompt for the user information section of user menu.
     */
    public void userMenuUserInfoPrompts() {
        System.out.println("What would you like to do with your account? " +
                "\n[1] View your trade history." +
                "\n[2] Set new password." +
                "\n[3] View your three most frequent Trading Partners. " +
                "\n[4] Your three most recent trades. " +
                "\n[5] Your inventory. " +
                "\n[6] Your wishlist. " +
                "\n[7] Exit.");
    }

    public String accountInfoPromptTradeHistory() {
        return "View your trade history";
    }

    public String accountInfoPromptPassword() {
        return "Set new password";
    }

    public String accountInfoPromptTradingPartners() {
        return "View your three most frequent trading partners";
    }

    public String accountInfoPromptRecentTrades() {
        return "View your three most recent trades";
    }

    public String accountInfoPromptInventory() {
        return "View your inventory";
    }

    public String accountInfoPromptWishlist() {
        return "View your wishlist";
    }

    /**
     * Prompt for a new item message successfully sent to admins.
     */
    public String newItemMessageSentToAdmin() {
        return "Item approval request sent. You can exit now.\n";
    }

    /**
     * Prompt for confirmation of trade request sending
     * @param username the username of the other person invovled in the trade request
     */
    public String tradeRequestSent(String username) {
        return "Trade request sent to user " + username + "!\n";
    }

    /**
     * Print the trade toString.
     * @param trade the trade object to print the toString of
     */
    public String tradeToString(Trade trade) {
        return trade.toString() + "\n";
    }

    /**
     * Prompt for user options during unconfirmed trades history menu
     */
    public void checkUnconfirmedTradesPrompts() {
        System.out.println("Can you confirm that this meeting happened? " +
                "\n[1] The meeting happened. " +
                "\n[2] The meeting did not happen.");
    }

    public String checkUnconfirmedTradesPrompt() {
        return "Can you confirm that this meeting happened?";
    }

    public String confirmMeetingPrompt() {
        return "This meeting happened";
    }

    public String denyMeetingPrompt() {
        return "This meeting did not happen";
    }

    /**
     * Prompt for a successful operation in confirming incomplete user trades
     * @param response 0 if the trade was confirmed, any other int if the trade was marked as a failure
     */
    public String unconfirmedTradeSystemResponse(int response) {
        if(response == 0) {
            return "Trade confirmed.\n";
        }
        else {
            return "Trade marked as failed.\n";
        }
    }

    /**
     * Return the toString of an item
     * @param item the Item object to be printed
     */
    public String itemToString(String item) {
        return item + "\n";
    }

    /**
     * Prompt to tell user that they have no recent trades.
     */
    public String noRecentTrades() {
        return "You have no recent trades!" + "\n";
    }

    /**
     * Confirmation of unfreeze request message success.
     */
    public String unfreezeRequestSent() {
        return "Admins have been sent an unfreeze request!\n";
    }

    /**
     * Confirm that the user is not frozen.
     */
    public String userNotFrozen() {
        return "You are not frozen right now.\n";
    }

    /**
     * If user attempts to loan but has not items in personal inventory
     */
    public String emptyPersonalInventoryWhileLoaning() {
        return "You have nothing to lend!\n";
    }

    /**
     * If none of the user's items are wanted by others
     */
    public String itemNotInOtherUsersWishlist() {
        return "None of your items exists in another user's wishlist. :(\n";
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

    public String tradeMenuPrompt() {
        return "What would you like to see in your trade history?";
    }

    public String nextTradePrompt() {
        return "Next Trade";
    }

    public String previousTradePrompt() {
        return "Previous Trade";
    }

    /**
     * Reached end of trades
     */
    public String userTradeHistoryEndOfIndex() {
        return "Reached end of user trades";
    }

    /**
     * If the global inventory is empty.
     */
    public String emptyGlobalInventory() {
        return "There are no items in the global inventory! Add some of your own! :)";
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

    public String userLoanPromptOfferedItemLabel() {
        return "Your offered item:";
    }

    public String userLoanPromptRecipientLabel() {
        return "Recipient User:";
    }

    public String userLoanPromptConfirm() {
        return "Confirm.";
    }

    public String userLoanPromptCancel() {
        return "Cancel.";
    }

    public String userPrivateMessageConfirmation() {
        return "Message sent. It is safe to exit.";
    }

    /**
     * Notify user on return to main menu.
     */
    public String returnToMainMenu() {
        return "Returning to main menu.";
    }

    public String userMessagePrompt() {
        return "Who would you like to send a message to?";
    }

    public String userMessagePromptSecundus() {
        return "What would you like to say?";
    }

    public String invalidUsername() {
        return "This is not a valid username, please try again.";
    }

    public String tradeOfferCreationCancelled() {
        return "Trade offer creation cancelled.";
    }
}

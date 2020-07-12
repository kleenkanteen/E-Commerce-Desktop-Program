package D_presenters;

import F_entities.Trade;

public class UserPresenter {

    public void requestFreezeOfUser() {
        System.out.println("A request for your account to be frozen has been sent.\n");
    }

    public void userAccountFrozen() {
        System.out.println("Your account has been frozen, and you are unable to trade.\n");
    }

    public void promptUserToConfirmTrades() {
        System.out.println("Here are all of your unconfirmed trades. Please confirm any that are complete.\n");
    }

    public void promptUserMenu() {
        System.out.println("What would you like to do today?" +
                "\n[1] Access your account information." +
                "\n[2] Browse through the global inventory." +
                "\n[3] Browse through the global wishlist (???) " +
                "\n[4] Look at your message inbox." +
                "\n[5] Add a new item to the system." +
                "\n[6] Send admins an unfreeze request." +
                "\n[7] Exit and log out.");
    }

    public void createNewItemPrompt(int select) {
        if(select == 0) {
            System.out.println("Enter in the item's name.");
        }
        else {
            System.out.println("Enter the item's description.");
        }
    }

    public void inputError() {
        System.out.println("Input not understood, please try again.\n");
    }

    public void setNewPasswordPrompt() {
        System.out.println("Enter in your new password." +
                "\nEnter 'exit' to exit.");
    }

    public void printUserTradePartners(String tradePartner) {
        System.out.println(tradePartner + " ");
    }

    public void userInventoryPrompts() {
        System.out.println("What would you like to do? You can: " +
                "\n [1] Remove this Item. " +
                "\n [2] Go to next Item. " +
                "\n [3] Return to previous Item. " +
                "\n [4] Exit.");
    }

    public void userWishlistPrompts() {
        System.out.println("What would you like to do? You can: " +
                "\n [1] Remove this Item. " +
                "\n [2] Go to next Item. " +
                "\n [3] Return to previous Item. " +
                "\n [4] Send the owner of this item a trade offer." +
                "\n [5] Exit.");
    }

    public void endOfUserInventory() {
        System.out.println("Reached end of inventory.\n");
    }

    public void endOfUserWishlist() {
        System.out.println("Reached the end of your wishlist.\n");
    }

    public void isEmpty(String collection) {
        System.out.println("Your " + collection + " is empty!\n");
    }

    public void itemRemoved() {
        System.out.println("Item removed.\n");
    }

    public void tooManyIncompleteTrades() {
        System.out.println("You have too many incomplete trades.\n");
    }

    public void tooManyBorrowsVLoans(int difference) {
        System.out.println("You have " + difference +  " more borrows than loans!\n");
    }

    public void tooManyTradesThisWeek() {
        System.out.println("You made too many trade offers this week! You can trade again next week.\n");
    }

    public void noTradingPartners() {
        System.out.println("You don't have any frequent trading partners yet!\n");
    }

    public void userMenuUserInfoPrompts() {
        System.out.println("What would you like to do with your account?" +
                "\n[1] Set new password." +
                "\n[2] View your most frequent Trading Partners. " +
                "\n[3] Your three most recent trades. " +
                "\n[4] Your inventory. " +
                "\n[5] Your wishlist. " +
                "\n[6] Exit.");
    }

    public void newItemMessageSentToAdmin() {
        System.out.println("A request for an admin to approve of your item has been sent.\n");
    }

    public void tradeRequestSent(String username) {
        System.out.println("Trade request sent to user " + username + "!\n");
    }

    public void tradeToString(Trade trade) {
        System.out.println(trade.toString() + "\n");
    }

    public void checkUnconfirmedTradesPrompts() {
        System.out.println("Can you confirm that this meeting happened? " +
                "\n[1] The meeting happened. " +
                "\n[2] The meeting did not happen.");
    }

    public void unconfirmedTradeSystemResponse(int response) {
        if(response == 0) {
            System.out.println("Trade confirmed.\n");
        }
        else {
            System.out.println("Trade marked as failed.\n");
        }
    }

    public void itemToString(String item) {
        System.out.println(item + "\n");
    }

    public void noRecentTrades() {
        System.out.println("You have no recent trades!" + "\n");
    }

    public void unfreezeRequestSent() {
        System.out.println("Admins have been sent an unfreeze request!\n");
    }

    public void userNotFrozen() {
        System.out.println("You are not frozen right now.\n");
    }
}

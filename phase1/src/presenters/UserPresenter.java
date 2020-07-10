package presenters;

public class UserPresenter {

    public void requestFreezeOfUser() {
        System.out.println("A request for your account to be frozen has been sent.");
    }

    public void userAccountFrozen() {
        System.out.println("Your account has been frozen, and you are unable to trade.");
    }

    public void promptUserToConfirmTrades() {
        System.out.println("Here are all of your unconfirmed trades. Please confirm any that are complete.");
    }

    public void userMaxNumOfTrades() {
        System.out.println("You have the reached the maximum number of trades " +
                "offers that you can send this week. \n You will have to wait until next week to send more.");
    }

    public void promptUserMenu() {
        System.out.println("What would you like to do today?" +
                "\n[1] Access your account information." +
                "\n[2] Browse through the global inventory." +
                "\n[3] Browse through the global wishlist (???) " +
                "\n[4] Look at your message inbox." +
                "\n[5] Add a new item to the system." +
                "\n[6] Exit and log out.");
    }

    public void createNewItemPrompt(int select) {
        if(select == 0) {
            System.out.println("Enter in the item's name");
        }
        else {
            System.out.println("Enter the item's description");
        }
    }

    public void inputError() {
        System.out.println("Input not understood, please try again.");
    }

    public void setNewPasswordPrompt() {
        System.out.println("Enter in your new password");
    }

    public void printUserTradePartners(String[] tradingPartners) {
        for(String user : tradingPartners) {
            System.out.println("\n" + user);
        }
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
        System.out.println("Reached end of inventory.");
    }

    public void endOfUserWishlist() {
        System.out.println("Reached the end of your wishlist.");
    }

    public void isEmpty(String collection) {
        System.out.println("Your " + collection + " is empty!");
    }

    public void itemRemoved() {
        System.out.println("Item removed.");
    }

    public void tooManyIncompleteTrades() {
        System.out.println("You have too many incomplete trades.");
    }

    public void tooManyBorrowsVLoans(int difference) {
        System.out.println("You have " + difference +  " more borrows than loans!");
    }

    public void tooManyTradesThisWeek() {
        System.out.println("You made too many trade offers this week! You can trade again next week.");
    }

    public void noTradingPartners() {
        System.out.println("You don't have any frequent trading partners yet!");
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
        System.out.println("A request for an admin to approve of your item has been sent");
    }
}

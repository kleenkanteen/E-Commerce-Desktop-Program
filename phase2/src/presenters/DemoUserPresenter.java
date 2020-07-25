package presenters;

import use_cases.DemoUserManager;

public class DemoUserPresenter {
    private DemoUserManager demousermanager;

    public DemoUserPresenter(DemoUserManager demousermanager) {
        this.demousermanager = demousermanager;
    }

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

    public void noAccess(){
        System.out.println("Sorry, Demo user you do not have access to this functionality, please create an account " +
                "to use our program");
    }

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
     * Input error prompt.
     */
    public void inputError() {
        System.out.println("Input not understood, please try again.\n");
    }

    public void enterItem(){
        System.out.println("Enter in the item's name.");
    }

    public void enterItemDescription(){
        System.out.println("Enter the item's description.");
    }

    public void adminApproval(){
        System.out.println("In a standard account, this item would be sent to the admins for approval, " +
                "but we'll be nice and simply add it to your account :)");
    }

}

package presenters;

import use_cases.DemoUserManager;
import use_cases.GlobalInventoryManager;

public class DemoUserPresenter {
    private DemoUserManager demousermanager;
    private GlobalInventoryManager globalInventoryManager;

    public DemoUserPresenter(DemoUserManager demousermanager, GlobalInventoryManager globalInventoryManager) {
        this.demousermanager = demousermanager;
        this.globalInventoryManager = globalInventoryManager;
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
        System.out.println("Sorry, as a demo user you do not have access to this functionality, \n" +
                "please create a standard user account to have full access to this program.");
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
        System.out.println("Reached the end of your inventory.");
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

    public void passwordInput() {
        System.out.println("Enter your new password:");
    }

    public void itemToString(String itemString) {
        System.out.println(itemString + "\n");
    }

    public void itemRemoved() {
        System.out.println("Item removed.");
    }

    /**
     * Prints the items in screen in pages, each page contains maximum of 10 items
     * @param page number of page in global inventory
     */
    public void printpage(int page){
        String items = "Choose your option below: \n";
        if (!globalInventoryManager.generatePage(page).isEmpty()){
            for (int k = 0; k < globalInventoryManager.generatePage(page).size(); k++) {
                items += "[" + k + "] " + globalInventoryManager.generatePage(page).get(k).getName() + "\n" ;
            }
            System.out.println(items + "[n] next page \n[p] previous page \n[e] exit");
        }
    }

    /**
     * prints this page is empty
     */
    public void emptyPage(){
        System.out.println("this page is empty");
    }

    /**
     * user is at the first page of the global inventory
     */
    public void atfirst(){
        System.out.println("It was the first page");
    }

    public void enterDate(){
        System.out.println("Enter the date and time (in 24-hour format!!!) in which the meeting occurs " +
                "(format: YYYY-MM-DD HH:MM): )");
    }

    public void enterPlace(){
        System.out.println("Enter the place of the meeting: ");
    }

    public void choosePermTemp() {
        System.out.println("What trade would you like to complete today?" +
                "\n[1] Start a permanent transaction with a user." +
                "\n[2] Start a temporary transaction with a user.");
    }

    /**
     * Presenter for choosing one way trade or two way trade.
     */
    public void chooseOneOrTwo() {
        System.out.println("What type of trade is it? \n" +
                "\n[1] One way trade." +
                "\n[2] Two way trade.");
    }

    public void createAcc(){
        System.out.println("Please create an account to request a real trade");
    }
}

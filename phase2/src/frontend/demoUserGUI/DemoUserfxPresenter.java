package frontend.demoUserGUI;

import entities.Item;

public class DemoUserfxPresenter {

    /**
     * constructor for DemoUserfxPresenter
     */
    public DemoUserfxPresenter() {
    }

    /**
     * prints no access String for demo
     * @return String prints on screen
     */
    public String noAccess(){
        return "Sorry, as a demo user you do not have access to this functionality, \n" +
                "please create a standard user account to have full access to this program.";
    }

    /**
     * prints global invenotyr is empty
     * @return String prints on screen
     */
    public String emptyglobalinventory(){
        return "The global inventory is empty";
    }

    /**
     * set up button text in demoUserMenu
     * @return button text for access account infomation
     */
    public String accInfo(){
        return "Access your account information";
    }

    /**
     * set up button text in demoUserMenu
     * @return button text for browse global inventory
     */
    public String browse(){
        return "Browse through the global inventory";
    }

    /**
     * set up button text in demoUserMenu
     * @return button text for loan
     */
    public String loan(){
        return "Loan one of your items to another user";
    }

    /**
     * set up button text in demoUserMenu
     * @return button text for message inbox
     */
    public String inbox(){
        return "Look at your message inbox";
    }

    /**
     * set up button text in demoUserMenu
     * @return button text for adding Item
     */
    public String addItem(){
        return "Add a new item to the system";
    }

    /**
     * set up button text in demoUserMenu
     * @return button text for send admin unfreeze request
     */
    public String unfreeze(){
        return "Send admins an unfreeze request";
    }

    /**
     * set up button text in demoUserMenu
     * @return button text for sending a private message
     */
    public String pm (){
        return "Send a private message to another user";
    }

    /**
     * set up button text in demoUserMenu
     * @return button text for exit
     */
    public String exit(){
        return "Exit and log out";
    }

    /**
     * ask user to enter item name
     * @return string ask user to enter item name
     */
    public String enterItemName(){
        return "Enter in the item's name.";
    }

    /**
     * ask user to enter Item description
     * @return string ask user to enter the item description
     */
    public String enterItemDescription(){
        return "Enter the item's description.";
    }

    /**
     * set up button for user to confirm
     * @return String confirm
     */
    public String Confirm() {
        return "Confirm.";
    }

    /**
     * setup button for user to cancel
     * @return String cancel
     */
    public String Cancel() {
        return "Cancel.";
    }

    /**
     * print the message when demo user trying to add item into the system
     * @return String message for demo user
     */
    public String demoaddingitem(){
        return "In a standard account, this item would be sent to the admins for approval, " +
                "but we'll be nice\nand simply add it to your account :)..." +
                "...but your new item will not be visible on the global inventory. :( ";
    }

    /**
     * set up button for demouser to view trade history
     * @return view your trade history
     */
    public String accountInfoPromptTradeHistory() {
        return "View your trade history";
    }

    /**
     * setup button for demouser to set new password
     * @return set new password
     */
    public String accountInfoPromptPassword() {
        return "Set new password";
    }

    /**
     * set up button for demo user to view frequent trading partners
     * @return View your three most frequent trading partners
     */
    public String accountInfoPromptTradingPartners() {
        return "View your three most frequent trading partners";
    }

    /**
     * set up button for demo user to view three most recent trades
     * @return View your tree most recent trades
     */
    public String accountInfoPromptRecentTrades() {
        return "View your three most recent trades";
    }

    /**
     * set up button for demo user to view inventory
     * @return View your inventory
     */
    public String accountInfoPromptInventory() {
        return "View your inventory";
    }

    /**
     * set up button for demo user to view wishlist
     * @return View your wishlist
     */
    public String accountInfoPromptWishlist() {
        return "View your wishlist";
    }

    /**
     * set up button for exiting
     * @return Exit
     */
    public String menuPromptExit() {
        return "Exit";
    }

    /**
     * ask user to create an account
     * @return Please create an account to request a real trade.
     */
    public String createAcc(){
        return "Please create an account to request a real trade.";
    }

    /**
     * setup TableView in globalInventoryMenu
     * @return Item Name
     */
    public String itemName(){
        return "Item Name";
    }

    /**
     * setup TableView in globalInventory
     * @return Item Owner
     */
    public String itemOwner(){
        return "Item Owner";
    }

    /**
     * setup TableView in globalInventoryMenu
     * @return Item Description
     */
    public String itemDescription(){
        return "Item Description";
    }

    /**
     * setup button in globalInventoryMenu
     * @return Add to wish-list
     */
    public String addToWishlist(){
        return"Add to wish-list";
    }

    /**
     * setup button in globalInventoryMenu
     * @return Send a trade request
     */
    public String sendTradeReqeust(){
        return "Send a trade request";
    }

    /**
     * prints message on screen in globalInventory when user did not select any item
     * @return No item selected
     */
    public String noItemSelected(){
        return "No item selected";
    }

    /**
     * prints on screen when user added item to their wish-list
     * @param item the added item
     * @return a message saying items is added
     */
    public String addedToWishlist(Item item){
        return item.getName() + " is added to your wish-list";
    }

    /**
     * ask what to do with the item selected by user
     * @param item item selected
     * @return a message asking what to do
     */
    public String whatToDo(Item item){
        return item.getName() + " is selected! \nWhat do you want to do with this item?";
    }

    /**
     * setup remove button
     * @return Remove
     */
    public String menuPromptRemove() {
        return "Remove";
    }


    /**
     * prints end of user Inventory
     * @return Reached end of inventory.
     */
    public String endOfUserInventory() {
        return "Reached end of inventory.";
    }

    /**
     * prints end of user inventory
     * @return Reached end of inventory
     */
    public String endOfUserWishlist(){
        return "Reached end of inventory";
    }

    /**
     * Prompt for item removal
     * @return Item removed
     */
    public String itemRemoved() {
        return "Item removed.\n";
    }

    /**
     * prints user inventory is empty
     * @return Your inventory is empty!
     */
    public String inventoryIsEmpty() {
        return "Your inventory is empty!\n";
    }

    /**
     * prints user wish-list is empty
     * @return Your wishlist is empty
     */
    public String wishlistIsEmpty() {
        return "Your wishlist is empty!\n";
    }

    /**
     * prints something went wrong, please try again
     * @return Something went wrong, please try again
     */
    public String error(){
        return "Something went wrong, please try again";
    }

}

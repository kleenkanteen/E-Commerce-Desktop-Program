package frontend.DemoUserGUI;

import entities.Item;

public class DemoUserfxPresenter {

    public DemoUserfxPresenter() {
    }

    public String noAccess(){
        return "Sorry, as a demo user you do not have access to this functionality, \n" +
                "please create a standard user account to have full access to this program.";
    }

    public String emptyglobalinventory(){
        return "The global inventory is empty";
    }
    public String accInfo(){
        return "Access your account information";
    }

    public String browse(){
        return "Browse through the global inventory";
    }

    public String loan(){
        return "Loan one of your items to another user";
    }
    public String inbox(){
        return "Look at your message inbox";
    }

    public String addItem(){
        return "Add a new item to the system";
    }

    public String unfreeze(){
        return "Send admins an unfreeze request";
    }

    public String pm (){
        return "Send a private message to another user";
    }

    public String exit(){
        return "Exit and log out";
    }

    public String enterItemName(){
        return "Enter in the item's name.";
    }

    public String enterItemDescription(){
        return "Enter the item's description.";
    }

    public String Confirm() {
        return "Confirm.";
    }

    public String Cancel() {
        return "Cancel.";
    }

    public String demoaddingitem(){
        return "In a standard account, this item would be sent to the admins for approval, " +
                "but we'll be nice\nand simply add it to your account :)..." +
                "...but your new item will not be visible on the global inventory. :( ";
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

    public String menuPromptExit() {
        return "Exit";
    }

    public String createAcc(){
        return "Please create an account to request a real trade.";
    }

    public String itemName(){
        return "Item Name";
    }

    public String itemOwner(){
        return "Item Owner";
    }
    public String itemDescription(){
        return "Item Description";
    }
    public String addToWishlist(){
        return"Add to wish-list";
    }
    public String sendTradeReqeust(){
        return "Send a trade request";
    }

    public String noItemSelected(){
        return "No item selected";
    }

    public String addedToWishlist(Item item){
        return item.getName() + " is added to your wish-list";
    }
    public String whatToDo(Item item){
        return item.getName() + " is selected! \nWhat do you want to do with this item?";
    }
}

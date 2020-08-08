package frontend.GlobalInventoryGUI;

import entities.Item;

public class GlobalInventoryMenuPresenter {

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
     * set up button for exiting
     * @return Exit
     */
    public String menuPromptExit() {
        return "Exit";
    }

    /**
     * prints when user account is frozen and cannot trade
     * @return Your account is frozen, you cannot trade
     */
    public String FrozenAcc(){
        return "Your account is frozen, you cannot trade";
    }

    /**
     * prints when user select their own item
     * @return This is your own item
     */
    public String ownItem(){
        return "This is your own item";
    }

    /**
     * prints when user adding same item in their wish-list
     * @return This item is already in you wish-list
     */
    public String alreadyHave(){
        return "This item is already in you wish-list";
    }

    /**
     * prints something went wrong, please try again
     * @return Something went wrong, please try again
     */
    public String error(){
        return "Something went wrong, please try again";
    }




    public String userItemLabel(Item item){
        return item.getOwnerName() + "'s inventory";
    }

    public String itemSelected(){
        return "Items selected";
    }

    public String selectItem(Item item){
        return "You are Trading with " + item.getOwnerName() + "\n please select the items you want to trade";
    }

    public String select(){
        return "Select";
    }

    public String remove(){
        return "Remove";
    }

    public String trade(){
        return "trade";
    }
}

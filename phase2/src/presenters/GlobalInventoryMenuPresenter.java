package presenters;

import entities.Item;

public class GlobalInventoryMenuPresenter {

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

    public String menuPromptExit() {
        return "Exit";
    }

    /**
     * prints when user account is frozen and cannot trade
     */
    public String FrozenAcc(){
        return "Your account is frozen, you cannot trade";
    }

    /**
     * prints when user select their own item
     */
    public String ownItem(){
        return "This is your own item";
    }

    /**
     * prints when user adding same item in their wish-list
     */
    public String alreadyHave(){
        return "This item is already in you wish-list";
    }
}

package presenters;

import entities.Item;
import use_cases.GlobalInventoryManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class GlobalInventoryPresenter implements  Iterator<String>{
    private List<String> properties = new ArrayList<>();
    public GlobalInventoryManager globalInventoryManager;
    private int current = 0;

    /**
     * Constructor for GlobalInventoryPresenter
     * @param globalInventoryManager the GlobalInventoryManager
     */
    public GlobalInventoryPresenter(GlobalInventoryManager globalInventoryManager) {
        this.globalInventoryManager = globalInventoryManager;
    }

    /**
     * check if there is more prompts need to print
     * @return true if there is more prompts need to print
     */
    @Override
    public boolean hasNext() {
        return current < properties.size();
    }

    /**
     * Prints next prompts
     * @return next promts as a string
     */
    @Override
    public String next() {
        String res;
        try {
            res = properties.get(current);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
        current += 1;
        return res;
    }

    /**
     * Prints the items in screen in pages, each page contains maximum of 10 items
     * @param page number of page in global inventory
     */
    public void printpage(int page){
        StringBuilder items = new StringBuilder();
        items.append("Choose your option below: \n");
        if (!globalInventoryManager.generatePage(page).isEmpty()){
            for (int k = 0; k < globalInventoryManager.generatePage(page).size(); k++) {
                String item = "[" + k + "] " + globalInventoryManager.generatePage(page).get(k).getName() + "\n" ;
                items.append(item);
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
     * prints option for user to add the item to wish-list or request a trade
     * @param item the item
     */
    public void addToWishlishandTradeRequest(Item item){
        System.out.println("Choose your option below: \n[1] Add this item (" + item.getName() +
                ") to your wish-list \n" +
                "[2] Request a trade");
    }

    /**
     * prints option for user to add the item to wish-list
     * @param item the item
     */
    public void addToWishlist(Item item){
        System.out.println("Choose your option below: \n [1] Add this item (" + item.getName() + ") to your wish-list\n");
    }

    /**
     * prints something went wrong, please try again
     */
    public void error(){
        System.out.println("Something went wrong, please try again");
    }

    /**
     * shows the item successfully added to wish-list
     * @param item the item
     */
    public void addedToWishlist(Item item){
        System.out.println(item.getName() +  " is added to your wishlist");
    }

    /**
     * Invalid input
     */
    public void invalid(){
        System.out.println("Invalid input, please try again");
    }

    /**
     * user is at the first page of the global inventory
     */
    public void atfirst(){
        System.out.println("It was the first page");
    }

    /**
     * prints when user select their own item
     */
    public void ownItem(){
        System.out.println("This is your own item");
    }

    /**
     * prints when user adding same item in their wish-list
     */
    public void alreadyHave(){
        System.out.println("This item is already in you wish-list");
    }

    /**
     * prints when user account is frozen and cannot trade
     */
    public void FrozenAcc(){
        System.out.println("Your account is frozen, you cannot trade");
    }

    public void traderItem(Item item){
        // owner inventory
        StringBuilder items = new StringBuilder();
        items.append("What other item do you want trade with the this user?\n");
        if (!globalInventoryManager.getPersonInventory(item.getOwnerName()).isEmpty()){
            for (int k = 0; k < globalInventoryManager.getPersonInventory(item.getOwnerName()).size(); k++) {
                    String i = "[" + k + "] " + globalInventoryManager.
                            getPersonInventory(item.getOwnerName()).get(k).getName() + "\n" ;
                    items.append(i);
            }
            System.out.println(items + "[e] exit");
        }
    }

    public void seeTraderInventory(){
        System.out.println("Do you want to see this user's inventory to trade more items?\n[1] Yes\n[2] No" );
    }

    public void alreadySelected(){
        System.out.println("You already selected this item");
    }

    public void incompleteTrade(){
        System.out.println("You did not complete the trade");
    }

    public void noMoreItem(){
        System.out.println("This user does not have other items to trade. Please exit and continue with your trade!"+
                "\n[e] exit");
    }
    public void addedToTrade(Item item){
        System.out.println(item.getName() + " is added to trade request ");
    }
}

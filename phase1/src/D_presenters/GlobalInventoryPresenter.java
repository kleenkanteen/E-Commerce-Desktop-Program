package D_presenters;

import F_entities.Item;
import E_use_cases.GlobalInventoryManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class GlobalInventoryPresenter implements  Iterator<String>{
    private List<String> properties = new ArrayList<>();
    public GlobalInventoryManager gim;
    private int current = 0;

    /**
     * Constructor for GlobalInventoryPresenter
     * @param gim the GlobalInventoryManager
     */
    public GlobalInventoryPresenter(GlobalInventoryManager gim) {
        this.gim = gim;
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
        String items = "Choose your option below: \n";
        if (!gim.generatePage(page).isEmpty()){
            for (int k = 0; k < gim.generatePage(page).size(); k++) {
                items += "[" + k + "] " + gim.generatePage(page).get(k).getName() + "\n" ;
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
        System.out.println("Choose your option below: \n[1] Add this item (" + item.getName() + ") to your wish-list \n" +
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
}

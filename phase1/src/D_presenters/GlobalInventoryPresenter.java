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

    public GlobalInventoryPresenter(GlobalInventoryManager gim) {

        this.gim = gim;
    }

    @Override
    public boolean hasNext() {
        return current < properties.size();
    }

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

    public void printpage(int page){
        String items = "Choose your option below: \n";
        if (!gim.generatePage(page).isEmpty()){
            for (int k = 0; k < gim.generatePage(page).size(); k++) {
                items += "[" + k + "] " + gim.generatePage(page).get(k).getName() + "\n" ;
            }
            System.out.println(items + "[n] next page \n[p] previous page \n[e] exit");
        }
    }

    public void emptyPage(){
        System.out.println("this page is empty");
    }

    public void addToWishlishandTradeRequest(Item item){
        System.out.println("Choose your option below: \n [1] Add this item (" + item.getName() + ") to your wish-list \n" +
                "[2] Request a trade");
    }
    public void addToWishlist(Item item){
        System.out.println("Choose your option below: \n [1] Add this item (" + item.getName() + ") to your wish-list\n");
    }
    public void enter(){
        System.out.println("Choose your option below: \n [1] Continue \n [2] Exit");
    }

    public void error(){
        System.out.println("Something went wrong, please try again");
    }

    public void addedToWishlist(Item item){
        System.out.println(item.getName() +  "is added to your wishlist");
    }

    public void invalid(){
        System.out.println("Invalid input, please try again");
    }

    public void atfirst(){
        System.out.println("It was the first page");
    }

    public void ownItem(){
        System.out.println("This is your own item");
    }

    public void alreadyHave(){
        System.out.println("This item is already in you wish-list");
    }
    public void FrozenAcc(){
        System.out.println("Your account is frozen, you cannot trade");
    }
}

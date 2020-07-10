package presenters;

import entities.Item;
import use_cases.GlobalInventoryManager;

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
                items += "[" + k + "]" + gim.generatePage(page).get(k).getName() + "\n" ;
            }
            properties.add(items + "go to next page by typing 'next' or go to previous page by typing 'previous");
        }
        else properties.add("this page is empty");

    }

    public void addToWishlishandTradeRequest(Item item){
        System.out.println("Choose your option below: \n [1] Add this item (" + item.getName() + ") to your wish-list \n" +
                "[2] Request a trade");
    }
    public void addToWishlist(Item item){
        System.out.println("\"Choose your option below: \\n [1] Add this item (\" + item.getName() + \") to your wish-list\"");
    }
    public void enter(){
        System.out.println("Choose your option below: \n [1] Continue \n [2] Exit");
    }

    public void error(){
        System.out.println("Something went wrong, please try again");
    }
}

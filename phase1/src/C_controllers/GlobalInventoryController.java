package C_controllers;

import D_presenters.GlobalInventoryPresenter;
import E_use_cases.GlobalInventoryManager;
import E_use_cases.UserManager;
import F_entities.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GlobalInventoryController {
    public void run(GlobalInventoryManager gim, UserManager UM, String user) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        GlobalInventoryPresenter prompts = new GlobalInventoryPresenter(gim);


        int pageNumber = 1;
        Item item;
        prompts.enter();
        try {
            String input = br.readLine();
            while (!input.equals("e")) { // != compares memory addresses.
                prompts.printpage(pageNumber);
                input = br.readLine();
                if (input.equals("n")){
                    if (pageNumber < gim.generatePageNumber()) {
                        pageNumber += 1;
                    }
                    else
                        prompts.emptyPage();

                }
                if (input.equals("p")){
                    if (pageNumber == 1){
                        prompts.atfirst();
                    }
                    else{
                    pageNumber -= 1; }
                }
                if (input.matches("[0-9]") && Integer.valueOf(input) <= gim.generatePage(pageNumber).size()-1){
                    item = gim.generatePage(pageNumber).get(Integer.parseInt(input));
                    if (UM.getUserFrozenStatus(user)) {
                        prompts.addToWishlishandTradeRequest(item);
                        input = br.readLine();
                        if (input.equals("1")) {
                            UM.getUserWishlist(user).add(item);
                        }
                        if (input.equals("2")) {
                            TradeController trademenu = new TradeController(UM);
                            ArrayList<Item> items = new ArrayList<>();
                            items.add(item);
                            trademenu.run(items, user);
                        }
                    }
                    else {
                    prompts.addToWishlist(item);
                    input = br.readLine();
                    if (input.equals("1")){
                        UM.getUserWishlist(user).add(item);
                        prompts.addedToWishlist(item);
                    }}
                }
                if(input.matches("[0-9]") && Integer.valueOf(input) > gim.generatePage(pageNumber).size()-1){
                    prompts.invalid();
                }
            }
        } catch (IOException e) {
            prompts.error();
        }

    }
}

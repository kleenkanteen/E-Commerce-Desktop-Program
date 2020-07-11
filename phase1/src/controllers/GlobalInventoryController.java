package controllers;

import entities.Item;
import presenters.GlobalInventoryPresenter;
import use_cases.GlobalInventoryManager;
import use_cases.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GlobalInventoryController {
    public void run(GlobalInventoryManager gim, UserManager UM, String user) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        GlobalInventoryPresenter prompts = new GlobalInventoryPresenter(gim);


        int pageNumber = 1;
        Item item;
        prompts.enter();
        try {
            String input = br.readLine();
            while (!input.equals("exit")) { // != compares memory addresses.
                prompts.printpage(pageNumber);
                if (prompts.hasNext()) {
                    System.out.println(prompts.next()); // printing 10 item and goto next
                }
                input = br.readLine();
                if (pageNumber <= gim.generatePageNumber() && input.equals("next")){
                    pageNumber += 1;
                    prompts.printpage(pageNumber);
                }
                if (pageNumber != 1 && input.equals("previous")){
                    pageNumber -= 1;
                    prompts.printpage(pageNumber);
                }
                if (Integer.parseInt(input) <= 10 && Integer.parseInt(input) >= 1){
                    item = gim.generatePage(pageNumber).get(Integer.parseInt(input)-1);
                    if (UM.getUserFrozenStatus(user)) {
                        prompts.addToWishlishandTradeRequest(item);
                        input = br.readLine();
                        if (input.equals("1")) {
                            UM.getUserWishlist(user).add(item);
                        }
                        if (input.equals("2")) {
                            // call trademenu
                        }
                    }
                    else
                    prompts.addToWishlist(item);
                    if (input.equals("1")){
                        UM.getUserWishlist(user).add(item);
                    }
                }
            }
        } catch (IOException e) {
            prompts.error();
        }

    }
}

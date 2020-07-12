package C_controllers;

import D_presenters.GlobalInventoryPresenter;
import E_use_cases.GlobalInventoryManager;
import E_use_cases.TradeManager;
import E_use_cases.UserManager;
import F_entities.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GlobalInventoryController {
    public void run(GlobalInventoryManager gim, UserManager UM, String user, TradeManager TM) {

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
                if (input.equals("previous")){
                    pageNumber -= 1;
                    prompts.printpage(pageNumber);
                }
                if (input.matches("[0-9]")){
                    item = gim.generatePage(pageNumber).get(Integer.parseInt(input));
                    if (UM.getUserFrozenStatus(user)) {
                        prompts.addToWishlishandTradeRequest(item);
                        input = br.readLine();
                        if (input.equals("1")) {
                            UM.getUserWishlist(user).add(item);
                        }
                        if (input.equals("2")) {
                            TradeController trademenu = new TradeController(UM, TM, user);
                            ArrayList<Item> items = new ArrayList<>();
                            items.add(item);
                            trademenu.run(items, user);
                        }
                    }
                    else
                    prompts.addToWishlist(item);
                    input = br.readLine();
                    if (input.equals("1")){
                        UM.getUserWishlist(user).add(item);
                        prompts.addedToWishlist(item);
                    }
                }
            }
        } catch (IOException e) {
            prompts.error();
        }

    }
}

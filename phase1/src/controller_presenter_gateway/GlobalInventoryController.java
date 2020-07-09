package controller_presenter_gateway;

import entities.Item;
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

        System.out.println("Type 'exit' to quit or ok to continue.");
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
                if (Integer.parseInt(input) <= 10 && Integer.parseInt(input) >= 1){
                    item = gim.generatePage(pageNumber).get(Integer.parseInt(input)-1);
                    prompts.wishlishOrTradeRequest(item);
                    input = br.readLine();
                    if (input.equals("wishlist")){
                        UM.getUserWishlist(user).add(item);
                    }
                    if (input.equals("traderequest")){
                        // calls the trade menu
                    }

                }
            }
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

        //user selected item in page and temp.get(0) item
        // get selected item and give it to tradereqeustx
    }
}

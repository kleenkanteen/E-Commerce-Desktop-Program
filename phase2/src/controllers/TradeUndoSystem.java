package controllers;

import entities.Trade;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TradeUndoSystem {
    private UserManager usermanager;
    private TradeManager tradeManager;
    TradeUndoSystem(TradeManager tradeManager, UserManager userManager){
        this.usermanager = userManager;
        this.tradeManager = tradeManager;
    }
    public void run() throws IOException {
        System.out.println("Type the User Name of the User whom you want to undo the trade\n" +
                "Or type 1 to go back to Admin Menu");
        boolean exit = false;
        BufferedReader re = new BufferedReader(new InputStreamReader(System.in));
        String user = re.readLine();
        while (! exit){
            if (user.equals("1")){
                break;
            }
            while (!usermanager.isValidUser(user)){
                System.out.println("No User has been found, try again or type 1 to go back to Admin Menu");
                user = re.readLine();
                if (user.equals("1")){
                    exit = true;
                    break; }
            }
            while (usermanager.isValidUser(user)){
                List<Trade> trade = tradeManager.getUnstartTrades(user);
                if (trade.size() == 0){
                    System.out.println("No trade has been found");
                    System.out.println("Type the User Name of the User whom you want to undo the trade\n" +
                            "Or type 1 to go back to Admin Menu");
                    user = re.readLine();
                    break;
                }
                System.out.println("Type 1 if you want to reach the next trade\n" +
                        "Type 2 to go back to the previous trade\n" +
                        "Type 3 to confirm the deletion of the Trade\n" +
                        "Type 4 to go back");
                String option = re.readLine();
                boolean finishChoosing = false;
                int counting = 0;
                while (!finishChoosing){
                    switch (option) {
                        case "1":
                            if(counting <= trade.size() - 1){
                            counting += 1;
                            System.out.println(trade.get(counting));
                            option = re.readLine();
                            }
                            else {
                                System.out.println("NO more trade can be shown");
                            }
                            break;
                        case "2":
                            if (counting >= 1) {
                                counting -= 1;
                            } else {
                                System.out.println("You have already reached the first trade");
                            }
                            System.out.println(trade.get(counting));
                            option = re.readLine();
                            break;
                        case "3":
                            tradeManager.removeTrade(trade.get(counting));
                            finishChoosing = true;
                            System.out.println("The Trade has been removed\n");
                            System.out.println("Type the User Name of the User whom you want to undo the trade\n" +
                                    "Or type 1 to go back to Admin Menu");
                            user = re.readLine();
                            break;
                        case "4":
                            finishChoosing = true;
                            System.out.println("Type the User Name of the User whom you want to undo the trade\n" +
                                    "Or type 1 to go back to Admin Menu");
                            user = re.readLine();
                            break;
                        default:
                            System.out.println("Your option makes no sense");
                            option = re.readLine();
                            break;
                    }
                }

            }

        }

    }

}

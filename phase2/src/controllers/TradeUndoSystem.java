package controllers;

import entities.Trade;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TradeUndoSystem {
    UserManager usermanager;
    TradeManager tradeManager;
    TradeUndoSystem(TradeManager tradeManager, UserManager userManager){
        this.usermanager = userManager;
        this.tradeManager = tradeManager;
    }
    public void run() throws IOException {
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
                    break;
                }
                System.out.println("Type the User Name of the User whom you want to undo the trade\n" +
                        "Or type 1 to go back to Admin Menu");
                String option = re.readLine();
                boolean finishChoosing = false;
                int counting = 0;
                while (!finishChoosing){
                    System.out.println("Type 1 if you want to reach the next trade\n" +
                            "Type 2 to go back to the previous trade\n" +
                            "Type 3 to confirm the deletion of the Trade\n" +
                            "Type 4 to go back");
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
                            break;
                        case "4":
                            finishChoosing = true;
                            break;
                        default:
                            System.out.println("Your option makes no sense");
                            option = re.readLine();
                            break;
                    }
                }
            }user = re.readLine();
        }

    }

}

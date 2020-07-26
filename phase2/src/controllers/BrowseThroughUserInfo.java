package controllers;

import entities.*;
import presenters.UserPresenter;
import use_cases.*;
import java.util.Scanner;

public class BrowseThroughUserInfo {

    private String currUser;
    private UserPresenter userPresenter;
    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;
    private TradeManager tradeManager;

    /**
     * Constructs a new BrowseThroughUserInfo object
     * @param currUser String username
     * @param userManager UserManager object
     * @param tradeManager TradeManager object
     * @param globalInventoryManager GlobalInventoryManager object
     * @param globalWishlistManager GlobalWishlistManager object
     */
    public BrowseThroughUserInfo(String currUser, UserManager userManager, TradeManager tradeManager,
                                   GlobalInventoryManager globalInventoryManager,
                                   GlobalWishlistManager globalWishlistManager) {
        this.currUser = currUser;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
        this.userPresenter = new UserPresenter();
    }

    /**
     * Runs the BrowseThroughUserInfo object
     */
    public void run() {
        Scanner input = new Scanner(System.in);
        String userInput = "";
        while(!userInput.equals("exit")) {
            this.userPresenter.userMenuUserInfoPrompts();
            userInput = input.nextLine();
            // view trade history
            if (userInput.equals("1")) {
                BrowseThroughUserTrades browseThroughUserTrades =
                        new BrowseThroughUserTrades(this.currUser, this.tradeManager);
                browseThroughUserTrades.run();
            }
            // change password
            else if (userInput.equals("2")) {
                this.userPresenter.setNewPasswordPrompt();
                String newPass = input.nextLine();
                if(!newPass.toLowerCase().equals("exit")) {
                    this.userManager.changePassword(this.currUser, newPass);
                }
            }
            // view frequent trading partners
            else if (userInput.equals("3")) {
                String[] tradingPartners = this.tradeManager.getFrequentTradingPartners(this.currUser, 3);
                // find a better way to do this
                if(tradingPartners[0] == null) {
                    this.userPresenter.noTradingPartners();
                }
                else {
                    for (String tradePartner : tradingPartners) {
                        if (tradePartner == null) {
                            break;
                        }
                        this.userPresenter.printUserTradePartners(tradePartner);
                    }
                }
            }
            // view 3 most recent trades
            else if (userInput.equals("4")) {
                Trade[] recentTradeHistory = this.tradeManager.getRecentTrade(this.currUser, 3);
                // find a better way to do this
                if(recentTradeHistory[0] == null){
                    this.userPresenter.noRecentTrades();
                }
                else {
                    for(Trade trade : recentTradeHistory) {
                        if (trade == null) {
                            break;
                        }
                        this.userPresenter.tradeToString(trade);
                    }
                }
            }
            // look at personal inventory
            else if (userInput.equals("5")) {
                BrowseThroughUserInventory browseThroughUserInventory =
                        new BrowseThroughUserInventory(this.currUser, this.globalInventoryManager);
                browseThroughUserInventory.run();
            }
            // look at personal wishlist
            else if (userInput.equals("6")) {
                BrowseThroughUserWishlist browseThroughUserWishlist = new BrowseThroughUserWishlist(this.currUser,
                        this.userManager, this.tradeManager, this.globalInventoryManager, this.globalWishlistManager);
                browseThroughUserWishlist.run();
            }
            // exit
            else if (userInput.equals("7")) {
                userInput = "exit";
            }
            // input error
            else {
                this.userPresenter.inputError();
            }
        }
    }
}

package controllers;

import entities.Trade;
import presenters.UserPresenter;
import use_cases.TradeManager;
import java.util.List;
import java.util.Scanner;

public class BrowseThroughUserTrades {

    private String currUser;
    private UserPresenter userPresenter;
    private TradeManager tradeManager;

    /**
     * Construct a new BrowseThroughUserTrades object
     * @param currUser the String currUser
     * @param tradeManager the TradeManager object
     */
    public BrowseThroughUserTrades(String currUser, TradeManager tradeManager) {
        this.currUser = currUser;
        this.tradeManager = tradeManager;
        this.userPresenter = new UserPresenter();
    }

    /**
     * Run the BrowseThroughUserTrades controller
     */
    public void run() {
        List<Trade> userTrades = this.tradeManager.getTradeHistory(this.currUser);
        int index = 0;
        Scanner input = new Scanner(System.in);
        String userTradeInput = "";
        while(!userTradeInput.equals("exit")) {
            if (userTrades.size() == 0) {
                this.userPresenter.isEmpty("trade history");
                break;
            }
            this.userPresenter.itemToString(userTrades.get(index).toString());
            this.userPresenter.userTradeHistoryPrompts();
            userTradeInput = input.nextLine();
            // next
            if(userTradeInput.equals("1")) {
                if(index == userTrades.size() - 1) {
                    this.userPresenter.userTradeHistoryEndOfIndex();
                }
                else{
                    index++;
                }
            }
            // previous
            else if(userTradeInput.equals("2")) {
                if(index == userTrades.size() - 1) {
                    this.userPresenter.userTradeHistoryEndOfIndex();
                }
                else{
                    index--;
                }
            }
            // exit
            else if(userTradeInput.equals("3")) {
                userTradeInput = "exit";
            }
            // input error
            else {
                this.userPresenter.inputError();
            }
        }
    }
}

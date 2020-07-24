package controllers;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import presenters.BannedUserPresenter;
import entities.*;

public class BannedUserController {

    private ArrayList<Message> adminMessages;
    private String currUser;
    private BannedUserPresenter unbanPresenter;

    public BannedUserController(ArrayList<Message> adminMessages, String currUser) {
        this.adminMessages = adminMessages;
        this.currUser = currUser;
        this.unbanPresenter = new BannedUserPresenter();
    }

    public void run() {
        boolean continueToRun = true;
        Scanner input = new Scanner(System.in);
        while(continueToRun) {
            this.unbanPresenter.unBanRequestPrompt();
            int userInput = input.nextInt();
            if(userInput == 1) {
                // TODO
                // unban request code goes here
                this.unbanPresenter.unbanRequestSent();
                continueToRun = false;
            }
            else if(userInput == 2) {
                this.unbanPresenter.returningToMainMenu();
                continueToRun = false;
            }
            else {
                this.unbanPresenter.inputNotUnderstood();
            }
        }

    }
}

package controllers;

import java.util.Scanner;
import java.util.List;
import entities.Message;
import presenters.BannedUserPresenter;
import use_cases.MessageBuilder;
import use_cases.AdminManager;

public class BannedUserController {

    private String currUser;
    private BannedUserPresenter unbanPresenter;
    private MessageBuilder messageBuilder;
    private AdminManager adminManager;

    /**
     * Create a new BannedUserController
     * @param currUser the current user
     * @param adminManager the AdminManager object to take in
     */
    public BannedUserController(String currUser, AdminManager adminManager) {
        this.adminManager = adminManager;
        this.currUser = currUser;
        this.unbanPresenter = new BannedUserPresenter();
        this.messageBuilder = new MessageBuilder();
    }

    /**
     * Run the the BannedUserController functionality
     */
    public void run() {
        boolean continueToRun = true;
        Scanner input = new Scanner(System.in);
        while(continueToRun) {
            this.unbanPresenter.unBanRequestPrompt();
            int userInput = input.nextInt();
            // if the user wants to be unbanned
            if(userInput == 1) {
                List<Message> adminMessages = this.adminManager.getAdminMessages();
                adminMessages.add(this.messageBuilder.getUnbanRequest("Please unban user: " +
                        this.currUser + ". They are very sorry :(", this.currUser));
                this.adminManager.setAdminMessages(adminMessages);
                this.unbanPresenter.unbanRequestSent();
                continueToRun = false;
            }
            // if the user does not want to be unbanned
            else if(userInput == 2) {
                this.unbanPresenter.returningToMainMenu();
                continueToRun = false;
            }
            // input error
            else {
                this.unbanPresenter.inputNotUnderstood();
            }
        }

    }
}

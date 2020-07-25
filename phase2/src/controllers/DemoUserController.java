package controllers;

import presenters.DemoUserPresenter;
import use_cases.DemoUserManager;

import java.util.Scanner;

public class DemoUserController {
    private DemoUserPresenter prompts;

    public void run(DemoUserManager DemoUserManager){
        Scanner input = new Scanner(System.in);
        String userInput = "";

        prompts = new DemoUserPresenter(DemoUserManager);
        prompts.promptUserMenu();

        while (!input.equals("Exit")){
            prompts.promptUserMenu();
            userInput= input.nextLine();
            if (userInput.equals("1")){
                browseThroughUserInfo();
            }
            // browse global inventory
            else if (userInput.equals("2")) {
                browseThroughGlobalInventory();
            }
            // loan
            else if (userInput.equals("3")) {
                prompts.noAccess();
            }
            // message
            else if (userInput.equals("4")){
                DemoUserManager.getUserMessage();
                //TODO is that how to get the message?
            }
            // add item to globalinventory
            else if (userInput.equals("5")){
                String demoNameInput;
                String demoDescriptionInput;
                prompts.enterItem();
                demoNameInput = input.nextLine();
                prompts.enterItemDescription();
                demoDescriptionInput = input.nextLine();
                DemoUserManager.createNewItem(demoNameInput, demoDescriptionInput);
                prompts.adminApproval();
            }
            //send admin unfreeze request
            else if (userInput.equals("6")){
                prompts.noAccess();
            }
            else if (userInput.equals("7")) {
                userInput = "exit";
            }
            else {
                prompts.inputError();
            }

        }

    }

    /**
     * Helper method for run() that allows a user to access their personal information
     */
    private void browseThroughUserInfo() {
        Scanner input = new Scanner(System.in);
        String userInput = "";
        while(!userInput.equals("exit")) {
            prompts.userMenuUserInfoPrompts();
            userInput = input.nextLine();
            // view trade history
            if (userInput.equals("1")) {
                prompts.noAccess();
            }
            // change password
            else if (userInput.equals("2")) {
                prompts.noAccess();
            }
            // view frequent trading partners
            else if (userInput.equals("3")) {
                prompts.noAccess();
            }
            // view 3 most recent trades
            else if (userInput.equals("4")) {
                prompts.noAccess();
            }
            // look at personal inventory
            else if (userInput.equals("5")) {
                browseThroughUserInventory();
            }
            // look at personal wishlist
            else if (userInput.equals("6")) {
                browseThroughUserWishlist();
            }
            // exit
            else if (userInput.equals("7")) {
                userInput = "exit";
            }
            // input error
            else {
                prompts.inputError();
            }
        }
    }

    private void browseThroughUserInventory(){
        //TODO
    }

    private void  browseThroughUserWishlist(){
        //TODO
    }

    private  void browseThroughGlobalInventory(){
        //TODO create few items and let user to select, simulate creating traderequest
    }
}
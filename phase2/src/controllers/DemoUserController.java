package controllers;

import presenters.DemoUserPresenter;
import use_cases.DemoUserManager;
import entities.*;
import java.util.List;
import java.util.ArrayList;

import java.util.Scanner;

public class DemoUserController {
    private DemoUserPresenter prompts;
    private DemoUserManager demoUserManager;
    private String username;
    private List<Item> globalInventory;

    /**
     * Constructs a DemoUserController object
     * @param username string username
     * @param password string password
     */
    public DemoUserController(String username, String password) {
        this.demoUserManager = new DemoUserManager(username, password);
        this.username = username;
        this.globalInventory = new ArrayList<>();
    }

    /**
     * Run DemoUserController.
     */
    public void run(){
        Scanner input = new Scanner(System.in);
        String userInput = "";

        this.prompts = new DemoUserPresenter(this.demoUserManager);
        this.prompts.promptUserMenu();

        while (!userInput.equals("exit")){
            this.prompts.promptUserMenu();
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
                this.prompts.noAccess();
            }
            // message
            else if (userInput.equals("4")){
                browseThroughUserMessages();
            }
            // add item to global inventory
            else if (userInput.equals("5")){
                String demoNameInput;
                String demoDescriptionInput;
                this.prompts.enterItem();
                demoNameInput = input.nextLine();
                this.prompts.enterItemDescription();
                demoDescriptionInput = input.nextLine();
                this.globalInventory.add(this.demoUserManager.createNewItem(demoNameInput, demoDescriptionInput));
                this.prompts.adminApproval();
            }
            //send admin unfreeze request
            else if (userInput.equals("6")){
                this.prompts.noAccess();
            }
            else if (userInput.equals("7")) {
                userInput = "exit";
            }
            else {
                this.prompts.inputError();
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
            this.prompts.userMenuUserInfoPrompts();
            userInput = input.nextLine();
            // view trade history
            if (userInput.equals("1")) {
                this.prompts.noAccess();
            }
            // change password
            else if (userInput.equals("2")) {
                this.prompts.passwordInput();
                String passwordInput = input.nextLine();
                this.demoUserManager.setPassword(passwordInput);
            }
            // view frequent trading partners
            else if (userInput.equals("3")) {
                this.prompts.noAccess();
            }
            // view 3 most recent trades
            else if (userInput.equals("4")) {
                this.prompts.noAccess();
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
                this.prompts.inputError();
            }
        }
    }

    /**
     * Helper for run that browses through user messages?
     */
    private void browseThroughUserMessages() {
        List<Message> userMessages = this.demoUserManager.getUserMessage();
        //TODO humongous pain in the ass oh my god
    }

    /**
     * Helper for run that browses through user inventory
     */
    private void browseThroughUserInventory(){
        List<Item> userInventory = this.demoUserManager.getUserInventory();
        int index = 0;
        Scanner input = new Scanner(System.in);
        String userInput = "";
        while(!userInput.equals("exit")) {
            if(userInventory.size() == 0) {
                System.out.println("Your inventory is empty!");
                return;
            }
            this.prompts.itemToString(userInventory.get(index).toString());
            this.prompts.userInventoryPrompts();
            userInput = input.nextLine();
            // remove an item
            if(userInput.equals("1")) {
                this.demoUserManager.removeFromInventory(userInventory.get(index));
                userInventory.remove(index);
                this.globalInventory.remove(index);
                this.prompts.itemRemoved();
            }
            // go to next item
            else if(userInput.equals("2")) {
                if(index == userInventory.size() - 1) {
                    this.prompts.endOfUserInventory();
                }
                else{
                    index++;
                }
            }
            // go to previous item
            else if(userInput.equals("3")) {
                if(index == 0) {
                    this.prompts.endOfUserInventory();
                }
                else {
                    index--;
                }
            }
            // exit
            else if(userInput.equals("4")) {
                userInput = "exit";
            }
            // input error
            else {
                this.prompts.inputError();
            }
        }
    }

    /**
     * Helper for run that browses through user wishlist
     */
    private void browseThroughUserWishlist(){
        List<Item> userWishlist = this.demoUserManager.getUserWishlist();
        Scanner input = new Scanner(System.in);
        String userInput = "";
        int index = 0;
        while(!userInput.equals("exit")) {
            if(userWishlist.size() == 0) {
                System.out.println("Your wishlist is empty.");
                return;
            }
            this.prompts.itemToString(userWishlist.get(index).toString());
            this.prompts.userWishlistPrompts();
            userInput = input.nextLine();
            // remove an item from wishlist
            if(userInput.equals("1")) {
                this.demoUserManager.removeFromWishlist(userWishlist.get(index));
                userWishlist.remove(userWishlist.get(index));
            }
            // next item
            else if(userInput.equals("2")) {
                if(index == userWishlist.size() - 1) {
                    this.prompts.endOfUserWishlist();
                }
                else{
                    index++;
                }
            }
            // previous item
            else if(userInput.equals("3")) {
                if(index == 0) {
                    this.prompts.endOfUserWishlist();
                }
                else {
                    index--;
                }
            }
            // exit
            else if(userInput.equals("4")) {
                userInput = "exit";
            }
            // input error
            else {
                this.prompts.inputError();
            }
        }
    }

    /**
     * Helper for run that browses through a "global inventory" of predefined objects
     */
    private  void browseThroughGlobalInventory(){
        //TODO create few items and let user to select, simulate creating trade request
    }
}
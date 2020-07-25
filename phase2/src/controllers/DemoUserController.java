package controllers;

import java.util.Scanner;
import use_cases.DemoUserManager;

public class DemoUserController {

    private DemoUserManager demoUserManager;
    private String username;

    public DemoUserController(String username, String password) {
        this.username = username;
        this.demoUserManager = new DemoUserManager(username, password);
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        boolean continueToRun = true;
        while(continueToRun) {
            System.out.println("Welcome to this demo! This is what you would see with a standard user account." +
                    "Note that since you are using a demo account, some features will not be available." +
                    "Now, what would you like to do today?" +
                    "\n[1] Access your account information." +
                    "\n[2] Browse through the global inventory." +
                    "\n[3] Loan one of your items to another user." +
                    "\n[4] Look at your message inbox." +
                    "\n[5] Add a new item to the system." +
                    "\n[6] Send admins an unfreeze request." +
                    "\n[7] Exit and log out.");
            int userInput = input.nextInt();
            // browse through demo user info
            if(userInput == 1) {

            }
            // browse through the global inventory
            else if(userInput == 2) {

            }
            // would loan, but since this is a demo account, would just print demo error
            else if(userInput == 3) {
                System.out.println("You are using a demo account and do not have access to this feature.\n");
            }
            // browse through user messages
            else if(userInput == 4) {

            }
            // create new item
            else if(userInput == 5) {
                String demoNameInput;
                String demoDescriptionInput;
                System.out.println("Enter in the item's name.");
                demoNameInput = input.nextLine();
                System.out.println("Enter the item's description.");
                demoDescriptionInput = input.nextLine();
                this.demoUserManager.createNewItem(demoNameInput, demoDescriptionInput);
                System.out.println("In a standard account, this item would be sent to the admins for approval, " +
                        "but we'll be nice and simply add it to your account :)");
            }
            // would normally send admins an unfreeze request, but only prints demo error
            else if(userInput == 6) {
                System.out.println("You are using a demo account and do not have access to this feature.\n");
            }
            else if(userInput == 7) {
                continueToRun = false;
            }
            else {
                System.out.println("Input not understood, please try again.\n");
            }
        }
    }
}

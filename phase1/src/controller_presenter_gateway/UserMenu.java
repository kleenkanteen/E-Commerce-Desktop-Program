package controller_presenter_gateway;

import java.util.Scanner;
import java.util.HashMap;
import entities.User;
import entities.GlobalWishlist;
import entities.GlobalInventory;
import exceptions.InvalidUsernameException;
import uses_cases.UserManager;
import uses_cases.GlobalInventoryManager;


public class UserMenu {
    private String currUser;
    private HashMap<String, User> allUsers;
    private GlobalInventory globalInventory;
    private GlobalWishlist globalWishlist;

    public UserMenu(String currUser, HashMap<String, User> allUser,
                    GlobalInventory globalInventory, GlobalWishlist globalWishlist) {
        this.currUser = currUser;
        this.allUsers = allUser;
        this.globalInventory = globalInventory;
        this.globalWishlist = globalWishlist;
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        // check for too many incompletes?
        // check for all incomplete trades to confirm
        // any additional checks needed as soon as the user successfully logs in?
        String userInput = "";
        UserManager userManager = new UserManager(this.allUsers);
        GlobalInventoryManager globalInventoryManager = new GlobalInventoryManager(this.globalInventory);
        while(!userInput.equals("exit")) {
            System.out.println("Enter in blah blah blah idk I'll work on this later");
            System.out.println("Enter 'exit' to exit.");
            userInput = input.nextLine();
            // look at/change user information
            if (userInput.equals("1")) {
                browseThroughUserInfo(userManager);
            }
            // look at global inventory
            else if (userInput.equals("2")) {
                // call browsing menu whenever it's finished
            }
            // global wishlist
            else if (userInput.equals("3")) {
                // separate menu for GlobalWishlist?
            }
            // look at personal inventory/personal wishlist
            else if (userInput.equals("4")) {
                // ?
            }
            // messages
            else if (userInput.equals("5")) {
                UserMessageReplySystem messageSystem = new UserMessageReplySystem(userManager, globalInventoryManager,
                        this.globalWishlist, this.currUser);
                messageSystem.run();

            }
            // create a new item for admin approval
            else if (userInput.equals("6")) {}
            else {
                System.out.println("Input not understood, please try again.");
            }
        }
    }

    private void browseThroughUserInfo(UserManager userManager) {
        Scanner input = new Scanner(System.in);
        String userInput = "";
        while(!userInput.equals("exit")) {
            System.out.println("blah blah blah");
            userInput = input.nextLine();
            // change password
            if (userInput.equals("1")) {
                System.out.println("Enter in your new password");
                String newPass = input.nextLine();
                userManager.changePassword(this.currUser, newPass);
            }
            //
            else if (userInput.equals("2")) {

            }
            //
            else if(userInput.equals("3")) {

            }
        }
    }
}

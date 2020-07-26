package controllers;

import entities.Item;
import presenters.UserPresenter;
import use_cases.GlobalInventoryManager;
import java.util.List;
import java.util.Scanner;

public class BrowseThroughUserInventory {

    private String currUser;
    private UserPresenter userPresenter;
    private GlobalInventoryManager globalInventoryManager;

    /**
     * Construct a new BrowseThroughUserInventory object
     * @param currUser String current user
     * @param globalInventoryManager GlobalInventoryManager object
     */
    public BrowseThroughUserInventory(String currUser, GlobalInventoryManager globalInventoryManager) {
        this.currUser = currUser;
        this.globalInventoryManager = globalInventoryManager;
        this.userPresenter = new UserPresenter();
    }

    /**
     * Run the BrowseThroughUserInventory controller
     */
    public void run() {
        List<Item> userInventory = this.globalInventoryManager.getPersonInventory(this.currUser);
        int index = 0;
        Scanner input = new Scanner(System.in);
        String userInventoryInput = "";
        while(!userInventoryInput.equals("exit")) {
            // check to see if inventory is empty or not
            if (userInventory.size() == 0) {
                this.userPresenter.isEmpty("inventory");
                break;
            }
            this.userPresenter.itemToString(userInventory.get(index).toString());
            // prompt user on what to do with this item
            this.userPresenter.userInventoryPrompts();
            userInventoryInput = input.nextLine();
            // remove the item from the global inventory and the personal inventory
            if(userInventoryInput.equals("1")) {
                this.globalInventoryManager.removeItem(userInventory.get(index).getItemID());
                userInventory = this.globalInventoryManager.getPersonInventory(this.currUser);
                index = 0;
                this.userPresenter.itemRemoved();
            }
            // next
            else if(userInventoryInput.equals("2")) {
                if(index == userInventory.size() - 1) {
                    this.userPresenter.endOfUserInventory();
                }
                else{
                    index++;
                }
            }
            // back
            else if(userInventoryInput.equals("3")) {
                if(index == 0) {
                    this.userPresenter.endOfUserInventory();
                }
                else {
                    index--;
                }
            }
            // exit
            else if(userInventoryInput.equals("4")) {
                userInventoryInput = "exit";
            }
            // if the user puts in something weird
            else {
                this.userPresenter.inputError();
            }
        }
    }
}

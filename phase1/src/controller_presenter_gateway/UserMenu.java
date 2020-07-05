package controller_presenter_gateway;

import java.util.Scanner;
import java.util.HashMap;
import entities.User;

public class UserMenu {
    private String currUser;
    private HashMap<String, User> allUsers;

    public UserMenu(String currUser, HashMap<String, User> allUser) {
        this.currUser = currUser;
        this.allUsers = allUser;
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        String userInput = "";
        while(!userInput.equals("exit")) {
            System.out.println("Enter in blah blah blah idk I'll work on this later");
        }
    }
}

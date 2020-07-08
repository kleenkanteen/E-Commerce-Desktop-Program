package controller_presenter_gateway;

import entities.Admin;
import entities.Item;
import entities.User;
import exceptions.InvalidLoginException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MainMenu {
    public void run() throws IOException {

        String serializedAdmins = "src/managers/serializedAdmins.ser";
        String serializedUsers = "src/managers/serializedUsers.ser";
        String serializedGlobalInventory = "src/managers/serializedGlobalInventory.ser";
        String serializedItemIDs = "src/managers/serializedItemIDs.ser";
        //String serializedMessages = "src/managers/serializedMessages.ser";

        UserGateway ug = new UserGateway(serializedUsers);
        HashMap<String, User> userHashMap = ug.getMapOfUsers();

        AdminAccountGateways ag = new AdminAccountGateways(serializedAdmins);
        HashMap<String, Admin> adminHashMap = ag.getAdminMap();

        GlobalInventoryGateways gig = new GlobalInventoryGateways(serializedGlobalInventory);
        HashMap<String, Item> itemHashMap = gig.gI.getItemMap();

        //TODO: add gateway to deserialize itemIDs so we can also pass that.
        //TODO: message deserialization? or is all of that stored in each admin/ user acc?

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals("exit")) {
            System.out.println("Type:\n'1' for User Login.\n'2' for User Account Creation.\n'3' for Admin Login.\n'exit' to exit the program.");
            try {
                input = br.readLine();
                if (input.equals("1") || input.equals("2") || input.equals("3")) {
                    System.out.println("Enter username:");
                    String username = br.readLine();
                    System.out.println("Enter password");
                    String pass = br.readLine();
                    if (input.equals("1") || input.equals("2")){
                        UserLogin ul = new UserLogin(username, pass);
                        if (input.equals("1") && (ul.login(userHashMap)).equals(username)){
                                UserMenu um = new UserMenu(username, userHashMap);
                                um.run();
                        }
                        else {
                            System.out.println("avoiding error");
                            //TODO: bring up concerns w this
                        }
                    }
                    else {
                        AdminLogin y = new AdminLogin(username, pass, adminHashMap);
                        if (y.login().equals(username)) {
                            Admin loggedInAdmin = y.getAdminObject();
                            AdminMenu am = new AdminMenu(loggedInAdmin);
                        }
                    }
                }
            } catch (IOException | InvalidLoginException e) {
                System.out.println("Something went wrong");
            }
            ug.writeToFile(serializedUsers, userHashMap);
            ag.saveToFile(adminHashMap);
            // gig.writeToFile(itemHashMap);
        }
    }
}


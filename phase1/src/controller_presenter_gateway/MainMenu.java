package controller_presenter_gateway;

import entities.*;
import exceptions.InvalidLoginException;
import exceptions.InvalidUsernameException;
import gateways.UserGateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainMenu {
    public void run() throws IOException {

        String serializedAdmins = "src/ser_file_infos/serializedAdmins.ser";
        String serializedUsers = "src/ser_file_infos/serializedUsers.ser";
        String serializedGlobalInventory = "src/ser_file_infos/serializedGlobalInventory.ser";
        String serializedAdminMessages = "src/ser_file_infos/serializedAdminMessages.ser";
        String serializedGlobalWishlist = "src/ser_file_infos/serializedGlobalWishlist.ser";
        String serializedUserTrades = "src/ser_file_infos/serializedUserTrades.ser";

        UserGateway ug = new UserGateway(serializedUsers);
        HashMap<String, User> userHashMap = ug.getMapOfUsers();

        AdminAccountGateways ag = new AdminAccountGateways(serializedAdmins);
        HashMap<String, Admin> adminHashMap = ag.getAdminMap();

        AdminMessageGateway amg = new AdminMessageGateway(serializedAdminMessages);
        ArrayList<Message> adminMessagesArrayList = amg.getMessages();

        GlobalInventoryGateways gig = new GlobalInventoryGateways(serializedGlobalInventory);
        GlobalInventory globalInv = gig.getgI();

        GlobalWishlistGateway gwl = new GlobalWishlistGateway(serializedGlobalWishlist);
        GlobalWishlist globalWList = gwl.getWishlistItems();

        UserTradesGateway utg = new UserTradesGateway(serializedUserTrades);
        HashMap<String, ArrayList<Trade>> userTrades = utg.getUserTrades();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
            System.out.println("Type:\n'1' for User Login.\n'2' for User Account Creation.\n'3' for Admin Login.\nAny other value to exit the program.");
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
                                UserMenu um = new UserMenu(username, userHashMap, userTrades, globalInv, globalWList, adminMessagesArrayList);
                                um.run();
                        }
                        else {
                            ul.createNewUser(userHashMap);
                        }
                    }
                    else {
                        AdminLogin y = new AdminLogin(username, pass, adminHashMap);
                        if (y.login().equals(username)) {
                            Admin loggedInAdmin = y.getAdminObject();
                            AdminSystem am = new AdminSystem(loggedInAdmin, adminHashMap,
                                    adminMessagesArrayList, userHashMap, globalInv);
                        }
                    }
                }
                else {
                    System.out.println("Exiting program.");
                }
            } catch (IOException | InvalidLoginException | InvalidUsernameException e) {
                System.out.println("Something went wrong");
            }
            ug.writeToFile(serializedUsers, userHashMap);
            ag.saveToFile(adminHashMap);
            amg.writeToFile(serializedAdminMessages, adminMessagesArrayList);
            gig.writeToFile(globalInv);
            gwl.writeToFile(serializedGlobalWishlist, globalWList);
            utg.writeToFile(serializedUserTrades, userTrades);
    }
}


package controller_presenter_gateway;

import controllers.AdminSystem;
import entities.*;
import exceptions.InvalidLoginException;
import exceptions.InvalidUsernameException;
import gateways.*;
import presenters.AdminMenu;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;


public class MainMenu {
    public void run() throws IOException, ClassNotFoundException {

        String serializedAdmins = "phase1/src/ser_file_infos/serializedAdmins.ser";
        String serializedUsers = "phase1/src/ser_file_infos/serializedUsers.ser";
        String serializedGlobalInventory = "phase1/src/ser_file_infos/serializedGlobalInventory.ser";
        String serializedAdminMessages = "phase1/src/ser_file_infos/serializedAdminMessages.ser";
        String serializedGlobalWishlist = "phase1/src/ser_file_infos/serializedGlobalWishlist.ser";
        String serializedUserTrades = "phase1/src/ser_file_infos/serializedUserTrades.ser";

        String dirName = "phase1/src/ser_file_infos";

        Files.list(new File(dirName).toPath())
                .limit(10)
                .forEach(path -> {
                    System.out.println(path);
                });

        UserGateway ug = new UserGateway(serializedUsers);
        HashMap<String, User> userHashMap = ug.getMapOfUsers();

        /*AdminAccountGateways ag = new AdminAccountGateways(serializedAdmins);
        HashMap<String, Admin> adminHashMap = ag.getAdminMap();*/
        Admin primeAdmin = new Admin("a", "a");
        AdminAccountGateways ag = new AdminAccountGateways(serializedAdmins);
        HashMap<String, Admin> adminHashMap = ag.getAdminMap();
        if (!adminHashMap.containsKey("a")){
            adminHashMap.put("a", primeAdmin);
        }
        ag.saveToFile(adminHashMap);

        AdminMessageGateway amg = new AdminMessageGateway(serializedAdminMessages);
        ArrayList<Message> adminMessagesArrayList = amg.getMessages();

        GlobalInventoryGateways gig = new GlobalInventoryGateways(serializedGlobalInventory);
        GlobalInventory globalInv = gig.getgI();

        GlobalWishlistGateway gwl = new GlobalWishlistGateway(serializedGlobalWishlist);
        GlobalWishlist globalWList = gwl.getWishlistItems();

        UserTradesGateway utg = new UserTradesGateway(serializedUserTrades);
        HashMap<String, ArrayList<Trade>> userTrades = utg.getUserTrades();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

/*
        Admin x = new Admin("ball", "bowl");
        HashMap<String, Admin> adminList = new HashMap<>();
        adminList.put(x.getUsername(), x);

        User x2 = new User("hi", "hello");
        HashMap<String, User> allUser = new HashMap<>();
        allUser.put(x2.getUsername(), x2);

        FreezeRequestMessage x3 = new FreezeRequestMessage("blah", x.getUsername());
        ArrayList<Message> adminMessageList = new ArrayList<>();
        adminMessageList.add(x3);

        HashMap<String, Item> itemMap = new HashMap<String, Item>();
        Item tempItem = new Item("duck", "hi", "this is a dubber ruck");
        int d = getRandom(40);
        String str = String.valueOf(d);
        System.out.println(str);
        itemMap.put(str, tempItem);
        ArrayList<String> itemIdCollection = new ArrayList<>();
        itemIdCollection.add(str);
        GlobalInventory x4 = new GlobalInventory();
        x4.addItem(str, tempItem);

        GlobalWishlist globalwl = new GlobalWishlist();
        globalwl.addWish(str, x2.getUsername());

      new AdminMenu(x);*/

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
                        System.out.println("Valid login.");
                        Admin loggedInAdmin = y.getAdminObject();
                        System.out.println("2");

                        AdminSystem am = new AdminSystem(loggedInAdmin, adminHashMap,
                                adminMessagesArrayList, userHashMap, globalInv);
                        System.out.println("3");

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
    //TODO: remove this
    public static int getRandom(int max){
        return (int) (Math.random()*max);
    }
}


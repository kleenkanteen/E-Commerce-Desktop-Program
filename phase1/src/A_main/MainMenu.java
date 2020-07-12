package A_main;

import C_controllers.AdminSystem;
import C_controllers.UserMenu;
import D_presenters.AdminMenu;
import E_use_cases.*;
import F_entities.*;
import G_exceptions.InvalidLoginException;
import G_exceptions.InvalidUsernameException;
import B_gateways.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;


public class MainMenu {
    /**
     * attempts to deserialize all previously stored Admins, Users, the GlobalInventory, Messages shared by all admins
     * the GlobalWishList, all UserTrades
     * Based on user input and if a successful login is performed, grants access to User/ Admin Menu/ User Acc Creation
     * @throws IOException in case file is moved/ some other I/O error.
     * @throws ClassNotFoundException in case the class that is being passed in or casted does not exist.
     */
    public void run() throws IOException, ClassNotFoundException {
        String serializedAdmins = "phase1/src/H_ser_file_infos/serializedAdmins.ser";
        String serializedUsers = "phase1/src/H_ser_file_infos/serializedUsers.ser";
        String serializedGlobalInventory = "phase1/src/H_ser_file_infos/serializedGlobalInventory.ser";
        String serializedAdminMessages = "phase1/src/H_ser_file_infos/serializedAdminMessages.ser";
        String serializedGlobalWishlist = "phase1/src/H_ser_file_infos/serializedGlobalWishlist.ser";
        String serializedUserTrades = "phase1/src/H_ser_file_infos/serializedUserTrades.ser";
        //deserialize admins
        AdminAccountGateways ag = new AdminAccountGateways(serializedAdmins);
        System.out.println("Admins:\n" + ag.getAdminMap());

//        Admin tempadmin = new Admin("admin", "admin");
//        HashMap<String, Admin> tempadminlist = new HashMap<>();
//        tempadminlist.put(tempadmin.getUsername(), tempadmin);
//        ag.setAdminMap(tempadminlist)w;
//        System.out.println("Admins:\n" + ag.getAdminMap());

        //deserialize users
        UserGateway ug = new UserGateway(serializedUsers);
        System.out.println("Users:\n" + ug.getMapOfUsers());
        /*Item temporaryItem = new Item("bike", "abenav", "old rusty bike");
        int rand = getRandom(40000);
        String randString = Integer.toString(rand);
        temporaryItem.setItemID(randString);
         */
        //deserialize global inventory
        GlobalInventoryGateways gig = new GlobalInventoryGateways(serializedGlobalInventory);
        System.out.println("Global inventory:\n" + gig.getgI());
        /*GlobalInventory tempinv = new GlobalInventory();
        ArrayList<String> itemIdCollection = new ArrayList<>();
        tempinv.getItemMap().put(randString, temporaryItem);
        tempinv.getItemIdCollection().add(randString);
        gig.writeToFile(tempinv);

        Item temporaryItem2 = new Item("car", "abenav", "honda accord");
        rand = getRandom(40000);
        randString = Integer.toString(rand);
        temporaryItem2.setItemID(randString);
        gig.getgI().addItem(temporaryItem2.getItemID(), temporaryItem2);
        tempinv = gig.getgI();
         */
        /*AdminAccountGateways ag = new AdminAccountGateways(serializedAdmins);
        HashMap<String, Admin> adminHashMap = ag.getAdminMap();*/
        /* User newuser = new User("abenav", "abenav");
        User newuser2 = new User("pylon", "pylon");
        HashMap<String, User> allUsers = new HashMap<>();
        allUsers.put(newuser.getUsername(), newuser);
        allUsers.put(newuser2.getUsername(), newuser2);
        Item speakers = new Item("speakers", newuser.getUsername(), "micca mb42");
        ArrayList<Item> user1trades = new ArrayList<>();
        user1trades.add(speakers);
        Item phone = new Item("phone", newuser2.getUsername(), "samsung s9");
        ArrayList<Item> user2trades = new ArrayList<>();
        user2trades.add(phone);
        LocalDateTime rightNow1 = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.of(2021, Month.JUNE, 2, 2, 34, 2);
        TempTrade newtrade = new TempTrade(newuser.getUsername(), newuser2.getUsername(), user1trades, user2trades, rightNow1, endDate);
        ArrayList<Trade> tempArray = new ArrayList<>();
        tempArray.add(newtrade);
        HashMap<String, ArrayList<Trade>> userTradez = new HashMap<>();
        userTradez.put(newuser.getUsername(), tempArray);

        /*
        System.out.println("User trade info:\n" + userTradez);
        UserTradesGateway utg = new UserTradesGateway(serializedUserTrades);
        utg.writeToFile(serializedUserTrades, userTradez);*/
        //deserialize all user trades
        UserTradesGateway utg = new UserTradesGateway(serializedUserTrades);
        System.out.println("User trade info:\n" +utg.getUserTrades());
        /* HashMap<String, Item> itemMap1 = new HashMap<String, Item>();
        ArrayList<String> itemIDcollection1 = new ArrayList<>();
        int p = getRandom(5000);
        String p2 = Integer.toString(p);
        itemIDcollection1.add(p2);
        System.out.println(p2);
        Item noo = new Item("asd", "afa", "old sfas");
        noo.setItemID(p2);
        itemMap1.put(p2, noo);
        GlobalInventory tempGlobal = new GlobalInventory();
        tempGlobal.setItemMap(itemMap1);
        tempGlobal.addItemIdToCollection(p2);
        System.out.println("Global inventory:\n" + gig.getgI());
        */
        /* GlobalWishlist globo = new GlobalWishlist();
        globo.addWish(p2, newuser.getUsername());
        System.out.println("Messages: \n"+globo);
        */
        //deserialize GlobalWishlistGateway
        GlobalWishlistGateway gwl = new GlobalWishlistGateway(serializedGlobalWishlist);
        System.out.println("Global Wishlist Items:\n" + gwl.getWishlistItems());
        /*ArrayList <Message> tempray = new ArrayList<>();
        FreezeRequestMessage xd = new FreezeRequestMessage("im freezing you!", newuser.getUsername());
        tempray.add(xd);
        System.out.println("Messages: \n"+tempray);
         */
        //deserialize AdminMessageGateway
        AdminMessageGateway amg = new AdminMessageGateway(serializedAdminMessages);
        System.out.println("Admin Messages:\n"+ amg.getMessages());
        /*amg.writeToFile(serializedAdminMessages, tempray);
         */
        /*
        UserMenu um = new UserMenu("abenav", ug.getMapOfUsers(), utg.getUserTrades(), gig.getgI(), gwl.getWishlistItems(), amg.getMessages());
        um.run();
        ug.writeToFile(serializedUsers, ug.getMapOfUsers());
        gig.writeToFile(gig.getgI());
        utg.writeToFile(serializedUserTrades, utg.getUserTrades());
        gwl.writeToFile(serializedGlobalWishlist,  gwl.getWishlistItems());
        amg.writeToFile(serializedAdminMessages, amg.getMessages());
         */
        //create UserManager x
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        System.out.println("Choose your option below:\n[1] for User Login.\n[2] for User Account Creation.\n[3] for Admin Login.\nAny other value to exit the program.");        try {
            input = br.readLine();
            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                System.out.println("Enter username:");
                String username = br.readLine();
                System.out.println("Enter password");
                String pass = br.readLine();
                UserManager attempt = new UserManager(ug.getMapOfUsers());
                GlobalInventoryManager y2 = new GlobalInventoryManager(gig.getgI());
                if (input.equals("1") || input.equals("2")){
                    attempt.login(username, pass);
                    if (input.equals("1")){
                        if (attempt.login(username, pass)) {
                            TradeManager y = new TradeManager(utg.getUserTrades());
                            GlobalWishlistManager y3 = new GlobalWishlistManager(gwl.getWishlistItems());
                            UserMenu um = new UserMenu(username, attempt, y, y2, y3, amg.getMessages());
                            um.run();
                        }
                    }
                    else
                        attempt.createNewUser(username, pass, utg.getUserTrades());
                    }
                else {
                    AdminLogin thing = new AdminLogin(username, pass, ag.getAdminMap());
                    if (thing.login().equals(username) && !(username.equals("System Messages"))){
                        AdminManager r = new AdminManager(ag.getAdminMap(), amg.getMessages());
                        AdminSystem successful = new AdminSystem(thing.getAdminObject(), r, attempt, y2);
                        successful.run();
                    }
                    }
                }
            else {
                System.out.println("Exiting program.");
            }
        } catch (IOException e) {
            return;
        } catch (InvalidLoginException x) {
        }
        ug.writeToFile(serializedUsers, ug.getMapOfUsers());
        gig.writeToFile(gig.getgI());
        utg.writeToFile(serializedUserTrades, utg.getUserTrades());
        gwl.writeToFile(serializedGlobalWishlist,  gwl.getWishlistItems());
        amg.writeToFile(serializedAdminMessages, amg.getMessages());
        ag.saveToFile(ag.getAdminMap());

    }


}


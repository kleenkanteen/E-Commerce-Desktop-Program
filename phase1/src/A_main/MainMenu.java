package A_main;

import C_controllers.AdminSystem;
import C_controllers.UserMenu;
import D_presenters.MainMenuPresenter;
import E_use_cases.*;
import F_entities.Admin;
import G_exceptions.InvalidLoginException;
import B_gateways.*;
import G_exceptions.InvalidUsernameException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainMenu {
    /**
     * attempts to deserialize all previously stored Admins, Users, the GlobalInventory, Messages shared by all admins
     * the GlobalWishList, all UserTrades
     * Based on user input and if a successful login is performed, grants access to User/ Admin Menu/ User Acc Creation
     */
    public void run() {
        MainMenuPresenter mm = new MainMenuPresenter();

        String serializedAdmins = "phase1/H_ser_file_infos/serializedAdmins.ser";
        String serializedUsers = "phase1/H_ser_file_infos/serializedUsers.ser";
        String serializedGlobalInventory = "phase1/H_ser_file_infos/serializedGlobalInventory.ser";
        String serializedAdminMessages = "phase1/H_ser_file_infos/serializedAdminMessages.ser";
        String serializedGlobalWishlist = "phase1/H_ser_file_infos/serializedGlobalWishlist.ser";
        String serializedUserTrades = "phase1/H_ser_file_infos/serializedUserTrades.ser";


        AdminAccountGateways ag;
        UserGateway ug;
        GlobalInventoryGateways gig;
        UserTradesGateway utg;
        GlobalWishlistGateway gwl;
        AdminMessageGateway amg;
        try {
            //deserialize admins
            ag = new AdminAccountGateways(serializedAdmins);
            //System.out.println("Admins:\n" + ag.getAdminMap());

            //deserialize users
            ug = new UserGateway(serializedUsers);
            //System.out.println("Users:\n" + ug.getMapOfUsers());

            //deserialize global inventory
            gig = new GlobalInventoryGateways(serializedGlobalInventory);
            //System.out.println("Global inventory:\n" + gig.getgI());

            //deserialize all user trades
            utg = new UserTradesGateway(serializedUserTrades);
            //System.out.println("User trade info:\n" + utg.getUserTrades());

            //deserialize GlobalWishlistGateway
            gwl = new GlobalWishlistGateway(serializedGlobalWishlist);
            //System.out.println("Global Wishlist Items:\n" + gwl.getWishlistItems());

            //deserialize AdminMessageGateway
            amg = new AdminMessageGateway(serializedAdminMessages);
            //System.out.println("Admin Messages:\n" + amg.getMessages());
        }catch(IOException | ClassNotFoundException ex){
            mm.readError();
            mm.printExit();
            return;
        }
        //create UserManager x
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (true) {
            mm.printMenuPrompt();
            try {
                input = br.readLine();
                if (input.equals("1") || input.equals("3")) mm.printLoginPrompt1();
                if(input.equals("2")) mm.printLoginPromptNewUsername();
                if (input.equals("1") || input.equals("2") || input.equals("3")) {
                    String username = br.readLine();
                    mm.printLoginPrompt2();
                    String pass = br.readLine();
                    UserManager attempt = new UserManager(ug.getMapOfUsers());
                    GlobalInventoryManager y2 = new GlobalInventoryManager(gig.getgI());
                    if (input.equals("1") || input.equals("2")) {
                        attempt.login(username, pass);
                        if (input.equals("1")) {
                            if (attempt.login(username, pass)) {
                                TradeManager y = new TradeManager(utg.getUserTrades());
                                GlobalWishlistManager y3 = new GlobalWishlistManager(gwl.getWishlistItems());
                                UserMenu um = new UserMenu(username, attempt, y, y2, y3, amg.getMessages());
                                um.run();
                            }
                        } else{
                            if (attempt.createNewUser(username, pass, utg.getUserTrades())){
                                ag.getAdminMap().put(username, new Admin(username, pass));
                            }
                            else mm.takenUsername();
                        }
                    } else {
                        AdminLogin thing = new AdminLogin(username, pass, ag.getAdminMap());
                        if (thing.login().equals(username) && !(username.equals("System Messages"))) {
                            AdminManager r = new AdminManager(ag.getAdminMap(), amg.getMessages());
                            AdminSystem successful = new AdminSystem(thing.getAdminObject(), r, attempt, y2);
                            successful.run();
                        }
                    }
                } else {
                    mm.printExit();
                    break;
                }
            } catch (IOException e) {
                mm.inputError();
                mm.printExit();
                return;
            } catch (InvalidLoginException x) {
                mm.wrongLogin();
            }
            catch (InvalidUsernameException ex){
                mm.takenUsername();
            }

        }
            try {
                ug.writeToFile(serializedUsers, ug.getMapOfUsers());
                gig.writeToFile(gig.getgI());
                utg.writeToFile(serializedUserTrades, utg.getUserTrades());
                gwl.writeToFile(serializedGlobalWishlist, gwl.getWishlistItems());
                amg.writeToFile(serializedAdminMessages, amg.getMessages());
                ag.saveToFile(ag.getAdminMap());
            }catch(IOException e){
                mm.savingError();
            }
    }


}


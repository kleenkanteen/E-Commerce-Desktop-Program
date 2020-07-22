package controllers;
import presenters.MainMenuPresenter;
import entities.User;
import exceptions.InvalidUsernameException;
import gateways.GlobalWishlistGateway;
import use_cases.GlobalWishlistManager;

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

        gateways.AdminAccountGateways ag;
        gateways.UserGateway ug;
        gateways.GlobalInventoryGateways gig;
        gateways.UserTradesGateway utg;
        gateways.GlobalWishlistGateway gwl;
        gateways.AdminMessageGateway amg;
        try {
            //deserialize admins
            ag = new gateways.AdminAccountGateways(serializedAdmins);

            if(ag.getAdminMap().isEmpty()){
                ag.beginAdminMap();
            }
            //deserialize users
            ug = new gateways.UserGateway(serializedUsers);

            //deserialize global inventory
            gig = new gateways.GlobalInventoryGateways(serializedGlobalInventory);

            //deserialize all user trades
            utg = new gateways.UserTradesGateway(serializedUserTrades);

            //deserialize GlobalWishlistGateway
            gwl = new GlobalWishlistGateway(serializedGlobalWishlist);

            //deserialize AdminMessageGateway
            amg = new gateways.AdminMessageGateway(serializedAdminMessages);

        }catch(IOException | ClassNotFoundException ex){
            mm.readError();
            mm.printExit();
            return;
        }
        //create UserManager x
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        boolean done = false;
        do {
            mm.printMenuPrompt();
            try {
                input = br.readLine();
                if (input.equals("1") || input.equals("3")) mm.printLoginPrompt1();
                if (input.equals("2")) mm.printLoginPromptNewUsername();
                if (input.equals("1") || input.equals("2") || input.equals("3")) {
                    String username = br.readLine();
                    mm.printLoginPrompt2();
                    String pass = br.readLine();
                    use_cases.AdminManager r = new use_cases.AdminManager(ag.getAdminMap(), amg.getMessages());
                    use_cases.UserManager attempt = new use_cases.UserManager(ug.getMapOfUsers());
                    use_cases.GlobalInventoryManager y2 = new use_cases.GlobalInventoryManager(gig.getgI());
                    if (input.equals("1") || input.equals("2")) {
                        attempt.login(username, pass);
                        if (input.equals("1")) {
                            // user selected "1" (user sign-in)
                            if(attempt.login(username, pass)) {
                                use_cases.TradeManager y = new use_cases.TradeManager(utg.getUserTrades());
                                use_cases.GlobalWishlistManager y3 = new GlobalWishlistManager(gwl.getWishlistItems());
                                controllers.UserMenu um = new UserMenu(username, attempt, y, y2, y3, amg.getMessages());
                                um.run();
                            }
                            else mm.wrongLogin();
                        } else{
                            // user selected "2" (user sign-up)
                            boolean d = attempt.createNewUser(username, pass, utg.getUserTrades());
                            if (!d){
                                mm.usernameTooShort();
                            }
                            else {
                                if (!(ag.getAdminMap().containsKey(username))) {
                                    ug.getMapOfUsers().put(username, new User(username, pass));
                                    mm.successfulAccountCreation();
                                }
                                else mm.takenUsername();
                            }
                        }
                    } else {
                        // user selected "3" (admin sign-in)
                        if ((r.login(username, pass))) {
                            controllers.AdminSystem successful = new AdminSystem(r.getAdmin(username), r, attempt, y2);
                            successful.run();
                        }
                        else mm.wrongLogin();
                    }
                } else {
                    mm.printExit();
                    done = true;
                }
            } catch (IOException e) {
                mm.inputError();
                mm.printExit();
                return;
            }
            catch (InvalidUsernameException f){
                mm.takenUsername();
            }
        } while(!done);
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


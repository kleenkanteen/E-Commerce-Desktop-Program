package controllers;
import presenters.MainMenuPresenter;
import entities.User;
import exceptions.InvalidUsernameException;
import gateways.*;
import use_cases.*;

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

        String adminFilepath = "data/serializedAdmins.ser";
        String userFilepath = "data/serializedUsers.ser";
        String globalInventoryFilepath = "data/serializedGlobalInventory.ser";
        String adminMessagesFilepath = "data/serializedAdminMessages.ser";
        String globalWishlistFilepath = "data/serializedGlobalWishlist.ser";
        String tradeFilepath = "data/serializedUserTrades.ser";

        AdminAccountGateways adminAccountGateways;
        UserGateway userGateway;
        GlobalInventoryGateways globalInventoryGateways;
        UserTradesGateway userTradesGateway;
        GlobalWishlistGateway globalWishlistGateway;
        AdminMessageGateway adminMessageGateway;
        try {
            GatewayBuilder gatewayBuilder = new GatewayBuilder();
            //deserialize admins
            adminAccountGateways = gatewayBuilder.getAdminAccountGateways(adminFilepath);

            if(adminAccountGateways.getAdminMap().isEmpty()){
                adminAccountGateways.beginAdminMap();
            }
            //deserialize users
            userGateway = gatewayBuilder.getUserGateway(userFilepath);

            //deserialize global inventory
            globalInventoryGateways = gatewayBuilder.getGlobalInventoryGateways(globalInventoryFilepath);

            //deserialize all user trades
            userTradesGateway = gatewayBuilder.getUserTradesGateway(tradeFilepath);

            //deserialize GlobalWishlistGateway
            globalWishlistGateway = gatewayBuilder.getGlobalWishlistGateway(globalWishlistFilepath);

            //deserialize AdminMessageGateway
            adminMessageGateway = gatewayBuilder.getAdminMessageGateways(adminMessagesFilepath);

        }catch(IOException | ClassNotFoundException ex){
            mm.readError();
            mm.printExit();
            return;
        }

        UseCaseBuilder useCaseBuilder = new UseCaseBuilder();
        AdminManager adminManager = useCaseBuilder.getAdminManager(adminAccountGateways.getAdminMap(),
                adminMessageGateway.getMessages());
        UserManager userManager = useCaseBuilder.getUserManager(userGateway.getMapOfUsers());

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
                    //use_cases.AdminManager r = new use_cases.AdminManager(ag.getAdminMap(), amg.getMessages());
                    //use_cases.UserManager attempt = new use_cases.UserManager(ug.getMapOfUsers());
                    //use_cases.GlobalInventoryManager y2 = new use_cases.GlobalInventoryManager(gig.getgI());
                    if (input.equals("1") || input.equals("2")) {
                        userManager.login(username, pass);
                        if (input.equals("1")) {
                            // user selected "1" (user sign-in)
                            if(userManager.login(username, pass)) {
                                TradeManager tradeManager =
                                        useCaseBuilder.getTradeManager(userTradesGateway.getUserTrades());
                                GlobalInventoryManager globalInventoryManager =
                                        useCaseBuilder.getGlobalInventoryManager(globalInventoryGateways.getgI());
                                GlobalWishlistManager globalWishlistManager =
                                        useCaseBuilder.getGlobalWishlistManager(globalWishlistGateway.getWishlistItems());

                                UserMenu um = new UserMenu(username, userManager, tradeManager, globalInventoryManager
                                        , globalWishlistManager, adminManager.getAdminMessages());
                                um.run();
                            }
                            else mm.wrongLogin();
                        } else{
                            // user selected "2" (user sign-up)
                            boolean d = userManager.createNewUser(username, pass, userTradesGateway.getUserTrades());
                            if (!d){
                                mm.usernameTooShort();
                            }
                            else {
                                if (!adminManager.userExist(username)) {
                                    userManager.createNewUser(username, pass, userTradesGateway.getUserTrades());
                                    mm.successfulAccountCreation();
                                }
                                else mm.takenUsername();
                            }
                        }
                    } else {
                        // user selected "3" (admin sign-in)
                        if ((adminManager.login(username, pass))) {
                            GlobalInventoryManager globalInventoryManager =
                                    useCaseBuilder.getGlobalInventoryManager(globalInventoryGateways.getgI());
                            TradeManager tradeManager =
                                    useCaseBuilder.getTradeManager(userTradesGateway.getUserTrades());

                            controllers.AdminSystem successful = new AdminSystem(adminManager.getAdmin(username),
                                    adminManager, userManager, globalInventoryManager,
                                    tradeManager);
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
            userGateway.writeToFile(userFilepath, userGateway.getMapOfUsers());
            globalInventoryGateways.writeToFile(globalInventoryGateways.getgI());
            userTradesGateway.writeToFile(tradeFilepath, userTradesGateway.getUserTrades());
            globalWishlistGateway.writeToFile(globalWishlistFilepath, globalWishlistGateway.getWishlistItems());
            adminMessageGateway.writeToFile(adminMessagesFilepath, adminManager.getAdminMessages());
            adminAccountGateways.saveToFile(adminAccountGateways.getAdminMap());
        }catch(IOException e){
            mm.savingError();
        }
    }
}


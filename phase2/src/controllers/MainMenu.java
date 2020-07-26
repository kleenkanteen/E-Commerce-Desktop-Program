package controllers;
import gateways.*;
import presenters.MainMenuPresenter;
import use_cases.*;
import entities.User;
import exceptions.InvalidUsernameException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainMenu {
    /**
     * attempts to deserialize all previously stored Admins, Users, the GlobalInventory, Messages shared by all admins
     * the GlobalWishList, all UserTrades
     * Based on user input and if a successful login is performed, grants access to User/ Admin Menu/ User Acc Creation
     */
    MainMenuPresenter mainMenuPresenter = new MainMenuPresenter();

    String serializedAdmins = "H_ser_file_infos/serializedAdmins.ser";
    String serializedUsers = "H_ser_file_infos/serializedUsers.ser";
    String serializedGlobalInventory = "H_ser_file_infos/serializedGlobalInventory.ser";
    String serializedAdminMessages = "H_ser_file_infos/serializedAdminMessages.ser";
    String serializedGlobalWishlist = "H_ser_file_infos/serializedGlobalWishlist.ser";
    String serializedUserTrades = "H_ser_file_infos/serializedUserTrades.ser";

    AdminAccountGateways adminAccountGateways;
    UserGateway userGateway;
    GlobalInventoryGateways globalInventoryGateways;
    UserTradesGateway userTradesGateway;
    GlobalWishlistGateway globalWishlistGateway;
    AdminMessageGateway adminMessageGateway;

    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public void run() {
        try {
            // deserialize variables
            adminAccountGateways = new AdminAccountGateways(serializedAdmins);
            if (adminAccountGateways.getAdminMap().isEmpty()) {
                adminAccountGateways.beginAdminMap();
            }
            userGateway = new UserGateway(serializedUsers);
            globalInventoryGateways = new GlobalInventoryGateways(serializedGlobalInventory);
            userTradesGateway = new UserTradesGateway(serializedUserTrades);
            globalWishlistGateway = new GlobalWishlistGateway(serializedGlobalWishlist);
            adminMessageGateway = new AdminMessageGateway(serializedAdminMessages);
        }
        catch(IOException | ClassNotFoundException ex) {
            mainMenuPresenter.readError();
            mainMenuPresenter.printExit();
            return;
        }
        // create managers to pass in serialized data
        AdminManager adminManager =
                new AdminManager(adminAccountGateways.getAdminMap(), adminMessageGateway.getMessages());
        UserManager userManager = new UserManager(userGateway.getMapOfUsers());
        GlobalInventoryManager globalInventoryManager = new GlobalInventoryManager(globalInventoryGateways.getgI());
        TradeManager tradeManager = new TradeManager(userTradesGateway.getUserTrades());
        GlobalWishlistManager globalWishlistManager =
                new GlobalWishlistManager(globalWishlistGateway.getWishlistItems());

        String input;
        boolean done = false;

        do {
            mainMenuPresenter.printMenuPrompt();
            try {
                input = bufferedReader.readLine();
                if (input.equals("1")) userLogin(userManager, tradeManager, adminManager, globalInventoryManager,
                        globalWishlistManager);
                else if (input.equals("2")) userSignup(userManager);
                else if (input.equals("3")) adminLogin(adminManager, userManager, tradeManager, globalInventoryManager);
                else {
                    mainMenuPresenter.printExit();
                    done = true;
                }
            }
            catch (IOException e) {
                mainMenuPresenter.inputError();
                mainMenuPresenter.printExit();
                return;
            }
        } while (!done);

        try {
            userGateway.writeToFile(serializedUsers, userGateway.getMapOfUsers());
            globalInventoryGateways.writeToFile(globalInventoryGateways.getgI());
            userTradesGateway.writeToFile(serializedUserTrades, userTradesGateway.getUserTrades());
            globalWishlistGateway.writeToFile(serializedGlobalWishlist, globalWishlistGateway.getWishlistItems());
            adminMessageGateway.writeToFile(serializedAdminMessages, adminMessageGateway.getMessages());
            adminAccountGateways.saveToFile(adminAccountGateways.getAdminMap());
        }
        catch(IOException e) {
            mainMenuPresenter.savingError();
        }
    }

    private void userLogin(UserManager userManager, TradeManager tradeManager, AdminManager adminManager,
                           GlobalInventoryManager globalInventoryManager, GlobalWishlistManager globalWishlistManager) {
        try {
            mainMenuPresenter.printLoginPrompt1();
            String username = bufferedReader.readLine();
            mainMenuPresenter.printLoginPrompt2();
            String pass = bufferedReader.readLine();

            if(userManager.login(username, pass)) {
                if (!userManager.getUserIsBanned(username)) {
                    UserMenu userMenu = new UserMenu(username, userManager, tradeManager, globalInventoryManager,
                            globalWishlistManager, adminManager);
                    userMenu.run();
                }
                else{
                    BannedUserController bannedUserController = new BannedUserController(username, adminManager);
                    bannedUserController.run();
                }
            }
            else mainMenuPresenter.wrongLogin();
        }
        catch (IOException e) {
            mainMenuPresenter.inputError();
            mainMenuPresenter.printExit();
        }
    }

    private void userSignup(UserManager userManager){
        try {
            mainMenuPresenter.printLoginPromptNewUsername();
            String username = bufferedReader.readLine();
            mainMenuPresenter.printLoginPrompt2();
            String pass = bufferedReader.readLine();

            boolean d = userManager.createNewUser(username, pass, userTradesGateway.getUserTrades());
            if (!d){
                mainMenuPresenter.usernameTooShort();
            }
            else {
                if (!(adminAccountGateways.getAdminMap().containsKey(username))) {
                    userGateway.getMapOfUsers().put(username, new User(username, pass));
                    mainMenuPresenter.successfulAccountCreation();
                }
                else mainMenuPresenter.takenUsername();
            }
        }
        catch (IOException e)
        {
            mainMenuPresenter.inputError();
            mainMenuPresenter.printExit();
        }
        catch (InvalidUsernameException f){
            mainMenuPresenter.takenUsername();
        }
    }

    private void adminLogin(AdminManager adminManager, UserManager userManager, TradeManager tradeManager,
                            GlobalInventoryManager globalInventoryManager) {
        try {
            mainMenuPresenter.printLoginPrompt1();
            String username = bufferedReader.readLine();
            mainMenuPresenter.printLoginPrompt2();
            String pass = bufferedReader.readLine();

            if ((adminManager.login(username, pass))) {
                AdminSystem successful = new AdminSystem(adminManager.getAdmin(username), adminManager, userManager,
                        globalInventoryManager, tradeManager);
                successful.run();
            } else mainMenuPresenter.wrongLogin();
        }
        catch (IOException e)
        {
            mainMenuPresenter.inputError();
            mainMenuPresenter.printExit();
        }
    }


}


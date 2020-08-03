package controllers;
import gateways.*;
import presenters.MainMenuPresenter;
import use_cases.*;
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
    private MainMenuPresenter mainMenuPresenter = new MainMenuPresenter();

    public void run() {
        final String adminFilepath = "data/serializedAdmins.ser";
        final String userFilepath = "data/serializedUsers.ser";
        final String globalInventoryFilepath = "data/serializedGlobalInventory.ser";
        final String adminMessagesFilepath = "data/serializedAdminMessages.ser";
        final String globalWishlistFilepath = "data/serializedGlobalWishlist.ser";
        final String tradeFilepath = "data/serializedUserTrades.ser";

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

        }
        catch(IOException | ClassNotFoundException ex) {
            mainMenuPresenter.readError();
            mainMenuPresenter.printExit();
            return;
        }
        // create managers to pass in serialized data
        UseCaseBuilder useCaseBuilder = new UseCaseBuilder();
        AdminManager adminManager = useCaseBuilder.getAdminManager(adminAccountGateways.getAdminMap(),
                adminMessageGateway.getMessages());
        UserManager userManager = useCaseBuilder.getUserManager(userGateway.getMapOfUsers());
        TradeManager tradeManager =
                useCaseBuilder.getTradeManager(userTradesGateway.getUserTrades());
        GlobalInventoryManager globalInventoryManager =
                useCaseBuilder.getGlobalInventoryManager(globalInventoryGateways.getGlobalInventory());
        GlobalWishlistManager globalWishlistManager =
                useCaseBuilder.getGlobalWishlistManager(globalWishlistGateway.getWishlistItems());

        String input;
        boolean done = false;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        do {
            mainMenuPresenter.printMenuPrompt();
            try {
                input = bufferedReader.readLine();
                if (input.equals("1")) userLogin(bufferedReader, userManager, tradeManager, adminManager,
                        globalInventoryManager, globalWishlistManager);
                else if (input.equals("2")){
                    userSignup(bufferedReader, userManager, adminManager);
                }
                else if(input.equals("3")){
                    startDemo(bufferedReader, globalInventoryManager);
                }
                else if (input.equals("4")){
                    adminLogin(bufferedReader, adminManager, userManager,
                            tradeManager, globalInventoryManager);
                }
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
            userGateway.writeToFile(userFilepath, userManager.getUserData());
            globalInventoryGateways.writeToFile(globalInventoryManager.getGlobalInventoryData());
            userTradesGateway.writeToFile(tradeFilepath, tradeManager.getTradeData());
            globalWishlistGateway.writeToFile(globalWishlistFilepath, globalWishlistManager.getGlobalWishlistData());
            adminAccountGateways.saveToFile(adminManager.getAdminData());
            adminMessageGateway.writeToFile(adminMessagesFilepath, adminManager.getAdminMessages());
            bufferedReader.close();
        }
        catch(IOException e) {
            mainMenuPresenter.savingError();
        }
    }
    private void startDemo(BufferedReader bufferedReader, GlobalInventoryManager globalInventoryManager){
        try {
            mainMenuPresenter.printLoginPrompt1();
            String username = bufferedReader.readLine();
            mainMenuPresenter.printLoginPrompt2();
            String password = bufferedReader.readLine();

            DemoUserController demo = new DemoUserController(username, password, globalInventoryManager);
            demo.run();
        }
        catch (IOException e) {
            mainMenuPresenter.inputError();
        }
    }
    private void userLogin(BufferedReader bufferedReader, UserManager userManager, TradeManager tradeManager,
                           AdminManager adminManager, GlobalInventoryManager globalInventoryManager,
                           GlobalWishlistManager globalWishlistManager) {
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
        }
    }

    private void userSignup(BufferedReader bufferedReader, UserManager userManager, AdminManager adminManager){
        try {
            mainMenuPresenter.printLoginPromptNewUsername();
            String username = bufferedReader.readLine();
            mainMenuPresenter.printLoginPrompt2();
            String password = bufferedReader.readLine();

            boolean d = userManager.createNewUser(username, password);
            if (!d){
                mainMenuPresenter.usernameTooShort();
            }
            else {
                if (!adminManager.userExist(username)) {
                    userManager.createNewUser(username, password);
                    mainMenuPresenter.successfulAccountCreation();
                }
                else mainMenuPresenter.takenUsername();
            }
        }
        catch (IOException e)
        {
            mainMenuPresenter.inputError();
        }
        catch (InvalidUsernameException f){
            mainMenuPresenter.takenUsername();
        }
    }

    private void adminLogin(BufferedReader bufferedReader, AdminManager adminManager, UserManager userManager,
                            TradeManager tradeManager, GlobalInventoryManager globalInventoryManager) {
        try {
            mainMenuPresenter.printLoginPrompt1();
            String username = bufferedReader.readLine();
            mainMenuPresenter.printLoginPrompt2();
            String pass = bufferedReader.readLine();

            if ((adminManager.login(username, pass))) {
                AdminSystem successfulLogin = new AdminSystem(adminManager.getAdmin(username), adminManager, userManager,
                        globalInventoryManager, tradeManager);
                successfulLogin.run();
            } else mainMenuPresenter.wrongLogin();
        }
        catch (IOException e)
        {
            mainMenuPresenter.inputError();
        }
    }


}


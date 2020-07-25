package controllers;

import presenters.AdminMenu;
import entities.Admin;
import use_cases.AdminManager;
import use_cases.GlobalInventoryManager;
import use_cases.TradeManager;
import use_cases.UserManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class AdminSystem {
    private Admin admin;
    private AdminMenu adminMenu;


    private AdminManager adminManager;
    private TradeManager tradeManager;


    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;

    /**
     * Class constructor.
     * Create a new AdminAccountSystem that controls and allows the admin to reply to system messages
     * @param admin the admin of the currently logged in.
     * @param adminManager the AdminManager will be used to change account information
     * @param um the UserManager will be used to change user account information
     * @param gim the GlobalInventory will be used to change item in GlobalInventory
     */




    public AdminSystem(Admin admin, AdminManager adminManager,
                       UserManager um, GlobalInventoryManager gim, TradeManager tradeManager) {
        this.admin = admin;
        adminMenu = new AdminMenu(admin);
        this.adminManager = adminManager;
        this.userManager = um;
        this.globalInventoryManager = gim;
        this.tradeManager = tradeManager;



    }
    /**
     * will run after the admin logged in and give options to admin to do some admin work.
     */

    public void run() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        adminMenu.printMainOption();
        while (!input.equals("5")) {
            try {
                input = br.readLine();
                switch (input) {
                    case "1":
                        adminMenu.goIntoMessageInbox();
                        AdminMessageReplySystem adminMessageReplySystem = new AdminMessageReplySystem(adminManager, globalInventoryManager,
                                userManager, admin.getUsername());
                        adminMessageReplySystem.run();
                        adminMenu.printMainOption();
                        break;
                    case "2":
                        AdminAccountSystem adminAccountSystem = new AdminAccountSystem(admin, adminManager, userManager);
                        adminAccountSystem.run();
                        adminMenu.printMainOption();
                        break;
                    case "3":
                        AdminBrowsingUsers adminBrowsingUsers = new AdminBrowsingUsers(userManager);
                        adminBrowsingUsers.start();
                        adminMenu.printMainOption();
                        break;
                    case "4":
                        TradeUndoSystem tradeUndoSystem = new TradeUndoSystem(tradeManager, userManager);
                        tradeUndoSystem.run();
                        adminMenu.printMainOption();
                        break;

                }


            } catch (IOException e) {
                adminMenu.printErrorOccurred();
            }
        }
        adminMenu.exitPresenter();
    }
}
package C_controllers;

import D_presenters.AdminMenu;
import F_entities.Admin;
import F_entities.Message;
import E_use_cases.AdminManager;
import E_use_cases.GlobalInventoryManager;
import E_use_cases.UserManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class AdminSystem {
    private Admin admin;
    private AdminMenu am;


    private AdminManager adminManager;


    private UserManager um;
    private GlobalInventoryManager gim;

    /**
     * Class constructor.
     * Create a new AdminAccountSystem that controls and allows the admin to reply to system messages
     * @param admin the admin of the currently logged in.
     * @param adminManager the AdminManager will be used to change account information
     * @param um the UserManager will be used to change user account information
     * @param gim the GlobalInventory will be used to change item in GlobalInventory
     */




    public AdminSystem(Admin admin, AdminManager adminManager,
                       UserManager um, GlobalInventoryManager gim) {
        this.admin = admin;
        am = new AdminMenu(admin);
        this.adminManager = adminManager;
        this.um = um;
        this.gim = gim;



    }
    /**
     * will run after the admin logged in and give options to admin to do some admin work.
     */

    public void run() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        am.printMainOption();
        while (!input.equals("4")) {
            try {
                input = br.readLine();
                switch (input) {
                    case "1":
                        am.goIntoMessageInbox();
                        AdminMessageReplySystem amr = new AdminMessageReplySystem(adminManager, gim,
                                um, admin.getUsername());
                        amr.run();
                        am.printMainOption();
                        break;
                    case "2":
                        AdminAccountSystem aas = new AdminAccountSystem(admin, adminManager, um);
                        aas.run();
                        am.printMainOption();
                        break;
                    case "3":
                        AdminBrowsingUsers abu = new AdminBrowsingUsers(um);
                        abu.start();
                        am.printMainOption();
                        break;
                }


            } catch (IOException e) {
                am.printErrorOccurred();
            }
        }
        am.exitPresenter();
    }
}
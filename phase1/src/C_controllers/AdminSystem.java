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
    Admin admin;
    AdminMenu am;


    AdminManager adminManager;
    ArrayList<Message> adminMessageList;

    UserManager um;



    GlobalInventoryManager gim;
    public AdminSystem(Admin admin, AdminManager adminManager,
                       UserManager um, GlobalInventoryManager gim) {
        this.admin = admin;
        am = new AdminMenu(admin);
        this.adminManager = adminManager;
        this.um = um;
        this.gim = gim;
        adminMessageList = adminManager.getAdminMessagesArrayList();


    }

    public void run() {
        am.printMainOption();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        boolean t = true;
        while (t) {
            try {
                input = br.readLine();
                switch (input) {
                    case "1":
                        am.goIntoMessageInbox();
                        AdminMessageReplySystem amr = new AdminMessageReplySystem(adminManager, gim,
                                um, admin.getUsername());
                        amr.run();

                    case "2":
                        AdminAccountSystem aas = new AdminAccountSystem(admin, adminManager, adminMessageList);
                        aas.run();

                    case "3":
                        AdminBrowsingUsers abu = new AdminBrowsingUsers(um);
                        abu.start();


                    case "4":
                        t = false;
                        am.exitPresenter();

                }

            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
    }
}
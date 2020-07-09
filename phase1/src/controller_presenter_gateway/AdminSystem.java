package controller_presenter_gateway;

import entities.Admin;
import uses_cases.AdminManager;
import uses_cases.GlobalInventoryManager;
import uses_cases.UserManager;
;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class AdminSystem {
    Admin admin;
    AdminMenu am;
    AdminAccountGateways aag;
    AdminMessageGateway amg;
    AdminManager adminManager;
    HashMap<String, Admin> adminHashMap;
    UserManager um;
    UserGateway ug;
    GlobalInventoryGateways gig;
    GlobalInventoryManager gim;
    AdminSystem(Admin admin) {
        this.admin = admin;
        am = new AdminMenu(admin);
        aag = new AdminAccountGateways("src/ser_file_infos/serializedAdmins.ser");
        amg = new AdminMessageGateway("src/ser_file_infos/serializedAdminMessages.ser");
        ug = new UserGateway("src/ser_file_infos/serializedUsers.ser");
        gig = new GlobalInventoryGateways("src/ser_file_infos/serializedGlobalInventory.ser");
        gim = new GlobalInventoryManager(gig.getgI());
        um = new UserManager(ug.getMapOfUsers());
        adminHashMap = aag.getAdminMap();
        adminManager = new AdminManager(adminHashMap, amg.getMessages());

    }

    public void run() {
        am.printMainOption();
        AdminMenu menu = new AdminMenu(admin);
        menu.printMainOption();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (true) {
            try {
                input = br.readLine();
                if (input.equals("1")) {
                    am.goIntoMessageInbox();
                    AdminMessageReplySystem amr = new AdminMessageReplySystem(adminManager, gim,
                            um, admin.getUsername());
                    amr.run();
                } else if (input.equals("2")) {
                    AdminAccountSystem aas = new AdminAccountSystem(admin);
                    aas.run();

                } else if (input.equals("3")) {
                    //TODO hello Sabih, How are you

                } else if (input.equals("4")) {
                    am.exitPresenter();

                    return;
                }

            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
    }
}
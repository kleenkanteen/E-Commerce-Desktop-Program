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
    AdminSystem(Admin admin, AdminAccountGateways aag, AdminMessageGateway amg, UserGateway ug, GlobalInventoryGateways gig) {
        this.admin = admin;
        am = new AdminMenu(admin);
        this.aag = aag;
        this.amg = amg;
        this.ug = ug;
        this.gig = gig;
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
                    AdminAccountSystem aas = new AdminAccountSystem(admin, aag, amg);
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
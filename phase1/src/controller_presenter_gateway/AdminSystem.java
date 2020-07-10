package controller_presenter_gateway;

import entities.Admin;
import entities.GlobalInventory;
import entities.Message;
import entities.User;
import use_cases.AdminManager;
import use_cases.GlobalInventoryManager;
import use_cases.UserManager;
;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminSystem {
    Admin admin;
    AdminMenu am;


    AdminManager adminManager;
    HashMap<String, Admin> adminHashMap;
    ArrayList<Message> adminMessageList;
    HashMap<String, User> userHashMap;
    GlobalInventory gi;
    UserManager um;

    GlobalInventoryGateways gig;
    GlobalInventoryManager gim;
    AdminSystem(Admin admin, HashMap<String, Admin> adminHashMap, ArrayList<Message> adminMessageList,
                HashMap<String,User> userHashMap, GlobalInventory gi) {
        this.admin = admin;
        am = new AdminMenu(admin);
        this.adminHashMap = adminHashMap;
        this.adminMessageList = adminMessageList;
        this.userHashMap = userHashMap;
        this.gi = gi;
        gim = new GlobalInventoryManager(gi);
        um = new UserManager(userHashMap);
        adminManager = new AdminManager(adminHashMap, adminMessageList);

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
                    AdminAccountSystem aas = new AdminAccountSystem(admin, adminHashMap, adminMessageList);
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
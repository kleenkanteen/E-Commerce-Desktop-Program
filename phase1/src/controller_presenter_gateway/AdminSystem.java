package controller_presenter_gateway;

import entities.Admin;
import uses_cases.AdminAccountManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminSystem {
    Admin admin;

    AdminSystem(Admin admin) {
        this.admin = admin;
    }

    public void run() {
        AdminMenu menu = new AdminMenu(admin);
        menu.printMainOption();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (true) {
            try {
                input = br.readLine();
                if (input.equals("1")) {
                    // AdminMessageReplySystem(admin.,);
                } else if (input.equals("2")) {
                    AdminAccountManagement ac = new AdminAccountManagement();
                    //ac.run()
                } else if (input.equals("3")) {
                    //TODO

                } else if (input.equals("4")) {

                    return;
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Something went wrong");
            }
        }

    }
}
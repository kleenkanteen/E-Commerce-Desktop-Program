package controller_presenter_gateway;

import entities.Admin;
;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminSystem {
    Admin admin;
    AdminMenu am;

    AdminSystem(Admin admin) {
        this.admin = admin;
        am = new AdminMenu(admin);
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
                    // AdminMessageReplySystem(admin.,);
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
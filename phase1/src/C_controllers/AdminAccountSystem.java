package C_controllers;
import F_entities.Admin;
import F_entities.Message;
import G_exceptions.InvalidUsernameException;
import D_presenters.AdminAccountPresenter;
import E_use_cases.AdminManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminAccountSystem {
    Admin admin;
    HashMap<String, Admin> adminHashMap;
    AdminAccountPresenter aap;
    ArrayList<Message> adminMessage;

    AdminManager am;

    /**
     * Class constructor.
     * Create a new AdminAccountSystem that controls and allows the admin to reply to system messages
     * @param admin the admin of the currently logged in.
     * @param adminHashMap the hashMap of admin that stored in program.
     * @param adminMessage the ArrayList of the Message
     */


    AdminAccountSystem(Admin admin, HashMap<String, Admin> adminHashMap,
                       ArrayList<Message> adminMessage){
        this.admin = admin;
        aap = new AdminAccountPresenter(admin);
        this.adminHashMap = adminHashMap;
        this.adminMessage = adminMessage;
        am = new AdminManager(adminHashMap, adminMessage);

    }


    /**
     * Interacts with the admin to allow them to modify information in AdminAccount.
     */


    public void run(){
        aap.printMainMenu();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals("3")) {

            try {
                input = br.readLine();
                if (input.equals("1")) {
                    aap.askForNewPassword();
                    String password1 = br.readLine();
                    aap.askToConfirmPassword();
                    String password2 = br.readLine();
                    if (am.addNewPassWord(password1,password2, admin)){
                        aap.passwordChanged();
                    }
                    else {
                        aap.failToChangePassword();
                    }
                    aap.printMainMenu();
                } else if (input.equals("2")) {
                    aap.newAdminUserName();
                    String newUsername = br.readLine();
                    aap.newAdminPassword();
                    String newPassword = br.readLine();
                    try {adminHashMap = am.addAdmin(newUsername, newPassword);
                  }
                    catch (InvalidUsernameException e) {
                        aap.failToCreateNewAdmin();
                    }
                    aap.printMainMenu();

                }
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
        aap.exitMenu();
    }

}

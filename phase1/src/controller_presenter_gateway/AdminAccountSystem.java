package controller_presenter_gateway;
import entities.Admin;
import entities.Message;
import exceptions.InvalidUsernameException;
import use_cases.AdminManager;

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


    AdminAccountSystem(Admin admin, HashMap<String, Admin> adminHashMap,
                       ArrayList<Message> adminMessage){
        this.admin = admin;
        aap = new AdminAccountPresenter(admin);
        this.adminHashMap = adminHashMap;
        this.adminMessage = adminMessage;
        am = new AdminManager(adminHashMap, adminMessage);

    }
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
                    };
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

                }
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
        aap.exitMenu();
    }

}

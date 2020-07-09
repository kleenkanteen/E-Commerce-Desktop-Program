package controller_presenter_gateway;
import entities.Admin;
import exceptions.InvalidUsernameException;
import uses_cases.AdminManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class AdminAccountSystem {
    Admin admin;
    HashMap<String, Admin> adminHashMap;
    AdminAccountPresenter aap;
    AdminAccountGateways aag;
    AdminMessageGateway amg;
    AdminManager am;


    AdminAccountSystem(Admin admin){
        this.admin = admin;
        aap = new AdminAccountPresenter(admin);
        aag = new AdminAccountGateways("src/ser_file_infos/serializedAdmins.ser");
        amg = new AdminMessageGateway("src/ser_file_infos/serializedAdmins.ser");
        adminHashMap = aag.getAdminMap();
        am = new AdminManager(adminHashMap, amg.getMessages());
        adminHashMap = aag.getAdminMap();
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
                    aag.saveToFile(adminHashMap);}
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

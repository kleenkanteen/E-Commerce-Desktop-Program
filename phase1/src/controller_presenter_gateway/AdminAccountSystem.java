package controller_presenter_gateway;
import entities.Admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class AdminAccountSystem {
    Admin admin;
    HashMap<String, Admin> adminHashMap;
    AdminAccountPresenter aap;
    AdminAccountGateways aag;

    AdminAccountSystem(Admin admin){
        this.admin = admin;
        aap = new AdminAccountPresenter(admin);
        aag = new AdminAccountGateways("/group_0147/phase1/src/ser_file_infos");
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
                    if (password1.equals(password2)){
                        adminHashMap.get(admin.getUsername()).setPassword(password1);
                        aag.saveToFile(adminHashMap);
                    }
                    else {
                        aap.failToChangePassword();
                    };
                } else if (input.equals("2")) {
                    aap.newAdminUserName();
                    String newUsername = br.readLine();
                    aap.newAdminPassword();
                    String newPassword = br.readLine();
                    if (!adminHashMap.containsKey(newUsername)){
                        adminHashMap.put(newUsername, new Admin(newUsername, newPassword));
                    aag.saveToFile(adminHashMap);}
                    else {
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

package C_controllers;
import E_use_cases.UserManager;
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
    private Admin admin;

    private AdminAccountPresenter aap;



    private UserManager um;

    private AdminManager am;


    /**
     * Class constructor.
     * Create a new AdminAccountSystem that controls and allows the admin to reply to system messages
     * @param admin the admin of the currently logged in.
     * @param am the AdminManager will be used to change account information

     * @param um the UserManager used to check account information
     */


    AdminAccountSystem(Admin admin, AdminManager am,
                       UserManager um){
        this.admin = admin;
        aap = new AdminAccountPresenter(admin);
        this.am = am;
        this.um = um;



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
                    if(um.isValidUser(newUsername)){
                        aap.failToCreateNewAdmin();
                    }
                    else {
                    try {
                        am.addAdmin(newUsername, newPassword);
                        aap.successadmin();
                  }
                    catch (InvalidUsernameException e) {
                        aap.failToCreateNewAdmin();
                    }
                    }
                    aap.printMainMenu();

                }
            } catch (IOException e) {
                aap.printErrorOccurred();
            }
        }
        aap.exitMenu();
    }

}

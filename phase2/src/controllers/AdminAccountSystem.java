package controllers;
import use_cases.UserManager;
import entities.Admin;
import exceptions.InvalidUsernameException;
import presenters.AdminAccountPresenter;
import use_cases.AdminManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminAccountSystem {
    private Admin admin;

    private AdminAccountPresenter adminAccountPresenter;



    private UserManager userManager;

    private AdminManager adminManager;


    /**
     * Class constructor.
     * Create a new AdminAccountSystem that controls and allows the admin to reply to system messages
     * @param admin the admin of the currently logged in.
     * @param adminManager the AdminManager will be used to change account information

     * @param userManager the UserManager used to check account information
     */


    AdminAccountSystem(Admin admin, AdminManager adminManager,
                       UserManager userManager){
        this.admin = admin;
        adminAccountPresenter = new AdminAccountPresenter(admin);
        this.adminManager = adminManager;
        this.userManager = userManager;



    }


    /**
     * Interacts with the admin to allow them to modify information in AdminAccount.
     */


    public void run(){
        adminAccountPresenter.printMainMenu();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals("3")) {

            try {
                input = br.readLine();
                if (input.equals("1")) {
                    adminAccountPresenter.askForNewPassword();
                    String password1 = br.readLine();
                    adminAccountPresenter.askToConfirmPassword();
                    String password2 = br.readLine();
                    if (adminManager.addNewPassWord(password1,password2, admin)){
                        adminAccountPresenter.passwordChanged();
                    }
                    else {
                        adminAccountPresenter.failToChangePassword();
                    }
                    adminAccountPresenter.printMainMenu();
                } else if (input.equals("2")) {
                    adminAccountPresenter.newAdminUserName();
                    String newUsername = br.readLine();
                    adminAccountPresenter.newAdminPassword();
                    String newPassword = br.readLine();
                    if(userManager.isValidUser(newUsername)){
                        adminAccountPresenter.failToCreateNewAdmin();
                    }
                    else {
                    try {
                        adminManager.addAdmin(newUsername, newPassword);
                        adminAccountPresenter.successadmin();
                  }
                    catch (InvalidUsernameException e) {
                        adminAccountPresenter.failToCreateNewAdmin();
                    }
                    }
                    adminAccountPresenter.printMainMenu();

                }
            } catch (IOException e) {
                adminAccountPresenter.printErrorOccurred();
            }
        }
        adminAccountPresenter.exitMenu();
    }

}

package controller_presenter_gateway;

import entities.Admin;

public class AdminMenu {
    Admin admin;
    AdminMenu(Admin admin){
        this.admin = admin;

    }
    public void printMainOption(){
        //TODO need adminManager ASAP
        System.out.println("Hi," + admin.getUsername() + "welcome back");
        System.out.println("Type 1 if you want to check your message inbox");
        System.out.println("Type 2 if you want to manage Admin account");
        System.out.println("Type 3 if you want to access the information of Users");
        System.out.println("Type 4 if you want to exit to login page");
    }

    public void exitPresenter(){
        System.out.println("Bye, have a good day!");
        System.out.println("Back to Login menu now");
    }

    public void goIntoMessageInbox(){
        System.out.println("You are now looking through messages you received");
    }




}

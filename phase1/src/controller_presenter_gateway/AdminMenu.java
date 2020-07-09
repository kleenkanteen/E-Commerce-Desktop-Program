package controller_presenter_gateway;

import entities.Admin;

public class AdminMenu {
    Admin admin;
    AdminMenu(Admin admin){
        this.admin = admin;

    }
    public void printMainOption(){
        //TODO need adminManager ASAP
        System.out.println("Hi," + admin.getUsername() + ", welcome back. Choose your option below:");
        System.out.println("1: check your message inbox");
        System.out.println("2: manage Admin account");
        System.out.println("3: access the information of Users");
        System.out.println("4: exit to login page");
    }

    public void exitPresenter(){
        System.out.println("Bye, have a good day!");
        System.out.println("Back to Login menu now");
    }

    public void goIntoMessageInbox(){
        System.out.println("You are now looking through messages you received");
    }




}

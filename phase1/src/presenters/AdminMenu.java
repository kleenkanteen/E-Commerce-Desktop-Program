package presenters;

import entities.Admin;

public class AdminMenu {
    Admin admin;
    public AdminMenu(Admin admin){
        this.admin = admin;

    }
    public void printMainOption(){
        //TODO need adminManager ASAP
        System.out.println("Hi," + admin.getUsername() + ", welcome back. Choose your option below:");
        System.out.println("[1] Check your message inbox");
        System.out.println("[2] Manage Admin account");
        System.out.println("[3] Access the information of Users");
        System.out.println("[4] Logout");
    }

    public void exitPresenter(){
        System.out.println("Bye, have a good day!");
        System.out.println("Back to Login menu now");
    }

    public void goIntoMessageInbox(){
        System.out.println("You are now looking through messages you received");
    }




}

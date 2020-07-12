package D_presenters;

import F_entities.Admin;

public class AdminMenu {
    Admin admin;
    /**
     * class constructor, it will create a new adminMenu and print the option the admins have in console
     * @param admin the admin that is currently logged in.
     */

    public AdminMenu(Admin admin){
        this.admin = admin;

    }

    /**
     * print to Admin after the admin logged in so that he will know what options he has
     */
    public void printMainOption(){
        System.out.println("Hello " + admin.getUsername() + ". Choose your option below:");
        System.out.println("[1] Check your message inbox");
        System.out.println("[2] Manage Admin account");
        System.out.println("[3] Access the information of Users");
        System.out.println("[4] Logout");
    }

    /**
     * print to Admin after the admin decides to logged out and go back to log in menu
     */

    public void exitPresenter(){
        System.out.println("Bye, have a good day!");
        System.out.println("Back to Login menu now");
    }

    /**
     * print to Admin if he decides to view the messages he received
     */

    public void goIntoMessageInbox(){
        System.out.println("You are now looking through messages you received");
    }




}

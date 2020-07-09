package controller_presenter_gateway;

public class AdminBrowsingUsersPresenter {

    public void enterUser(){ System.out.println("Enter the userid of the user you want or press [1] to exit"); }

    public void invalidUser(){
        System.out.println("Invalid user, try again");
    }

    public void infoUser(){
        System.out.println("Here is the user information. \n " +
                "Choose your option below: \n " +
                "[1] Change lending threshold, how many times user must lend before borrowing \n" +
                "[2] Freeze/unfreeze User" +
                "[3] Go back to user menu");
    }

    public void thresholdUser(){
        System.out.println("Here is the user information. Type [1] to go back to the browsing menu");
    }

}

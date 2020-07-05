package controller_presenter_gateway;

public class AdminMenu {
    AdminMenu(){

    }
    public void printMainOption(){
        System.out.println("Hi there, welcome back");
        System.out.println("Type 1 if you want to check your message inbox");
        System.out.println("Type 2 if you want to make an User the new Admin");
        System.out.println("Type 3 if you want to access the information of Users");
        System.out.println("Type 4 if you want to exit");
    }

    public void exitPresenter(){
        System.out.println("Bye, have a good day!");
        System.out.println("Back to Login now");
    }

    public void goIntoMessageInbox(){
        System.out.println("You are now looking through messages you received");
    }

    public void createNewAdmin(){
        System.out.println("You are creating a new admin");
        System.out.println("Press 1 to create/recreate the UserName of new admin");
        System.out.println("Press 2 to create/recreate the password of the new admin");
        System.out.println("Press 3 to confirm your new admin's UserName and its password");

    }

    public void newAdminConfirmed(){
        System.out.println("Bravo! You have made a new admin!");
        System.out.println("Now you are going back to the Admin's menu");
    }

}

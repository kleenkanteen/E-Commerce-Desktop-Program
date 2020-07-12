package D_presenters;
import F_entities.Admin;

public class AdminAccountPresenter {
    Admin admin;
    public AdminAccountPresenter(Admin admin){
        this.admin = admin;
    }
    public void printMainMenu(){
        System.out.println("Hi, you are now accessing the Admin account");
        System.out.println("Type 1 if you want to change your password\nType 2 if you want to" +
                "add a new admin account\nType 3 if you want to exit to admin's menu" );
    }
    public void askForNewPassword(){
        System.out.println("please type your new password: ");
    }
    public void askToConfirmPassword(){
        System.out.println("Please confirm your new password:");
    }
    public void failToChangePassword(){
        System.out.println("Sorry, two passwords don't match, failed to change your password");
    }
    public void passwordChanged(){
        System.out.println("Congrats! your password has been changed to " + admin.getPassword());
    }
    public void newAdminUserName(){
        System.out.println("Please type new admin's UserName: ");
    }
    public void newAdminPassword(){
        System.out.println("Please type new admin's password: ");
    }
    public void failToCreateNewAdmin(){
        System.out.println("Failed to create new admin because the UserName has been taken");
    }
    public void exitMenu(){
        System.out.println("going back to admin's menu.");

    }

}

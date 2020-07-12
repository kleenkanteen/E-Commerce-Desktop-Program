package D_presenters;
import F_entities.Admin;

public class AdminAccountPresenter {
    Admin admin;

    /**
     * class constructor, it will create a new adminAccountMenu and print the option the admins have in console
     * @param admin the admin that is currently logged in.
     */
    public AdminAccountPresenter(Admin admin){
        this.admin = admin;
    }

    /**
     * will show up when the admin access the AdminAccountMenu so that he will notice what options he has.
     */
    public void printMainMenu(){
        System.out.println("Hi, you are now accessing the Admin account");
        System.out.println("Type 1 if you want to change your password\nType 2 if you want to" +
                "add a new admin account\nType 3 if you want to exit to admin's menu" );
    }

    /**
     * print to Admin to tell that he has to type the new password he wants
     */
    public void askForNewPassword(){
        System.out.println("please type your new password: ");
    }
    /**
     * print to Admin to tell that he has to confirm the new password he wants
     */
    public void askToConfirmPassword(){
        System.out.println("Please confirm your new password:");
    }

    /**
     * print to Admin if he fails to change the password
     */
    public void failToChangePassword(){
        System.out.println("Sorry, two passwords don't match, failed to change your password");
    }

    /**
     * print to Admin if the password has been changed successfully and will present new password of that admin account
     */
    public void passwordChanged(){
        System.out.println("Congrats! your password has been changed to " + admin.getPassword());
    }

    /**
     *print to Admin to ask for new adminName after the admin chose to create a new AdminAccount
     */
    public void newAdminUserName(){
        System.out.println("Please type new admin's UserName: ");
    }

    /**
     * print to Admin to ask for new admin's password
     */
    public void newAdminPassword(){
        System.out.println("Please type new admin's password: ");
    }

    /**
     * print to Admin if he failed to create a new Admin Account since the userName has been taken
     */
    public void failToCreateNewAdmin(){
        System.out.println("Failed to create new admin because the UserName has been taken");
    }

    /**
     * print to Admin after he decides to leave AdminAccountMenu
     */
    public void exitMenu(){
        System.out.println("going back to admin's menu.");

    }

}

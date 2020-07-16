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
        System.out.println("You are accessing your Admin account. Choose your option below:");
        System.out.println("[1] Change your password\n[2] " +
                "Add a new admin account\n[3] Exit to admin menu" );
    }

    /**
     * print to Admin to tell that he has to type the new password he wants
     */
    public void askForNewPassword(){
        System.out.println("Please type your new password: ");
    }
    /**
     * print to Admin to tell that he has to confirm the new password he wants
     */
    public void askToConfirmPassword(){
        System.out.println("Please confirm your new password: ");
    }

    /**
     * print to Admin if he fails to change the password
     */
    public void failToChangePassword(){
        System.out.println("Two passwords don't match, failed to change your password");
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
        System.out.println("Please type new admin's username: ");
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
        System.out.println("Failed to create new admin because the Username has been taken");
    }

    /**
     * print to Admin after he decides to leave AdminAccountMenu
     */
    public void exitMenu(){
        System.out.println("Going back to admin's menu...");

    }

    /**
     * print admin account was successfully created
     */
    public void successadmin(){
        System.out.println("Success, admin account created");

    }

    /**
     * Print to the user that an error as occurred
     */
    public void printErrorOccurred(){
        System.out.println("Something went wrong");
    }

}

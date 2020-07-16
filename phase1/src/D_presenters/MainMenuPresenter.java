package D_presenters;

public class MainMenuPresenter {


    /**
     *Tells the user that they are exiting the program
     */
    public void printExit(){
        System.out.println("Exiting you from the program");
    }

    /**
     *Presents options to the user to log in (as admin or user) or create an account or exit.
     */
    public void printMenuPrompt() {
        System.out.println("Choose your option below:\n[1] User Login.\n[2] User Account Creation.\n" +
                "[3] Admin Login.\nOr any other value to exit the program\n");
    }
    /**
     * Presents option to user to enter username.
     */
    public void printLoginPrompt1(){
        System.out.println("Enter username:");
    }
    /**
     * Presents option to user to enter a new username.
     */
    public void printLoginPromptNewUsername(){
        System.out.println("Enter username (at least 3 characters):");
    }
    /**
     * Presents option to user to enter a password for their account.
     */
    public void printLoginPrompt2(){
        System.out.println("Enter password:");
    }
    /**
     * Tells user that there was some input error (file removed, moved, etc.)
     */
    public void inputError(){
        System.out.println("An error has occurred with your input");
    }
    /**
     * Tells user that there was some error in saving their file (can't write to, etc.)
     */
    public void savingError(){
        System.out.println("An error has occurred with saving.");
    }
    /**
     * Presents information to user/admin telling them their user + pass combination was incorrect.
     */
    public void wrongLogin(){
        System.out.println("Wrong login, try again.");
    }
    /**
     * Tells user that their file could not be read.
     */
    public void readError(){
        System.out.println("Failed to read.");
    }
    /**
     * Tells user or admin that the username they want to register with has already been taken.
     */
    public void takenUsername(){
        System.out.println("Your username is taken or invalid, try again.");
    }
    /**
     * Tells user that the username they are trying to register with is less than 3 characters long.
     */
    public void usernameTooShort(){ System.out.println("Your username was less than 3 characters, please try again."); }
    /**
     * Presents information to user that their user account was created with the username and password they specified.
     */
    public void successfulAccountCreation(){ System.out.println("New user account created."); }

}

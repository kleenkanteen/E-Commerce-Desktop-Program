package D_presenters;

public class MainMenuPresenter {
    public void printExit(){
        System.out.println("Exiting you from the program");
    }
    public void printMenuPrompt(){
        System.out.println("Choose your option below:\n[1] User Login.\n[2] User Account Creation.\n" +
                "[3] Admin Login.\nOr any other value to exit the program\n");
    }
    public void printLoginPrompt1(){
        System.out.println("Enter username:");
    }
    public void printLoginPromptNewUsername(){
        System.out.println("Enter username (at least 3 characters):");
    }
    public void printLoginPrompt2(){
        System.out.println("Enter password:");
    }
    public void inputError(){
        System.out.println("An error has occurred with your input");
    }
    public void savingError(){
        System.out.println("An error has occurred with saving.");
    }
    public void wrongLogin(){
        System.out.println("Wrong login, try again.");
    }
    public void readError(){
        System.out.println("Failed to read.");
    }
    public void takenUsername(){
        System.out.println("Your username is taken or invalid, try again.");
    }
    public void usernameTooShort(){ System.out.println("Your username was less than 3 characters, please try again."); }
    public void successfulAccountCreation(){ System.out.println("New user account created."); }

}

package D_presenters;

public class MainMenuPresenter {
    public void printExit(){
        System.out.println("Exiting you from the program");
    }
    public void printMenuPrompt(){
        System.out.println("Choose your option below:\n[1] User Login.\n[2] User Account Creation.\n" +
                "[3] Admin Login.\nAny other value to exit the program.");
    }
    public void printLoginPrompt1(){
        System.out.println("Enter username:\n");
    }
    public void printLoginPrompt2(){
        System.out.println("Enter password:\n");
    }
    public void inputError(){
        System.out.println("An error has occurred when you input");
    }
    public void savingError(){
        System.out.println("An error has occurred with saving");
    }
    public void wrongLogin(){
        System.out.println("Wrong login, try again");
    }
    public void readError(){
        System.out.println("Failed to read");
    }
}

package D_presenters;

public class MainMenuPresenter {
    public void printExit(){
        System.out.println("exiting you from the program");
    }
    public void printMenuPrompt(){
        System.out.println("Choose your option below:\n[1] for User Login.\n[2] for User Account Creation.\n" +
                "[3] for Admin Login.\nAny other value to exit the program.");
    }
    public void printLoginPrompt1(){
        System.out.println("Enter username:");
    }
    public void printLoginPrompt2(){
        System.out.println("Enter password");
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
}

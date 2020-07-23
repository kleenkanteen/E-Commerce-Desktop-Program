package presenters;

public class BannedUserPresenter {
    public void unBanRequestPrompt() {
        System.out.println("Would you like to submit an unban request to the administrators? \n[1] Yes. \n[2] No.");
    }

    public void unbanRequestSent() {
        System.out.println("Unban request sent.");
    }

    public void returningToMainMenu() {
        System.out.println("Returning you to main menu.");
    }

    public void inputNotUnderstood() {
        System.out.println("Input not understand, please try again.");
    }
}

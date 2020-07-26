package presenters;

public class BannedUserPresenter {
    public void unBanRequestPrompt() {
        System.out.println("You are BANNED");
        System.out.println("Would you like to beg for forgiveness to our glorious overlords? " +
                "\n[1] Plead for mercy. " +
                "\n[2] Remain a heretic.");
    }

    public void unbanRequestSent() {
        System.out.println("Congratulations! You have begged for forgiveness! :)");
    }

    public void returningToMainMenu() {
        System.out.println("Returning you to main menu.");
    }

    public void inputNotUnderstood() {
        System.out.println("Input not understand, please try again.");
    }
}

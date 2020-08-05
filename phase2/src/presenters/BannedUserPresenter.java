package presenters;

public class BannedUserPresenter {
    public void unBanRequestPrompt() {
        System.out.println("You are banned. Would you like to request an unban?" +
                "\n[1] Plead for mercy." +
                "\n[2] Return to main menu.");
    }

    public String unbanPrompt() {
        return "You are banned. Would you like to request an unban?";
    }

    public String unbanConfirm() {
        return "Plead for mercy.";
    }

    public String unbanCancel() {
        return "Cancel.";
    }

    public String unbanRequestSent() { return "Unban request sent."; }

    public void returningToMainMenu() {
        System.out.println("Returning you to main menu.");
    }

    public void inputNotUnderstood() {
        System.out.println("Input not understand, please try again.");
    }
}

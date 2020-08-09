package presenters;

public class BannedUserPresenter {

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
}

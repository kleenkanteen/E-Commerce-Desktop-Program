package frontend.bannedUserGUI;

class BannedUserPresenter {

    String unbanPrompt() {
        return "You are banned. Would you like to request an unban?";
    }

    String unbanConfirm() {
        return "Plead for mercy.";
    }

    String unbanCancel() {
        return "Cancel.";
    }

    String unbanRequestSent() { return "Unban request sent."; }
}

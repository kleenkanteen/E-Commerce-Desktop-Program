package frontend.AdminGUI;

import entities.Admin;

public class AdminGUIPresenter {
    AdminGUIPresenter(){

    }
    // Window

    public String adminAccountWindow(){
        return "Admin Account Management";

    }

    public String adminPasswordChangeWindow(){
        return ("Go set a new password!");

    }
    public String adminAccountCreationWindow(){
        return ("Go create a new pal!");

    }

    public String adminTradeUndoSearchWindow(){
        return "Admin Message Inbox";

    }

    public String undoUserTradeWindow(){
        return "User's Unstarted Trades";

    }

    public String adminMessageWindow(){
        return "Admin Message Inbox";
    }

    //Label and Text


    public String InvalidUserNameLabel(){
        return "invalid User Name";
    }

    public String userNameCannotBeEmpty(){
        return "User name cannot be empty";

    };

    public String passwordCannotBeEmpty(){
        return "Password cannot be empty";

    };


    public String newPasswordCreated(Admin admin){
        return "Congrats! your password has been changed to " + admin.getPassword();
    }

    public String newAdminCreated(){ return "Success, admin account created";

    }

    public String AdminCreationFailed(){
        return "Failed to create new admin because the Username has been taken";
    }

    public String newPasswordNotSaved(){
        return "Two passwords don't match, failed to change your password";
    }

    //Button

    public String exitButton(){
        return  "Back";
    }



    public String searchButtonText(){
        return "search";
    }

    public String saveChangeButtonText(){
        return "Save Changes";
    }

    public String adminCreationButtonText(){
        return "Add a new admin account";
    }

    public String adminChangePasswordButton(){
        return "Change your password";
    }


    public String messageInboxButton(){
        return "Check your message inbox";

    }
    public String adminAccountButton(){
        return "Manage Admin account";
    }
    public String userBrowsingButton(){
        return "Access the information of Users";
    }

    public String tradeUndoButton(){
        return "Undo the trade of Users";
    }

    public String deleteSelectedTradeButton(){
        return "delete selected Trade!";
    }

    public String newAdminButton(){
        return "Create a new Admin!";
    }





}

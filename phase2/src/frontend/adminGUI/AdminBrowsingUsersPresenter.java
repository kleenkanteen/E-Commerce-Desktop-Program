package frontend.adminGUI;

public class AdminBrowsingUsersPresenter {
    /**
     * Returns a string that prompts the user to choose an option
     * @return the string
     */
    public String optionPrompt(){
        return "Choose your option below";
    }

    /**
     * Returns a string telling the user they entered and invalid username
     * @return the string
     */
    public String invalidName(){
        return "Invalid username, try again";
    }

    /**
     * Returns a string telling the user to enter the new limit somewhere
     * @return the string
     */
    public String enterValuePrompt(){
        return "Enter the new limit in bottom right";
    }

    /**
     * Returns a string telling the user they changed the user's banning state
     * @return the string
     */
    public String banStateChangeSuccess(){
        return "User banning state has been changed";
    }

    /**
     * Returns a string telling the user they changed the user's frozen state
     * @return the string
     */
    public String freezeStateChangeSuccess(){
        return "User freezing state has been changed";
    }

    /**
     * Returns a string telling the user they changed the user's weekly limit
     * @return the string
     */
    public String weeklyLimitChangeSuccess(){
        return "Weekly limit successfully changed";
    }

    /**
     * Returns a string telling the user they changed the user's incomplete trade limit
     * @return the string
     */
    public String incompleteLimitChangeSuccess(){
        return "Incomplete trades limit successfully changed";
    }

    /**
     * Returns a string telling the user they changed the user's threshold
     * @return the string
     */
    public String thresholdChangeSuccess(){
        return "Threshold successfully changed";
    }

    /**
     * Returns a string telling the user they entered the wrong format
     * @return the string
     */
    public String wrongFormat(){ return "Enter only numbers"; }

}

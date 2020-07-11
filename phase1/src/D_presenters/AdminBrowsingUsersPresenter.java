package D_presenters;

public class AdminBrowsingUsersPresenter {

    public AdminBrowsingUsersPresenter(){
    }

    public void enterUser(){ System.out.println("Enter the userid of the user you want or press [1] to exit"); }

    public void invalidUser(){
        System.out.println("User does not exist, try again or press [0] to exit");
    }

    /**
     * @param info - The string of info passed in
     */

    public void infoUser(String info){
        System.out.println("User found, info below, choose your option below: \n" +
                "[1] Change lending threshold, how many times user must lend before borrowing \n" +
                "[2] Freeze/unfreeze User \n" +
                "[3] Change limit of trades per week \n" +
                "[4] Change limit of incomplete trades per week \n" +
                "[5] Go back to user menu \n" +
                info);

    }

    public void thresholdUser(){
        System.out.println("Print the number you want to set the lending threshold to:");
    }

    public void tradelimitUser(){
        System.out.println("Print the number you want to set the weekly trade limit to:");
    }

    public void incomptradeUser(){
        System.out.println("Print the number you want to set the weekly incomplete trade limit to:");
    }

    public void successUser(){
        System.out.println("Limit successfully changed");
    }

    public void thresholdsuccessUser(){
        System.out.println("Threshold successfully changed");
    }

    public void freezingUser(){ System.out.println("User freezing state has been changed"); }

    public void error(){ System.out.println("Something went wrong"); }

    public void invalid(){ System.out.println("Invalid input try again with only numbers"); }

    public void invalidoption(){ System.out.println("Invalid option, reloading menu"); }

}

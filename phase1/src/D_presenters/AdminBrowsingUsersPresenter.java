package D_presenters;

public class AdminBrowsingUsersPresenter {

    public AdminBrowsingUsersPresenter(){
    }

    public void enterUser(){ System.out.println("Enter the username of the user you want or press [1] to exit:"); }

    public void invalidUser(){
        System.out.println("User does not exist, try again or press [1] to exit");
    }

    /**
     * @param info - The string of info passed in
     */

    public void infoUser(String info){
        System.out.println("\n" + info + "\n\n" + "User account info above, choose your option below: \n\n" +
                "[1] Change lending threshold, how many times user must lend before borrowing \n" +
                "[2] Freeze/unfreeze User \n" +
                "[3] Change limit of trades per week \n" +
                "[4] Change limit of incomplete trades per week \n" +
                "[5] Return to user selection \n" +
                "[6] Return to admin menu");

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
        System.out.println("\nLimit successfully changed\n");
    }

    public void thresholdsuccessUser(){
        System.out.println("\nThreshold successfully changed\n");
    }

    public void freezingUser(){ System.out.println("\nUser freezing state has been changed\n"); }

    public void error(){ System.out.println("Something went wrong"); }

    public void invalid(){ System.out.println("\nInvalid input try again with only numbers\n"); }

    public void invalidoption(){ System.out.println("\nInvalid option, reloading menu\n"); }

}

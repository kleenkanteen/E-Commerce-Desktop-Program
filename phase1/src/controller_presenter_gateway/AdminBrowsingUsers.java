package controller_presenter_gateway;
import use_cases.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class AdminBrowsingUsers {
    UserManager users;
    AdminBrowsingUsersPresenter browse;

    public AdminBrowsingUsers(UserManager system, AdminBrowsingUsersPresenter browse){
        this.browse = browse;
        this.users = system;
    }


    public void start() throws IOException {
        try {
            // initially use start variable for while loop so .enterUser() menu is
            // printed every time user comes back to menu. Pressing 1 will stop while loop when it
            // loops back.
            boolean start = true;

            while (start) {
                browse.enterUser();
                BufferedReader re = new BufferedReader(new InputStreamReader(System.in));
                String user = re.readLine();
                // check if string is just composed of numbers
                while (!user.matches("[0-9]+")){
                    browse.invalid();
                    user = re.readLine();
                }
                // checks to see if admin wants to leave
                if (user.equals("1")){ start = false; }

                while (!user.equals("1")){
                    String info = users.representUser(user);
                    browse.infoUser(info);
                    String option = re.readLine();
                    while (!option.matches("[0-9]+")){
                        browse.invalid();
                        option = re.readLine();
                    }
                    // check if admin wants to go back to userid input menu
                    if (option.equals("5")) { user = "1"; }
                    else if (option.equals("1")){
                        browse.thresholdUser();
                        option = re.readLine();
                        while (!option.matches("[0-9]+")){
                            browse.invalid();
                            option = re.readLine();
                        }
                        users.setNewThresholdForOneUser(user, Integer.parseInt(user));
                        browse.thresholdsuccessUser();
                        }
                    // check if admin wants to change freeze a user
                    else if (option.equals("2")){
                        users.freezeUserAccount(user);
                        browse.freezingUser();
                    }
                    // check if admin wants to change limit for
                    // trades per week for individual user it selected
                    else if (option.equals("3")){
                        browse.tradelimitUser();
                        option = re.readLine();
                        while (!option.matches("[0-9]+")){
                            browse.invalid();
                            option = re.readLine();
                        }
                        users.setWeeklyTradesForOneUser(user, Integer.parseInt(option));
                        browse.successUser();
                    }
                    // check if admin wants to change limit  incomplete per week
                    // for individual user it selected
                    else if (option.equals("4")){
                        browse.incomptradeUser();
                        option = re.readLine();
                        while (!option.matches("[0-9]+")){
                            browse.invalid();
                            option = re.readLine();
                        }
                        users.setLimitOfIncompleteTradesForOneUser(user, Integer.parseInt(option));
                        browse.successUser();

                    }
                    }
                }
            }
            catch (IOException e){
                browse.error();
                }

        }
    }


package controllers;
import presenters.AdminBrowsingUsersPresenter;
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

                // check if valid user
                boolean valid = false;
                boolean validloop = true;

                // keep looping until admin either gives valid user or wants to go back
                while (!users.isValidUser(user) && validloop){
                    browse.invalidUser();
                    user = re.readLine();
                    if (user.equals("0")){ validloop = false; }
                }

                while (!user.equals("1") && !user.equals("0") ){
                    String info = users.representUser(user);
                    browse.infoUser(info);
                    String option = re.readLine();
                    while (!option.matches("[0-9]+")){
                        browse.invalid();
                        option = re.readLine();
                    }
                    // check if admin wants to go back to userid input menu
                    switch (option) {
                        case "5":
                            user = "1";
                            break;
                        case "1":
                            browse.thresholdUser();
                            option = re.readLine();
                            while (!option.matches("[0-9]+")) {
                                browse.invalid();
                                option = re.readLine();
                            }
                            users.setNewThresholdForOneUser(user, Integer.parseInt(user));
                            browse.thresholdsuccessUser();
                            break;
                        // check if admin wants to change freeze a user
                        case "2":
                            users.freezeUserAccount(user);
                            browse.freezingUser();
                            break;
                        // check if admin wants to change limit for
                        // trades per week for individual user it selected
                        case "3":
                            browse.tradelimitUser();
                            option = re.readLine();
                            while (!option.matches("[0-9]+")) {
                                browse.invalid();
                                option = re.readLine();
                            }
                            users.setWeeklyTradesForOneUser(user, Integer.parseInt(option));
                            browse.successUser();
                            break;
                        // check if admin wants to change limit  incomplete per week
                        // for individual user it selected
                        case "4":
                            browse.incomptradeUser();
                            option = re.readLine();
                            while (!option.matches("[0-9]+")) {
                                browse.invalid();
                                option = re.readLine();
                            }
                            users.setLimitOfIncompleteTradesForOneUser(user, Integer.parseInt(option));
                            browse.successUser();
                            break;
                        default:
                            browse.invalidoption();
                            break;
                    }
                    }
                }
            }
            catch (IOException e){
                browse.error();
                }

        }
    }


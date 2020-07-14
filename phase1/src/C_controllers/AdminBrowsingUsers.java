package C_controllers;
import D_presenters.AdminBrowsingUsersPresenter;
import E_use_cases.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class AdminBrowsingUsers {
    UserManager users;
    AdminBrowsingUsersPresenter browse;

    public AdminBrowsingUsers(UserManager system){
        this.browse = new AdminBrowsingUsersPresenter();
        this.users = system;
    }


    public void start() throws IOException {
        try {
            // initially use start variable for while loop so .enterUser() menu is
            // printed every time user comes back to menu. Pressing 1 will stop while loop when it
            // loops back.
            boolean start = true;

            while (true) {
                browse.enterUser();
                BufferedReader re = new BufferedReader(new InputStreamReader(System.in));
                String user = re.readLine();
                // checks to see if admin wants to leave
                if (user.equals("1")){ break; }

                // check if valid user

                // keep looping until admin either gives valid user or wants to go back
                while (!users.isValidUser(user)){
                    browse.invalidUser();
                    user = re.readLine();
                    if (user.equals("1")){ break; }
                }

                while (users.isValidUser(user)){
                    String info = users.getUserInfo(user);
                    browse.infoUser(info);
                    String option = re.readLine();
                    while (!option.matches("[0-9]+")){
                        browse.invalid();
                        option = re.readLine();
                    }
                    // check if admin wants to go back to userid input menu
                    if (option.equals("5")){
                        break;
                    }
                    switch (option) {
                        case "1":
                            browse.thresholdUser();
                            option = re.readLine();
                            while (!option.matches("[0-9]+")) {
                                browse.invalid();
                                option = re.readLine();
                            }
                            users.setNewThresholdForOneUser(user, Integer.parseInt(option));
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


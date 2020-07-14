package A_main;

import F_entities.Admin;
import G_exceptions.InvalidLoginException;

import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class AdminLogin {
    private static final Logger logger = Logger.getLogger(AdminLogin.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    private String username;
    private String password;
    private HashMap<String, Admin> adminHashMap;

    public AdminLogin(String username, String password, HashMap<String, Admin> adminHashMap) {
        this.username = username;
        this.password = password;
        this.adminHashMap = adminHashMap;
    }

    /**
     * Allows an admin to log in
     * If the username does not match up with password, throw InvalidLoginException.
     * @return the string username
     * @throws InvalidLoginException an invalid login
     */
    public String login() throws InvalidLoginException {
        // check username
        if(adminHashMap.containsKey(username)) {
            // check password
            if(password.equals(adminHashMap.get(username).getPassword())) {
                // if successful, return Admin object
                return username;
            }
        }
        throw new InvalidLoginException();
    }

    public Admin getAdminObject(){
        return adminHashMap.get(username);
    }
}


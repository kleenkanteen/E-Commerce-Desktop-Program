package controller_presenter_gateway;

// import controller_presenter_gateway.UserGateway;

import exceptions.InvalidLoginException;
import java.util.HashMap;
import entities.User;
import exceptions.InvalidUsernameException;

import java.util.logging.Level;

public class UserLogin {
    private String usernameInput;
    private String passwordInput;

    public UserLogin(String usernameInput, String passwordInput) {
        this.usernameInput = usernameInput;
        this.passwordInput = passwordInput;
    }

    /**
     * Allows a user to login. Only use with user login option in main menu!
     * If the username does not match up with password, throw InvalidLoginException.
     * Put this method in a try-catch!!!
     * @return the string username
     * @throws InvalidLoginException an invalid login
     */
    public String login(HashMap<String, User> allUsers) throws InvalidLoginException {
        // check username
        if(allUsers.containsKey(this.usernameInput)) {
            // check password
            if(this.passwordInput.equals(allUsers.get(usernameInput).getPassword())) {
                // if successful, return String username
                return this.usernameInput;
            }
        }
        throw new InvalidLoginException();
    }

    /**
     * Attempts to add a new user to the HashMap of all users
     * All usernames are unique.
     * Returns the HashMap if the user successfully created, Throw error if there's another user with the same username.
     * PUt this method in a try-catch!!!
     * @return the new HashMap containing the new User
     * @throws InvalidUsernameException username is already taken
     */
    public HashMap<String, User> createNewUser(HashMap<String, User> allUsers) throws InvalidUsernameException{
        if(!allUsers.containsKey(this.usernameInput)) {
            allUsers.put(usernameInput, new User(usernameInput, passwordInput));
            return allUsers;
        }
        throw new InvalidUsernameException();
    }
}

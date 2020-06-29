package entities;

public class Admin extends AccountInformation {
    /**
     * Creates an admin with the given username and password
     *
     * @param username is the username of this account
     * @param password is the password of this account
     */
    protected Admin(String username, String password) {
        super(username, password);
    }
}

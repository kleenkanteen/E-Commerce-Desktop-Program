public abstract class AccountInformation {
    private String username, password;

    /**
     * Creates an AccountInformation with the given username and password
     * @param username is the username of this account
     * @param password is the password of this account
     */
    protected AccountInformation(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * Getter of the username of this account
     * @return the username of this account
     */
    protected String getUsername() {
        return username;
    }

    /**
     * Getter of the password of this account
     * @return the password of this account
     */
    protected String getPassword() {
        return password;
    }
}

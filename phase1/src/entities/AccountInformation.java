package entities;

public abstract class AccountInformation {
    private String username, password;

    /**
     * Creates an entities.AccountInformation with the given username and password
     * @param username is the username of this account
     * @param password is the password of this account
     */
    public AccountInformation(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * Getter of the username of this account
     * @return the username of this account
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter of the password of this account
     * @return the password of this account
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setting a new username to this account
     * @param newUsername the new username for the account
     */
    public void setUsername(String newUsername){
        username = newUsername;
    }

    /**
     * Setting a new password to this account
     * @param newPassword the new password for the account
     */
    public void setPassword(String newPassword){
        password = newPassword;
    }

    /**
     * Indicates whether two entities.AccountInformation are "equal".
     * @param accInfo is the entities.AccountInformation that is being compared with
     * @return Whether the two entities.AccountInformation equal. Two entities.AccountInformation are equal iff
     * both have the same username and password
     */
    public boolean equals(AccountInformation accInfo) {
        return accInfo.getPassword().equals(password) && accInfo.getUsername().equals(username);
    }

}

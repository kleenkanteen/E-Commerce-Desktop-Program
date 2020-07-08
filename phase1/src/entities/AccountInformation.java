package entities;

import java.io.Serializable;

public abstract class AccountInformation implements Serializable {
    private String username, password;

    /**
     * Creates an AccountInformation with the given username and password
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
     * Setting a new password to this account
     * @param newPassword the new password for the account
     */
    public void setPassword(String newPassword){
        password = newPassword;
    }

    /**
     * Indicates whether two Account Information are "equal". Two Account Information are equal iff
     *      * both have the same username
     * @param accInfo is the Account Information that is being compared with
     * @return Whether the two Account Information are equal.
     */
    public boolean equals(AccountInformation accInfo) {
        return  accInfo.getUsername().equals(username);
    }

    /**
     * Returns a hash code value for the object.
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return username.hashCode();
    }

    /**
     * Returns a string representation of the object. Which is just the username of this Account Information
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return username;
    }
}

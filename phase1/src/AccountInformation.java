import java.util.ArrayList;

public abstract class AccountInformation {
    private String username, password;
    private ArrayList<Message> messages;

    /**
     * Creates an AccountInformation with the given username and password
     * @param username is the username of this account
     * @param password is the password of this account
     */
    protected AccountInformation(String username, String password){
        this.username = username;
        this.password = password;
        messages = new ArrayList<Message>();
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

    /**
     * Getter of the messages of this account
     * @return the messages of this account
     */
    protected ArrayList<Message> getMessages(){
        return messages;
    }

    /**
     * Add a new message to messages
     * @param newMessage the message being added to messages
     */
    protected void addMessage(Message newMessage){
        messages.add(newMessage);
    }

    /**
     * Deleting a message from messages, if the message is not in messages, nothing happens
     * @param message the message being deleted from messages
     */
    protected void removeMessage(Message message){
        messages.remove(message);
    }

    /**
     * Setting a new username to this account
     * @param newUsername the new username for the account
     */
    protected void setUsername(String newUsername){
        username = newUsername;
    }

    /**
     * Setting a new password to this account
     * @param newPassword the new password for the account
     */
    protected void setPassword(String newPassword){
        password = newPassword;
    }

    /**
     * Indicates whether two AccountInformation are "equal".
     * @param accInfo is the AccountInformation that is being compared with
     * @return Whether the two AccountInformation equal. Two AccountInformation are equal iff
     * both have the same username and password
     */
    public boolean equals(AccountInformation accInfo) {
        return accInfo.getPassword().equals(password) && accInfo.getUsername().equals(username);
    }

}

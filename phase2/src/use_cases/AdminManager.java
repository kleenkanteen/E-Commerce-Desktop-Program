package use_cases;

import entities.Admin;
import entities.Message;
import exceptions.InvalidUsernameException;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminManager{
    private HashMap<String, Admin> adminHashMap;
    private ArrayList<Message> adminMessagesArrayList;

    /**
     * Class constructor.
     * Takes in HashMap of all admin accounts and ArrayList of Messages shared between all of them.
     * @param adminHashMap HashMap containing all admin accounts.
     * @param adminMessagesArrayList ArrayList containing all admin Messages.
     */
    public AdminManager(HashMap<String, Admin> adminHashMap, ArrayList<Message> adminMessagesArrayList) {
        this.adminHashMap = adminHashMap;
        this.adminMessagesArrayList = adminMessagesArrayList;
    }

    /**
     * Attempts to add a new admin to the HashMap of all admins
     * All usernames are unique (including between admins and users)
     * Returns the HashMap if the admin successfully created
     * Throw error if there's another admin with the same username.
     * Put this method in a try-catch!!!
     * @param toAdd Admin object who we want to add to the HashMap of all admins.
     * @return the new HashMap containing the new Admin
     * @throws InvalidUsernameException username is already taken
     */

    public HashMap<String, Admin> addAdmin (Admin toAdd) throws InvalidUsernameException{
        if (adminHashMap.containsKey(toAdd.getUsername()))
            throw new InvalidUsernameException();

        adminHashMap.put(toAdd.getUsername(), toAdd);
        return adminHashMap;
    }

    /**
     * adds a particular admin to the HashMap of all admins given a username and password String input
     * @param username username of admin to be added
     * @param password password of admin to be added
     * @throws InvalidUsernameException if the username and password were wrong, throw this
     *
     */
    public void addAdmin (String username, String password) throws InvalidUsernameException{
        if (adminHashMap.containsKey(username)){
            throw new InvalidUsernameException();}

        adminHashMap.put(username, new Admin(username, password));

    }

    /** Attempts to retrieve Messages shared by all admin accounts.
     * @return ArrayList of all Messages shared by all admin accounts.
     */
    public ArrayList<Message> getAdminMessagesArrayList() {
        return adminMessagesArrayList;
    }

    /** Attempts to set shared admin Messages by replacing previous one(s).
     * @param adminMessagesArrayList shared admin Messages to replace previous one(s).
     */
    public void setAdminMessagesArrayList(ArrayList<Message> adminMessagesArrayList) {
        this.adminMessagesArrayList = adminMessagesArrayList;
    }

    /**Attempts to change password of a specific admin by getting input of new desired password twice.
     * @param password1 attempt 1 at entering new password
     * @param password2 attempt 1 at entering new password
     * @param admin admin who is attempting to get a new password
     * @return True if successful password change (attempt 1 and attempt 2 are same), false otherwise.
     */
    public boolean addNewPassWord(String password1, String password2, Admin admin){
        if (password1.equals(password2)){
            admin.setPassword(password1);

            return true;
        }
        else {
            return false;
        }
    }

    /**
     * verifies login is correct
     * @param username username that admin logs in with
     * @param password password that admin logs in with
     * @return whether or not a login matches
     */
    public boolean login(String username, String password) {
        if (adminHashMap.containsKey(username))
          return (getAdmin(username).getPassword().equals(password));
        return false;
    }

    /**
     * returns the admin with the specified username
     * @param username of the particular admin
     * @return the Admin with the specified username
     */
    public Admin getAdmin(String username){
        return adminHashMap.get(username);
    }
}
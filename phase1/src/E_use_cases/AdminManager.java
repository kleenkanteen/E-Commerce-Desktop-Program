package E_use_cases;

import F_entities.Admin;
import F_entities.Message;
import F_entities.User;
import G_exceptions.InvalidUsernameException;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminManager{
    private HashMap<String, Admin> adminHashMap;
    private HashMap<String, User> userHashMap;
    private ArrayList<Message> adminMessagesArrayList;

    /**
     * Class constructor.
     * Takes in HashMap of all admin accounts and ArrayList of Messages shared between all of them.
     * @param adminHashMap HashMap containing all admin accounts.
     * @param adminMessagesArrayList ArrayList containing all admin Messages.
     * @param userHashMap HashMap containing all user accounts.
     */
    public AdminManager(HashMap<String, Admin> adminHashMap, ArrayList<Message> adminMessagesArrayList,
                        HashMap<String, User> userHashMap) {
        this.adminHashMap = adminHashMap;
        this.adminMessagesArrayList = adminMessagesArrayList;
        this.userHashMap = userHashMap;
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
        if (adminHashMap.containsKey(toAdd.getUsername()) || userHashMap.containsKey((toAdd.getUsername())))
            throw new InvalidUsernameException();

        adminHashMap.put(toAdd.getUsername(), toAdd);
        return adminHashMap;
    }

    public HashMap<String, Admin> addAdmin (String username, String password) throws InvalidUsernameException{
        if (adminHashMap.containsKey(username) || userHashMap.containsKey(username))
            throw new InvalidUsernameException();
        adminHashMap.put(username, new Admin(username, password));
        return adminHashMap;
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
}
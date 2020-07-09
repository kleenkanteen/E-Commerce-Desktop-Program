package uses_cases;

import entities.Admin;
import entities.GlobalInventory;
import entities.Message;
import entities.User;
import exceptions.InvalidLoginException;
import exceptions.InvalidUsernameException;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminManager{
    private HashMap<String, Admin> adminHashMap;
    private ArrayList<Message> adminMessagesArrayList;

    AdminManager(HashMap<String, Admin> adminHashMap, ArrayList<Message> adminMessagesArrayList) {
        this.adminHashMap = adminHashMap;
        this.adminMessagesArrayList = adminMessagesArrayList;
    }

    /**
     * Attempts to add a new admin to the HashMap of all admins
     * All usernames are unique.
     * Returns the HashMap if the admin successfully created
     * Throw error if there's another admin with the same username.
     * Put this method in a try-catch!!!
     * @return the new HashMap containing the new Admin
     * @throws InvalidUsernameException username is already taken
     */

    public HashMap<String, Admin> addAdmin (Admin toAdd) throws InvalidUsernameException{
        if(!adminHashMap.containsKey(toAdd.getUsername())) {
            adminHashMap.put(toAdd.getUsername(), toAdd);
            return adminHashMap;
        }
        throw new InvalidUsernameException();
    }

    public HashMap<String, Admin> addAdmin (String username, String password) throws InvalidUsernameException{
        if(!adminHashMap.containsKey(username)) {
            adminHashMap.put(username, new Admin(username, password));
            return adminHashMap;
        }
        throw new InvalidUsernameException();
    }

    public ArrayList<Message> getAdminMessagesArrayList() {
        return adminMessagesArrayList;
    }

    public void setAdminMessagesArrayList(ArrayList<Message> adminMessagesArrayList) {
        this.adminMessagesArrayList = adminMessagesArrayList;
    }
}
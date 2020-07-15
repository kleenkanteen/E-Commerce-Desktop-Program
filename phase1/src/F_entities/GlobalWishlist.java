package F_entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GlobalWishlist implements Serializable {
    private HashMap<String, ArrayList<String>> wishMap;

    /*
    * Constructor just creates a new empty hashmap.
    */
    public GlobalWishlist(){
        wishMap = new HashMap<String, ArrayList<String>>();
    }


     /**
     * Add an user to list of users wanting an item. If no users want it yet, create item key and add user.
      * Make sure you ALWAYS call isItemWanted() before calling this else will error
      * @param itemid - id of item that is wanted
     * @param userid - id of user that wants the item
     */

    public void addWish(String itemid, String userid){
        if (wishMap.get(itemid) == null) {
            wishMap.put(itemid, new ArrayList<>());
        }
        wishMap.get(itemid).add(userid);
    }


     /**
     * Remove an user from wanting an item. If after removing, no users want the item, delete item key.
     * Make sure you ALWAYS call isItemWanted() before calling this else will error
      *  @param itemid - id of item that is wanted
     * @param userid - id of user that wants the item
     */

    public void removeWish(String itemid, String userid){
        wishMap.get(itemid).remove(userid);
        if (wishMap.get(itemid).isEmpty()){
            wishMap.remove(itemid);
        }
    }

    /**
     * Remove itemid key from globalwishlist
     * Make sure you ALWAYS call isItemWanted() before calling this else will error
     * @param itemid - id of item that you want removed
     */

    public void removeItem(String itemid) {
        if (wishMap.get(itemid) != null) {
            wishMap.remove(itemid);
        }
    }


     /**
     * Return if an item is wanted by anyone
     * @param itemid - id of item that is wanted
      * @return Whether or not anyone wants the item
     */
    public boolean isItemWanted(String itemid){
        return wishMap.get(itemid) != null;
    }


    /**
     * Return first userid who wants the item
     * @param itemid - id of item that is of interest
     * @return first userid of whoever wants the item
     */
    public String getFirstInterestedUser(String itemid){
        return wishMap.get(itemid).get(0);
    }

    /**
     * Return all userids that wants the item
     * Make sure you ALWAYS call isItemWanted() before calling this.
     * @param itemid - id of first user that wants the item
     * @return - arraylist of all users who want this item
     */
    public ArrayList<String> getAllInterestedUsers(String itemid){
        if (isItemWanted(itemid)) {
            return wishMap.get(itemid);
        }
        else { return new ArrayList<>();}
    }

    public String toString(){
        String i = "";
        for (String key : wishMap.keySet()) {
            i += key + wishMap.get(key)+ "\n";
        }
        // from https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
        return i;
    }
}

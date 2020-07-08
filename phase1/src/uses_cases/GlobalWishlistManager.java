package uses_cases;

import entities.GlobalWishlist;
import entities.Item;
import java.io.Serializable;
import java.util.ArrayList;

public class GlobalWishlistManager implements Serializable {

     // gW is the GlobalWishlist we want to modify.
    protected GlobalWishlist gW;

    /**
     * constructor for the class
     * @param gW - The globalwishlist it takes in
     */
    public GlobalWishlistManager(GlobalWishlist gW) {
        this.gW = gW;
    }


    /**
     * add the user's wish to the global wishlist
     * @param itemid - the item the user wants
     * @param userid - the user who wants it
     */

    public void addWish(String itemid, String userid) {
        gW.addWish(itemid, userid);
    }


    /**
     * remove the user's wish from the global wishlist
     * @param itemid - the item the user wants to be removed from
     * @param userid - the user who wants it
     */

    public void removeWish(String itemid, String userid) {
        gW.removeWish(itemid, userid);
    }


    /**
     * Return all userids that wants the item
     * @param itemid - id of first user that wants the item
     * @return - arraylist of all users who want this item
     */
    public ArrayList<String> getAllInterestedUsers(String itemid){
        return gW.getAllInterestedUsers(itemid);
    }


    /**
     * Returns the itemid and userid if anyone wants a item in the given arraylist. else empty arraylist.
     * @param allItems - all the items a user has
     * @return an arraylist of 2 strings, the first string is the interested itemid, the second string is the first user
     * who wants the earliest item in the given inventory arraylist. If no match, return empty arraylist.
     */

    public ArrayList<String> userWhoWants (ArrayList<Item> allItems){
        ArrayList<String> interested = new ArrayList<>();
        for (Item allItem : allItems) {
            String currentItemid = allItem.getItemID();
            if (gW.isItemWanted(currentItemid)) {
                interested.add(currentItemid);
                interested.add(gW.getFirstInterestedUser(currentItemid));
                return interested;
            }
        }
        return interested;
    }
}




package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GlobalWishlist implements Serializable {

    HashMap<String, ArrayList<String>> wishMap = new HashMap<String, ArrayList<String>>();
    /*
     * Create a HashMap to map itemid to a list of userids.
     * The constructor will be called with no parameter and automatically construct an empty HashMap
     * and an empty ArrayList.
     */
}

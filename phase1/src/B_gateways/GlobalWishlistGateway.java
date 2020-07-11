package B_gateways;

import F_entities.*;

import java.io.*;
public class GlobalWishlistGateway implements Serializable{

    GlobalWishlist wishlist;

    /**
     * Creates a new gateway that loads in the GlobalWishlist stored in a .ser file
     * @param filepath the directory where the .ser file is stored
     */
    public GlobalWishlistGateway(String filepath) {
        try {
            File file = new File(filepath);
            if (file.exists()) {
                wishlist = readFromFile(filepath);
                if(wishlist == null) {
                    wishlist = new GlobalWishlist();
                }
            } else {
                file.createNewFile();
                wishlist = new GlobalWishlist();
            }
        }
        catch(IOException ex) {
            System.out.println("Input error!");
        }
    }
    /**
     * Deserializes the contents of the GlobalWishlist that is serialized.
     * @param filepath Filepath to the .ser file storing the GlobalWishlist
     * @return the deserialized GlobalWishlist
     */
    public GlobalWishlist readFromFile(String filepath) {
        GlobalWishlist wishItems = new GlobalWishlist();
        try {
            // load in the objects
            InputStream file = new FileInputStream(filepath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the hashmap of user objects
            try {
                wishItems = (GlobalWishlist) input.readObject();
                input.close();
                return wishItems;
            }
            catch(ClassCastException ex) {
                System.out.println("Casting to improper subclass in GlobalWishlistGateway.");
            }
        }
        catch(IOException ex) {
            System.out.println("Input error during deserialization.");
        }
        catch(ClassNotFoundException ex) {
            System.out.println("Class could not be found.");
        }
        return wishItems;
    }

    /**
     * Serializes the arraylist of Message objects.
     * @param filepath where this file will be stored
     */
    public void writeToFile(String filepath, GlobalWishlist wishlistItems2) {
        try {
            // load allUsers onto the file at designed path
            FileOutputStream file = new FileOutputStream(filepath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(buffer);

            // serialize objects
            output.writeObject(wishlistItems2);
            output.close();
        }
        catch(IOException ex) {
            System.out.println("Input error during serialization!");
        }
    }

    public GlobalWishlist getWishlistItems() {
        return wishlist;
    }

}

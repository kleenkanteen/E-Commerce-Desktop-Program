package uses_cases;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the saving and loading of Students.
 */
public class UniqueIDGeneration implements Serializable {

    private static final Logger logger = Logger.getLogger(UniqueIDGeneration.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    /** An ArrayList of all previously issued IDs. */
    private ArrayList<Integer> ID;

    /**
     * Checks and reads file content.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public UniqueIDGeneration(String filePath) throws ClassNotFoundException, IOException {
        ID = new ArrayList<>();

        // Associate the handler with the logger.
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        File file = new File(filePath);
        if (file.exists()) {
            readFromFile(filePath);
        } else {
            file.createNewFile();
        }
    }

    public void readFromFile(String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            ID = (ArrayList<Integer>) input.readObject();

            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }

    public String generateID(String filePath) throws IOException {
        increase(filePath);
        int temp = getID(filePath);
        String temp2 = String.valueOf(temp);
        return temp2;
    }

    public int getID(String filePath) throws IOException {
        if (ID.isEmpty())
            return -1;
        else
            return ID.get(0);
    }

    /**
     * Increases ID by one to preserve uniqueness of issued IDs
     *
     * @param filePath path where unique ID stored.
     */
    public void increase(String filePath) throws IOException {
        // Ensures that if there is no ID, the first issued ID is 0.
        if (ID.isEmpty())
            ID.add(0);
        else
            ID.set(0,(ID.get(0)+1));
        saveToFile(filePath);
    }

    /**
     * Writes the ID to file at filePath.
     *
     * @param filePath the file to write the records to
     * @throws IOException
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(ID);
        output.close();
    }

    @Override
    public String toString() {
        return ID.toString();
    }
}

package controller_presenter_gateway;

import entities.*;
import uses_cases.*;

import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;

public class TradeManagerGateways{
    String filePath;
    TradeManager tradeManager;

    TradeManagerGateways(String filePath) throws IOException {
        this.filePath = filePath;

        try {
            File file = new File(filePath);
            if (file.exists()) {
                readFromFile();
            } else {
                file.createNewFile();
            }
        }
        catch(
                IOException ex) {
            System.out.println("Failed to read");
        }
        tradeManager = new TradeManager();
    }
    public void readFromFile() {
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            HashMap<String, ArrayList<Trade>> temp = (HashMap<String, ArrayList<Trade>>) input.readObject();
            tradeManager = new TradeManager(temp);
            input.close();
        } catch (IOException ex) {
            System.out.println("Failed to read");
        } catch (ClassNotFoundException ex) {
            System.out.println("failed to find the class to read");
        }

    }

    public void writeToFile(HashMap<String, ArrayList<Trade>>  temp) {
        try {
            OutputStream file = new FileOutputStream(filePath);

            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(temp);
            output.close();
        } catch (FileNotFoundException ex) {
            System.out.println("No file is found");

        } catch (IOException ex) {
            System.out.println("Filed to write the Object");
        }


    }

    public TradeManager returnTradeManager(){
        return tradeManager;
    }
}


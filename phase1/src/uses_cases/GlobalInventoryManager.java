package uses_cases;

import entities.Item;
import entities.GlobalInventory;

import java.util.ArrayList;

public class GlobalInventoryManager {
    GlobalInventory gI;
    GlobalInventoryManager(GlobalInventory gI){
        this.gI = gI;
    }

    public void addItem(String itemID, Item item){
        gI.addItem(itemID, item);
    }

    public void removeItem(String itemID){
        gI.removeItem(itemID);
    }



    public ArrayList<Item> generatePage(int pageNumber){
        ArrayList<Item> itemList = new ArrayList<Item>();
        for (int i = (pageNumber - 1) * 10; i < pageNumber * 10 && i < gI.getNumOfItem(); i++){
            itemList.add(gI.getItemByIndex(i));

        }
        return itemList;

    }

    public ArrayList<Item> searchWithItemName(String itemName){
        return gI.searchByItemName(itemName);
    }
    public ArrayList<Item> searchWithItemOwner(String ownerName){
        return gI.searchByOwnerName(ownerName);
    }
}


